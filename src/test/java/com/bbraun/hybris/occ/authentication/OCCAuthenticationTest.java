package com.bbraun.hybris.occ.authentication;

import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

import io.restassured.http.ContentType;

/**
 * Tests of the OAuth 2.0 authentication endpoint of Hybris OCC Webservices
 * 
 * @author stuestde
 *
 */
@Epic("OCC Webservice Tests")
@Feature("OCC Authentication Tests")
public class OCCAuthenticationTest {

	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

	@TestProperty("hybris.oauth.username")
	private static String oAuthUsername;

	@TestProperty("hybris.oauth.password")
	private static String oAuthPassword;

	@TestProperty("hybris.oauth.url")
	private static String oAuthUrl;

	/**
	 * Tests OAuth authentication using GET request and JSON response
	 */
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with HTTP GET and expected JSON response.")
	@Story("Request OAuth authentication token with HTTP GET and expected JSON response.")
	public void testOAuthWithGetRequestJSONSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withQueryParam("grant_type", "password") //
				.withQueryParam("username", oAuthUsername) //
				.withQueryParam("password", oAuthPassword) //

				.doGet(oAuthUrl) //

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
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with HTTP POST and expected JSON response.")
	@Story("Request OAuth authentication token with HTTP POST and expected JSON response.")
	public void testOAuthWithPostRequestJSONSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withQueryParam("username", oAuthUsername) //
				.withQueryParam("password", oAuthPassword) //

				.doGet(oAuthUrl) //
				
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
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with HTTP GET and expected XML response.")
	@Story("Request OAuth authentication token with HTTP GET and expected XML response.")
	public void testOAuthWithGetRequestXMLSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/xml")//
				.withQueryParam("grant_type", "password") //
				.withQueryParam("username", oAuthUsername) //
				.withQueryParam("password", oAuthPassword) //

				.doGet(oAuthUrl) //

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
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with HTTP POST and expected XML response.")
	@Story("Request OAuth authentication token with HTTP POST and expected XML response.")
	public void testOAuthWithPostRequestXMLSuccess() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/xml")//
				.withPostParam("grant_type", "password") //
				.withQueryParam("username", oAuthUsername) //
				.withQueryParam("password", oAuthPassword) //

				.doGet(oAuthUrl) //

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
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with wrong Basic Auth credentials")
	@Story("Request OAuth authentication with wrong Basic Auth credentials.")
	public void testOAuthWithWrongBasicAuthCredentials() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "wrong_password") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withQueryParam("username", oAuthUsername) //
				.withQueryParam("password", oAuthPassword) //

				.doGet(oAuthUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_UNAUTHORIZED) //
				.assertResponseBodyByPathEquals("error", "unauthorized")
				.assertResponseBodyByPathEquals("error_description", "Bad credentials");
	}

	/**
	 * Tests OAuth authentication with wrong Hybris credentials
	 */
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: OAuth authentication with wrong webservice user credentials")
	@Story("Request OAuth authentication with wrong webservice user credentials.")
	public void testOAuthWithWrongHybrisCredentials() {

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.withHeader("Accept", "application/json")//
				.withPostParam("grant_type", "password") //
				.withQueryParam("username", "WRONGUSERNAME") //
				.withQueryParam("password", "WRONGPASSWORD") //

				.doGet(oAuthUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseHttpStatusCode(HttpStatus.SC_BAD_REQUEST) //
				.assertResponseBodyByPathEquals("error", "invalid_grant")
				.assertResponseBodyByPathEquals("error_description", "Bad credentials");
	}
}
