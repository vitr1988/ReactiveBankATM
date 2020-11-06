package ru.vtb.repository;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.vtb.model.Company;

public interface CompanyRepository extends ReactiveMongoRepository<Company, String> {

    // Metric: {'geoNear' : 'location', 'near' : [x, y], 'minDistance' : min,
    //          'maxDistance' : max, 'distanceMultiplier' : metric.multiplier,
    //          'spherical' : true }
    Flux<GeoResult<Company>> findByLocationNear(Point location, Distance distance);
}
