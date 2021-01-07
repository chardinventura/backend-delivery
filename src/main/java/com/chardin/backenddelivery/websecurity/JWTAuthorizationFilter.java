package com.chardin.backenddelivery.websecurity;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(BasicAuthenticationFilter.class);

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityContants.HEADER_NAME);

		if(header == null || !header.startsWith(SecurityContants.TOKEN_PREFIX)) {

			chain.doFilter(request, response);
			return;
		}

		Authentication authentication = null;

		try{

			authentication = getAuthenticationManager(request);
		}catch(TokenExpiredException e) {
			LOG.error(e.getMessage());
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	// Read the JWT from the Authentication header, and then uses JWT to valid the token.
	private UsernamePasswordAuthenticationToken getAuthenticationManager(HttpServletRequest request)
   			throws TokenExpiredException {

		String token = request.getHeader(SecurityContants.HEADER_NAME);

		if(token != null) {

			String user = JWT.require(Algorithm.HMAC512(SecurityContants.SECRET))
				.build()
				.verify(token.replace(SecurityContants.TOKEN_PREFIX, ""))
				.getSubject();

			return user != null ?
				new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>()) :
				null;
		}

		return null;
	}
}

