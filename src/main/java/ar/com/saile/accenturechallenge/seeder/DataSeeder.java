package ar.com.saile.accenturechallenge.seeder;

import ar.com.saile.accenturechallenge.domain.*;
import ar.com.saile.accenturechallenge.dto.AlbumDto;
import ar.com.saile.accenturechallenge.dto.CommentDto;
import ar.com.saile.accenturechallenge.dto.PhotoDto;
import ar.com.saile.accenturechallenge.dto.PostDto;
import ar.com.saile.accenturechallenge.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
@AllArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private CompanyRepository companyRepository;
    private PhotoRepository photoRepository;
    private AlbumRepository albumRepository;

    private GeoRepository geoRepository;

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private RestTemplate restTemplate;


    @Override
    public void run(ApplicationArguments args) {

        List<User> response = this.getResponse( "https://jsonplaceholder.typicode.com/users", new ParameterizedTypeReference<>() {
        } );
        List<AlbumDto> response1 = this.getResponse( "https://jsonplaceholder.typicode.com/albums", new ParameterizedTypeReference<>() {
        } );
        List<PhotoDto> response2 = this.getResponse( "https://jsonplaceholder.typicode.com/photos", new ParameterizedTypeReference<>() {
        } );
        List<PostDto> response3 = this.getResponse( "https://jsonplaceholder.typicode.com/posts", new ParameterizedTypeReference<>() {
        } );
        List<CommentDto> response4 = this.getResponse( "https://jsonplaceholder.typicode.com/comments", new ParameterizedTypeReference<>() {
        } );
        //TODO: optimizar
        for (User u : response) {
            u.getAddress().setGeo( geoRepository.save( u.getAddress().getGeo() ));
            u.setAddress( addressRepository.save( u.getAddress() ));
            u.setCompany( companyRepository.save( u.getCompany() ));
            u.setPassword("$2a$12$kyyacyDPjfzAZYNQcvf0YuhjAFx.LxJ/LlwY1S1QtJ32aO1GxWwa6");
        }
        userRepository.saveAll( response );
        //TODO: usar modelmapper o mapstruct
        List<Album> albums = response1.stream().map( p ->
        {
            var a = new Album();
            a.setTitle( p.getTitle() );
            a.setId( p.getId() );
            a.setUser(userRepository.findById( p.getUser().getId() ).orElse( null ));
            return a;
        }).toList();
        albumRepository.saveAll( albums );
        List<Photo> photos = response2.stream().map( p -> {
            Photo photo = new Photo();
            photo.setUrl( p.getUrl() );
            photo.setTitle( p.getTitle() );
            photo.setAlbum( albumRepository.findById( p.getAlbum().getId() ).orElse( null ) );
            photo.setId( p.getId() );
            photo.setThumbnailUrl( p.getThumbnailUrl() );
            return photo;
        } ).toList();
        photoRepository.saveAll(photos);

        List<Post> posts = response3.stream().map( p -> {
            var a = new Post();
            a.setBody( p.getBody() );
            a.setId( p.getId() );
            a.setUser( userRepository.findById( p.getUser().getId() ).orElse( null ));
            return a;
        } ).toList();

        postRepository.saveAll( posts );

        List<Comment> comments = response4.stream().map( p -> {
            var a = new Comment();
            a.setBody( p.getBody() );
            a.setName( p.getName() );
            a.setEmail( p.getEmail() );
            a.setId( p.getId() );
            a.setPost( postRepository.findById( p.getPost().getId() ).orElse( null ) );
            return a;
        } ).toList();
        commentRepository.saveAll(comments);

    }


    private <E> E getResponse(String url, ParameterizedTypeReference<E> responseType){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                HttpEntity<E> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                        responseType);
                return response.getBody();

    }
}
