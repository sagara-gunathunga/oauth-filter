package org.wso2.as.samples.oauth2.filter;

/**
 * This filter is not production ready and developed only for demonstrations purposes. 
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.wso2.as.samples.oauth2.util.TokenValidationClient;

public class OAuth2Filter implements Filter {

	private String userName = null;
	private String password = null;
	private String serverUrl = null;
	private String basicAuthValue = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authHeader = httpServletRequest
					.getHeader(authorizationHeader);
			if (authHeader != null && authHeader.length() > 0) {
				authHeader = authHeader.replace(bearer, "").trim();
				System.out.println("===" + authHeader + "====");
				TokenValidationClient client = new TokenValidationClient();
				try {
					if (client.validateToken(authHeader, basicAuthValue,
							serverUrl)) {
						filterChain.doFilter(request, response);
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		((HttpServletResponse) response)
				.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		return;

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		userName = filterConfig.getInitParameter("userName");
		password = filterConfig.getInitParameter("password");
		serverUrl = filterConfig.getInitParameter("serverUrl");
		if (userName == null || password == null || serverUrl == null) {
			throw new RuntimeException(
					" Init Parametes not set for OAuth2Filter");
		} else {
			String planText = userName + ":" + password;
			basicAuthValue = new String(
					Base64.encodeBase64(planText.getBytes()));
		}

	}

	public static final String authorizationHeader = "Authorization";
	public static final String bearer = "Bearer";

}
