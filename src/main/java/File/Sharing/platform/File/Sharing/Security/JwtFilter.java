package File.Sharing.platform.File.Sharing.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

@Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response,
                                    FilterChain filterchain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String email;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterchain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        try{
            email=jwtService.extractUsername(jwt);
            if(email!=null && SecurityContextHolder.
                    getContext().
                    getAuthentication()==null){
                UserDetails userDetails=userDetailsService.loadUserByUsername(email);

                if(jwtService.isTokenValid(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken authToken=
                            new UsernamePasswordAuthenticationToken
                                    (userDetails,null,userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().
                                    buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
        }
        catch (Exception e){

        }
        filterchain.doFilter(request,response);
    }
}
