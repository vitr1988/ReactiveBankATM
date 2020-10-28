package ru.vtb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "company")
public class Company {

    @Id
    private String id;

    private String companyId;

    private String name;

    private String country;

    private String address;

    private String addressAdd;

    private String workingTime;

    @GeoSpatialIndexed(name = "location", type = GeoSpatialIndexType.GEO_2D)
    private double[] location;

    private URL url;

    private Long rubricId;

    private LocalDateTime actualizationDate;
}
