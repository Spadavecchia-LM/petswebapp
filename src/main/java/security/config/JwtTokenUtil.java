package security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Data
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret_key;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails user){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration *  1000))
                .signWith(SignatureAlgorithm.ES512, secret_key)
                .compact();

    }

    public boolean validateToken(String token, UserDetails user){
        String username = getUserNameFromToken(token);

        return (username.equals(user.getUsername()) && !isTokenExpired(token));

    }

    public String getUserNameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }
    private Date tokenExpiration(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsTFunction.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        final Date expiration = tokenExpiration(token);
        return expiration.before(new Date());
    }
}
