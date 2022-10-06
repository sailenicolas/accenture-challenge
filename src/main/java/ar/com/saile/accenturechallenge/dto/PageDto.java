package ar.com.saile.accenturechallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
public class PageDto<T> extends PageImpl<T> {

    @Override
    @JsonIgnore
    public Sort getSort() {

        return super.getSort();
    }

    @Override
    @JsonIgnore
    public Pageable getPageable() {

        return super.getPageable();
    }

    public PageDto(List<T> content, Pageable pageable, long total) {

        super( content, pageable, total );
    }
}
