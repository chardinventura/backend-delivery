package com.chardin.backenddelivery.websecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chardin.backenddelivery.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

			try {

				UserDto cred = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

				List<GrantedAuthority> authorities = cred.getAuthorities() == null ?
						new ArrayList<>() :
						cred.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getName()))
								.collect(Collectors.toList());

				return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
							cred.getUsername(),
							cred.getPassword(),
							authorities));
			}catch(IOException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String token = JWT.create()
			.withSubject(authResult.getName())
			.withExpiresAt(new Date(System.currentTimeMillis() + SecurityContants.EXPIRATION_TIME))
			.sign(Algorithm.HMAC512(SecurityContants.SECRET));

		String body = SecurityContants.TOKEN_PREFIX.concat(token);

		response.getWriter().write(body);
		response.getWriter().flush();
	}
}

