package security.jwt;

import com.educacionIT.CLASE1.service.UsuarioService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.config.JwtTokenUtil;

import java.io.IOException;

@Data
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
   private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;

        String token = null;

        if(requestTokenHeader != null  && requestTokenHeader.startsWith("Bearer")){
            token = requestTokenHeader.substring(7);

            try{
                username = jwtTokenUtil.getUserNameFromToken(token);
            }catch(IllegalArgumentException e){
                System.out.println("no se puede acceder al token " + e);
            }
            catch(ExpiredJwtException e){
                System.out.println("token expirado " + e);
            }
        }
        else{
            System.out.println("el token no comienza con Bearer");
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = usuarioService.loadUserByUsername(username);

            if(jwtTokenUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }
        filterChain.doFilter(request,response);
    }
}
