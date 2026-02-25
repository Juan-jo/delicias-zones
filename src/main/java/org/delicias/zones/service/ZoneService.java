package org.delicias.zones.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.delicias.common.dto.PagedResult;
import org.delicias.zones.domain.model.ZoneInfo;
import org.delicias.zones.domain.repository.ZoneRepository;
import org.delicias.zones.dto.ZoneFilterReqDTO;
import org.delicias.zones.dto.ZoneInfoDTO;
import org.delicias.zones.dto.ZoneItemDTO;
import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ZoneService {

    @Inject
    ZoneRepository repository;

    @Transactional
    public void create(ZoneInfoDTO req) {

        ZoneInfo entity = ZoneInfo.builder()
                .name(req.name())
                .hasMinimumAmount(req.hasMinimumAmount())
                .minimumAmount(req.minimumAmount())
                .active(req.active())
                .area(createPolygonFromLatLng(req.coordinates()))
                .build();

        repository.persist(entity);
    }


    public ZoneInfoDTO findById(Integer zoneId) {

        ZoneInfo zone = repository.findById(zoneId);

        if(zone == null) {
            throw new NotFoundException("Zone Not Found");
        }

        List<List<Double>> coordinates = getCoordinates(zone.getArea());

        return ZoneInfoDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .hasMinimumAmount(zone.isHasMinimumAmount())
                .minimumAmount(zone.getMinimumAmount())
                .active(zone.isActive())
                .coordinates(coordinates)
                .build();

    }

    @Transactional
    public void update(ZoneInfoDTO req) {

        ZoneInfo entity = repository.findById(req.id());

        if(entity == null) {
            throw new NotFoundException("Zone Not Found");
        }

        Polygon area = createPolygonFromLatLng(req.coordinates());
        area.setSRID(4326);

        entity.setName(req.name());
        entity.setHasMinimumAmount(req.hasMinimumAmount());
        entity.setMinimumAmount(req.minimumAmount());
        entity.setActive(req.active());
        entity.setArea(area);
    }

    @Transactional
    public void deleteById(Integer zoneId) {

        var deleted = repository.deleteById(zoneId);

        if (!deleted) {
            throw new NotFoundException("Zone Not Found");
        }
    }



    public PagedResult<ZoneItemDTO> filterSearch(
            ZoneFilterReqDTO req
    ) {
        List<ZoneInfo> zones = repository.searchByName(
                req.getZoneName(),
                req.getPage(),
                req.getSize(),
                req.getOrderColumn(),
                req.toOrderDirection()
        );

        long total = repository.countByName(req.getZoneName());

        return new PagedResult<>(
                zones.stream().map(it -> ZoneItemDTO.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .active(it.isActive())
                        .build()).collect(Collectors.toList()),
                total,
                req.getPage(),
                req.getSize());
    }

    private Polygon createPolygonFromLatLng(List<List<Double>> points) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Coordinate[] coords = points.stream()
                .map(p -> new Coordinate(p.get(1), p.get(0))) // (lng, lat)
                .toArray(Coordinate[]::new);

        if (!coords[0].equals2D(coords[coords.length - 1])) {
            Coordinate[] closed = new Coordinate[coords.length + 1];
            System.arraycopy(coords, 0, closed, 0, coords.length);
            closed[closed.length - 1] = coords[0];
            coords = closed;
        }

        LinearRing shell = geometryFactory.createLinearRing(coords);

        Polygon polygon = geometryFactory.createPolygon(shell, null);
        polygon.setSRID(4326);

        return polygon;
    }


    private static List<List<Double>> getCoordinates(Polygon polygon) {

        Coordinate[] cords = polygon.getExteriorRing().getCoordinates();

        List<List<Double>> coordinates = new ArrayList<>();
        for (Coordinate c : cords) {
            List<Double> point = List.of(c.getY(), c.getX()); // [lat, lng]
            coordinates.add(point);
        }
        return coordinates;
    }
}
/*
String wkt = area.toText();
* int rows = repository.getEntityManager().createNativeQuery(
                        "UPDATE zone_info SET name = :name, area = ST_GeomFromText(:wkt, 4326), " +
                                "has_minimum_amount = :hasMin, minimum_amount = :minAmt, active = :active " +
                                "WHERE id = :id")
                .setParameter("name", req.name())
                .setParameter("wkt", wkt)
                .setParameter("hasMin", req.hasMinimumAmount())
                .setParameter("minAmt", req.minimumAmount())
                .setParameter("active", req.active())
                .setParameter("id", req.id())
                .executeUpdate();
* */