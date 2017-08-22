package com.bbraun.hybris.occ.product;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests the /products endpoint with AEM user and "de_DE" view configuration.
 * 
 * Test Product: Introcan Safety PRID00001011
 * Test Article: Softasept  3887138
 * 
 * @author stuestde
 */
public class OCCProductRequestDETest {

	@Test
	public void testGetPRIDIntrocanSafetyDEWithAEMUser() {
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

				.doGet("${hybris.occ.product.prid1011.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("${hybris.occ.product.prid1011.expectedFile}",
						"sapModifiedTime") //
		;
	}

	@Test
	public void testGetArticleSoftaseptDEWithAEMUser() {
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

				.doGet("${hybris.occ.product.article3887138.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("${hybris.occ.product.article3887138.expectedFile}",
						"sapModifiedTime") //
		;
	}

	@Test
	public void testGetBPGImageClassificationSoftaseptDEWithAEMUser() {
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

				.doGet("${hybris.occ.product.sofasept.bpg.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyPartEqualsReference("classifications",
						"${hybris.occ.product.sofasept.bpg.expectedFile}") //
		;
	}
}
