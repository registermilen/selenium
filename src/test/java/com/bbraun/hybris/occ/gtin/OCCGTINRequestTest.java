package com.bbraun.hybris.occ.gtin;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Test of the {@code /article/gtin} OCC webservice endpoint.
 * 
 * @author stuestde
 *
 */
public class OCCGTINRequestTest {

	@Test
	public void testGetArticleSoftaSeptByGtinDeAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "de_DE") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.gtin.softasept.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("code", "000000000003887138") //
				.assertResponseBodyByPathEquals("materialShortText", "SOFTASEPT-N UNCOLORED SPRAY \"DE\" 250ML") //
		;
	}
	
	@Test
	public void testGetUnknownArticleByGtinDeAEMUser() {
		new BBMWebServiceTestBuilder<>() //
		.withNewRequest() //
		.withBasicAuth("trusted_client", "secret") //
		.doAuthenticateOAuth2("${hybris.oauth.url}", //
				GrantType.PASSWORD, //
				"${hybris.oauth.username}", //
				"${hybris.oauth.password}") //
		.withNewRequest() //
		.withQueryParam("applicationKey", "AEM2015")//
		.withQueryParam("viewId", "de_DE") //
		.withQueryParam("access_token", "${accessToken}") //
		
		.doGet("${hybris.occ.gtin.unknown.url}") //
		
		.assertResponseContentType(ContentType.JSON) //
		.assertResponseBodyEqualsReference("OCC_GTIN_Unknown_Expected.json")
		;
	}
	
	@Test
	public void testGetArticleSoftaSeptByGtinDeAnonymousBarcodeAppUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withQueryParam("applicationKey", "BarcodeApp")//
				.withQueryParam("viewId", "de_DE") //

				.doGet("${hybris.occ.gtin.softasept.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("code", "000000000003887138") //
				.assertResponseBodyByPathEquals("materialShortText", "SOFTASEPT-N UNCOLORED SPRAY \"DE\" 250ML") //
		;
	}
}
