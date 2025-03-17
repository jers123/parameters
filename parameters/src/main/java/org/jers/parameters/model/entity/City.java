package org.jers.parameters.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = "city", catalog = DATABASE, schema = SCHEMA)
@NoArgsConstructor
@Getter
@Setter
public class City extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_city",nullable = false, updatable = false)
    private Integer idCity;

    @Column(name = "city_" + NAME, nullable = false, length = 100)
    private String cityName;

    @JoinColumn(name = ID + "_state", referencedColumnName = ID + "_state", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private State idState;

    @Column(name = "landline_phone_identifier", length = 5)
    private String landlinePhoneIdentifier;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "minimum_temperature")
    private Double minimumTemperature;

    @Column(name = "maximum_temperature")
    private Double maximumTemperature;

    @Column(name = "height_above_sea_level")
    private Double heightAboveSeaLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOriginCity", fetch = FetchType.LAZY)
    private List<CityToCity> cityToCityOrigins;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDestinationCity", fetch = FetchType.LAZY)
    private List<CityToCity> cityToCityDestinations;
}