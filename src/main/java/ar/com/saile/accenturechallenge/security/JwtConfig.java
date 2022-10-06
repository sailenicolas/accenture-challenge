package ar.com.saile.accenturechallenge.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Value( "${app.secret}" )
    private String SECRET;

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKey originalKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512");
        JWKSource<SecurityContext> immutableSecret = new ImmutableSecret<>( originalKey );
        return new NimbusJwtEncoder(immutableSecret);
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey originalKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA512" );
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm( MacAlgorithm.HS512 ).build();
    }
    @Bean
    Algorithm algorithm() {
        return Algorithm.HMAC512( SECRET.getBytes() );
    }

}
