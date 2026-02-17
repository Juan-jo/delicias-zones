package org.delicias.banners.service;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.banners.domain.model.ZoneBanner;
import org.delicias.banners.domain.repository.ZoneBannerRepository;
import org.delicias.banners.dto.ZoneBannerDTO;
import org.delicias.banners.dto.ZoneBannerItemDTO;
import org.delicias.common.dto.PagedResult;
import org.delicias.zones.domain.model.ZoneInfo;

import java.util.List;

@ApplicationScoped
public class ZoneBannerService {

    @Inject
    ZoneBannerRepository repository;

    @Transactional
    public void create(Integer zoneId, ZoneBannerDTO req) {

        repository.persist(
                ZoneBanner.builder()
                        .title(req.title())
                        .description(req.description())
                        .sequence(req.sequence())
                        .active(req.active())
                        .zone(new ZoneInfo(zoneId))
                        .build()
        );

    }

    @Transactional
    public void update(ZoneBannerDTO req) {

        var entity = repository.findById(req.id());


        if (entity == null) {
            throw new NotFoundException("ZoneBanner not found");
        }

        entity.setTitle(req.title());
        entity.setDescription(req.description());
        entity.setSequence(req.sequence());
        entity.setActive(req.active());

        repository.persist(entity);
    }

    public ZoneBannerDTO findById(Integer zoneBannerId) {

        var entity = repository.findById(zoneBannerId);

        if (entity == null) {
            throw new NotFoundException("ZoneBanner not found");
        }

        return ZoneBannerDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .sequence(entity.getSequence())
                .active(entity.getActive())
                .build();
    }

    @Transactional
    public void deleteById(Integer zoneBannerId) {
        var deleted = repository.deleteById(zoneBannerId);

        if (!deleted) {
            throw new NotFoundException("ZoneBanner Not Found");
        }
    }



    public PagedResult<ZoneBannerItemDTO> getByZone(
            Integer zoneId,
            Integer page,
            Integer size,
            String orderColumn,
            String orderDir
    ) {

        Sort.Direction direction = switch (orderDir.toLowerCase()) {
            case "desc", "descending" -> Sort.Direction.Descending;
            default -> Sort.Direction.Ascending;
        };

        var banners = repository.getByZone(zoneId, page, size, orderColumn, direction);

        long total = repository.countByZone(zoneId);

        if (total == 0 || banners.isEmpty()) {
            return new PagedResult<>(
                    List.of(),
                    total,
                    page,
                    size
            );
        }

        var filtered = banners.stream().map(it -> ZoneBannerItemDTO.builder()
                .id(it.getId())
                .title(it.getTitle())
                .sequence(it.getSequence())
                .active(it.getActive())
                .build()).toList();


        return new PagedResult<>(
                filtered,
                total,
                page,
                size
        );
    }

}
