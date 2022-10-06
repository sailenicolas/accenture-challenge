package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link Address} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class AddressDto implements Serializable {

    private Long id;

    private String street;

    private String suite;

    private String city;

    private String zipcode;

    private GeoDto geo;
}