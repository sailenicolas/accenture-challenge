package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.domain.Geo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link Geo} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class GeoDto implements Serializable {

    private Long id;

    private String lat;

    private String lng;
}