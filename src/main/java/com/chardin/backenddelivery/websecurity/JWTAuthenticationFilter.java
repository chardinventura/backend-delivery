package com.chardin.backenddelivery.websecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chardin.backenddelivery.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

			try {

				UserDto cred = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

				return getAuthenticationManager().authenticate(
						new UsernamePasswordAuthenticationToken(
							cred.getUsername(),
							cred.getPassword(),
							new ArrayList<>()));
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

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
											  HttpServletResponse response,
											  AuthenticationException failed) throws IOException, ServletException {

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, Object> body = new HashMap<>();
		body.put("message", failed.getMessage());
		body.put("error", "Username or password is incorrect");
		body.put("timeStamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:s")));
		body.put("status", HttpStatus.FORBIDDEN);

		PrintWriter out = response.getWriter();
		out.print(new ObjectMapper().writeValueAsString(body));
		out.flush();
	}
}

