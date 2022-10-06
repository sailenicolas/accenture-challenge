package ar.com.saile.accenturechallenge.dto;

import ar.com.saile.accenturechallenge.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link Company} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CompanyDto implements Serializable {

    private Long id;

    private String name;

    private String catchPhrase;

    private String bs;
}