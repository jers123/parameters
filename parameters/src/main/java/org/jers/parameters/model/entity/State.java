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

import static org.jers.parameters.utils.SystemConstants.DATABASE;
import static org.jers.parameters.utils.SystemConstants.ID;
import static org.jers.parameters.utils.SystemConstants.NAME;
import static org.jers.parameters.utils.SystemConstants.SCHEMA;

@Entity
@Table(name = "state", catalog = DATABASE, schema = SCHEMA)
@NoArgsConstructor
@Getter
@Setter
public class State extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID + "_state", nullable = false, updatable = false)
    private Integer idState;

    @Column(name = "state_" + NAME, nullable = false, length = 100)
    private String stateName;

    @JoinColumn(name = ID + "_country", referencedColumnName = ID + "_country", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Country idCountry;

    @JoinColumn(name = ID + "_state_type", referencedColumnName = ID + "_state_type", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StateType idStateType;

    @Column(name = "landline_phone_identifier", length = 5)
    private String landlinePhoneIdentifier;


}