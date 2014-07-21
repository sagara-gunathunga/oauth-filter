package org.wso2.as.samples.oauth2.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.commons.httpclient.Header;
import org.wso2.carbon.identity.oauth2.stub.OAuth2TokenValidationServiceStub;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationRequestDTO_OAuth2AccessToken;
import org.wso2.carbon.identity.oauth2.stub.dto.OAuth2TokenValidationResponseDTO;

public class TokenValidationClient {

	static ConfigurationContext configCtx;

	public boolean validateToken(String token, String basicAuthValue,
			String serverUrl) throws Exception {

		String serviceURL = null;
		ServiceClient client = null;
		Options option = null;
		OAuth2TokenValidationServiceStub authStub = null;

		serviceURL = serverUrl + "OAuth2TokenValidationService";
		authStub = new OAuth2TokenValidationServiceStub(configCtx, serviceURL);
		client = authStub._getServiceClient();
		option = client.getOptions();

		List<Header> list = new ArrayList<Header>();
		Header header = new Header();
		header.setName("Authorization");
		header.setValue("Basic " + basicAuthValue);
		list.add(header);
		option.setProperty(
				org.apache.axis2.transport.http.HTTPConstants.HTTP_HEADERS,
				list);

		OAuth2TokenValidationRequestDTO request = new OAuth2TokenValidationRequestDTO();
		OAuth2TokenValidationRequestDTO_OAuth2AccessToken param = new OAuth2TokenValidationRequestDTO_OAuth2AccessToken();
		param.setIdentifier(token);
		param.setTokenType("bearer");
		request.setAccessToken(param);
		OAuth2TokenValidationResponseDTO response = authStub.validate(request);
		System.out.println(" Validity " + response.getValid());
		System.out.println(" User " + response.getAuthorizedUser());
		return response.getValid();

	}

}
