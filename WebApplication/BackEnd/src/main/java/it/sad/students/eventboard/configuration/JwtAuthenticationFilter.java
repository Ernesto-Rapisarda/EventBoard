package it.sad.students.eventboard.configuration;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import it.sad.students.eventboard.persistenza.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext context) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");

    }
    @Override

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals("POST")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
        }else{
            throw new InsufficientAuthenticationException("Metodo non consentito");
        }


    }
    @Override

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException, ServletException {

        UserDetails user = ((UserDetails) authentication.getPrincipal());
        Person user1 = ((Person) authentication.getPrincipal());
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        byte[] signingKey = "%C*F-J@jXn2r5u8x/Asds?D(G+KbPdSgVkYp3s6v9y$B&E)H@McQfThWmZq4t7".getBytes();

        //preparo il token JWT
        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("secure-api")
                .setAudience("secure-app")
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 120*60*1000))
                .claim("rol", roles)
                .compact();




        try{

            //preparo la risposta da inviare al FE con Username, Authorities e Duration del token di autenticazione
            Gson gson = new Gson();
            response.setHeader("Access-Control-Expose-Headers", "*");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Map<String,Object> ret = new HashMap<String, Object>();
            ret.put("id",user1.getId());
            ret.put("username", user.getUsername());
            ret.put("lastName", user1.getLastName());
            ret.put("role",user.getAuthorities());
            ret.put("token",token);

            response.getWriter().write(gson.toJson(ret));

        }catch ( Exception e){
            e.printStackTrace();
        }

    }

}