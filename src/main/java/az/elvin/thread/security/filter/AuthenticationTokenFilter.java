package az.cc103.doctorNurseDriver.security.filter;

import az.cc103.doctorNurseDriver.entity.UserEntity;
import az.cc103.doctorNurseDriver.entity.UserLoginHistoryEntity;
import az.cc103.doctorNurseDriver.exception.AuthException;
import az.cc103.doctorNurseDriver.exception.CommonException;
import az.cc103.doctorNurseDriver.exception.InvalidHeaderException;
import az.cc103.doctorNurseDriver.repo.UserLoginHistoryRepository;
import az.cc103.doctorNurseDriver.repo.UserRepository;
import az.cc103.doctorNurseDriver.security.jwt.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static az.cc103.doctorNurseDriver.enums.ResponseEnum.*;
import static java.util.Objects.isNull;

@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	@Qualifier("appUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLoginHistoryRepository userLoginHistoryRepository;

	private static final String[] AUTH_WHITELIST = {
			"v2/api-docs",
			"swagger-resources",
			"configuration/ui",
			"configuration/security",
			"swagger-ui.html",
			"webjars",
			"v3/api-docs",
			"swagger-ui",
			"sign-in",
			"sign-out",
			"refresh-token",
			"send-otp",
			"verify-otp",
			"change-password",
			"console",
			"csrf"
	};

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		final String requestHeader = request.getHeader(environment.getProperty("token.header"));
		String username;
		String authToken;
		boolean isPermit = false;
		try {

			for (String s : AUTH_WHITELIST) {
				if (request.getServletPath().contains(s) || request.getServletPath().equals("/")) {
					isPermit = true;
					break;
				}
			}

			if (isPermit) {
				chain.doFilter(request,response);
			} else {
				if ((!isNull(requestHeader) && requestHeader.startsWith("Bearer "))) {
					authToken = requestHeader.substring(7);
					try {
						username = jwtTokenUtil.getUsernameFromToken(authToken);
					} catch (MalformedJwtException e) {
						log.error("an error occurred during getting username from token", e);
						throw new AuthException(ACCESS_DENIED);

					} catch (ExpiredJwtException e) {
						log.warn("the token is expired and not valid anymore", e);
						throw new AuthException(ACCESS_TOKEN_IS_EXPIRED);
					}
				} else {
					throw new InvalidHeaderException(INVALID_HEADER);
				}

				log.info("device id = " + request.getHeader(environment.getProperty("device.header")));
				final Integer deviceId = Integer.valueOf(request.getHeader(environment.getProperty("device.header")));

				UserEntity user = userRepository.findByUsernameIgnoreCase(username);
				UserLoginHistoryEntity userLoginHistoryEntity = userLoginHistoryRepository
						.findByUser_IdAndDeviceIdAndActive(user.getId(), deviceId, 1);
				Optional.ofNullable(userLoginHistoryEntity).orElseThrow(() -> new CommonException(USER_IS_LOGOUT));

				log.info(String.format("checking authentication for user = %s ", username));
				if (!isNull(username) && isNull(SecurityContextHolder.getContext().getAuthentication())) {

					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

					if (jwtTokenUtil.validateToken(authToken, userDetails)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						log.info("authenticated user " + username + ", setting security context");
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
				chain.doFilter(request, response);
			}

		}
		catch (RuntimeException ex) {
			resolver.resolveException(request, response, null, ex);
		}

	}

}