package com.bbraun.hybris.occ;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

import io.restassured.http.ContentType;

/**
 * Tests of the OAuth 2.0 authentication endpoint of Hybris OCC Webservices
 * 
 * @author stuestde
 *
 */
public class OCCAuthenticationTest {

	/**
	 * Tests OAuth authentication using GET request and JSON response
	 */
	@Test
	public void testOAuthWithGetRequestJSONSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withQueryParam("grant_type", "password") //
				.withQueryParam("username", "bbraunoccs") //
				.withQueryParam("password", "bbraunoccs") //

				.doGet("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_OK) //
				.assertResponseBodyByPathNotNull("access_token") //
				.assertResponseBodyByPathNotNull("expires_in") //
				.assertResponseBodyByPathEquals("token_type", "bearer") //
		;
	}

	/**
	 * Tests OAuth authentication using POST request and JSON response
	 */
	@Test
	public void testOAuthWithPostRequestJSONSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withPostParam("username", "bbraunoccs") //
				.withPostParam("password", "bbraunoccs") //

				.doPost("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_OK) //
				.assertResponseBodyByPathNotNull("access_token") //
				.assertResponseBodyByPathNotNull("expires_in") //
				.assertResponseBodyByPathEquals("token_type", "bearer") //
		;
	}

	/**
	 * Tests OAuth authentication using GET request and XML response
	 */
	@Test
	public void testOAuthWithGetRequestXMLSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/xml")//
				.withQueryParam("grant_type", "password") //
				.withQueryParam("username", "bbraunoccs") //
				.withQueryParam("password", "bbraunoccs") //

				.doGet("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.XML) //
				.assertResponseHttpStatusCode(HttpStatus.SC_OK) //
				.assertResponseBodyByPathNotNull("access_token") //
				.assertResponseBodyByPathNotNull("expires_in") //
		;
	}

	/**
	 * Tests OAuth authentication using POST request and XML response
	 */
	@Test
	public void testOAuthWithPostRequestXMLSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/xml")//
				.withPostParam("grant_type", "password") //
				.withPostParam("username", "bbraunoccs") //
				.withPostParam("password", "bbraunoccs") //

				.doPost("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.XML) //
				.assertResponseHttpStatusCode(HttpStatus.SC_OK) //
				.assertResponseBodyByPathNotNull("access_token") //
				.assertResponseBodyByPathNotNull("expires_in") //
		;
	}

	/**
	 * Tests OAuth authentication with wrong Basic auth credentials
	 */
	@Test
	public void testOAuthWithWrongBasicAuthCredentials() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "wrong_password") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withPostParam("username", "bbraunoccs") //
				.withPostParam("password", "bbraunoccs") //

				.doPost("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_UNAUTHORIZED) //
				.assertResponseBodyByPathEquals("error", "unauthorized")
				.assertResponseBodyByPathEquals("error_description", "Bad credentials");
	}

	/**
	 * Tests OAuth authentication with wrong Hybris credentials
	 */
	@Test
	public void testOAuthWithWrongHybrisCredentials() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withPostParam("username", "bbraunoccs") //
				.withPostParam("password", "WRONGPASSWORD") //

				.doPost("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_BAD_REQUEST) //
				.assertResponseBodyByPathEquals("error", "invalid_grant")
				.assertResponseBodyByPathEquals("error_description", "Bad credentials");
	}
}
