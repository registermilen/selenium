package com.bbraun.hybris.occ.product;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests the /products endpoint with AEM user and "en_us" view configuration.
 * 
 * Test Product: Introcan Safety PRID00001011
 * Test Article: Introcan Safety 4251611-02
 * 
 * @author stuestde
 */
public class OCCProductRequestUSATest {

	@Test
	public void testGetPRIDIntrocanSafetyUSWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_us") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.prid1011.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("${hybris.occ.product.prid1011.expectedFile}",
						"sapModifiedTime") //
		;
	}

	@Test
	public void testGetArticleIntrocanSafetyUSWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_us") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.article425161102.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("${hybris.occ.product.article425161102.expectedFile}",
						"sapModifiedTime") //
		;
	}

	@Test
	public void testGetBPGImageClassificationIntrocanSafetyUSWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_us") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.introcan.bpg.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyPartEqualsReference("classifications",
						"${hybris.occ.product.introcan.bpg.expectedFile}") //
		;
	}
}
