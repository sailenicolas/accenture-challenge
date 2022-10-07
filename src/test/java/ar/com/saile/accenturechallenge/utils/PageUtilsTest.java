package ar.com.saile.accenturechallenge.utils;

import ar.com.saile.accenturechallenge.dto.PageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {PageUtils.class})
@ExtendWith(SpringExtension.class)
class PageUtilsTest {

    /**
     * Method under test: {@link PageUtils#getPageable(int, String)}
     */
    @Test
    @DisplayName( "It checks if it creates a pegeable object based on two params, page and order." )
    void testGetPageable() {

        Pageable actualPageable = PageUtils.getPageable( 1, "desc" );
        assertTrue( actualPageable.hasPrevious() );
        assertEquals( 1, actualPageable.getSort().toList().size() );
        Optional<Sort.Order> optionalOrder = actualPageable.getSortOr( Sort.by( Sort.Direction.DESC.name() ) ).get().findFirst();
        assertThat( optionalOrder ).isPresent();
        assertThat( optionalOrder.get().isDescending()).isTrue();
        assertEquals( 10, actualPageable.getPageSize() );
    }

    /**
     * Method under test: {@link PageUtils#getPageable(int, String)}
     */
    @Test
    @DisplayName( "It should throw an Exception whenever we pass an argument page lower than zero to getPageable" )
    void testGetPageable2() {
        assertThrows( IllegalArgumentException.class,() -> PageUtils.getPageable( -1, "Order" ));
    }

    /**
     * Method under test: {@link PageUtils#getPageable(int, String)}
     */
    @Test
    @DisplayName( "It creates a pageable with desc direction" )
    void testGetPageable3() {

        Pageable actualPageable = PageUtils.getPageable( 1, "desc" );
        assertTrue( actualPageable.hasPrevious() );
        assertEquals( 1, actualPageable.getSort().toList().size() );
        assertEquals( 10, actualPageable.getPageSize() );
    }

    /**
     * Method under test: {@link PageUtils#getPageDto(Page)}
     */
    @Test
    void testGetPageDto() {
        PageDto<Object> prefix = PageUtils.getPageDto( new PageImpl<>( new ArrayList<>(), PageUtils.getPageable( 1, "asc" ), 10 ) );
        assertEquals(  0, prefix.getContent().size());
        Optional<Sort.Order> optionalOrder = prefix.getSort().get().findFirst();
        assertThat( optionalOrder ).isNotNull();
        assertThat(optionalOrder).isPresent();
        assertThat(optionalOrder.get().getDirection()).isEqualTo( Sort.Direction.ASC);
        assertThat( prefix.getTotalPages()).isEqualTo( 1 );
        assertThat( prefix.getNumber()).isEqualTo( 1 );
        assertThat( prefix.getNumberOfElements() ).isEqualTo( 0 );
    }

}

