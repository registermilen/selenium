package com.bbraun.hybris.occ.product;

import io.qameta.allure.*;
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
@Epic("OCC Webservice Tests")
@Feature("OCC Product endpoint Tests")
public class OCCProductRequestUSATest {

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get PRID by material number with AEM user.")
	@Story("Request PRID by material number with AEM user for en_US")
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
						"sapModifiedTime", "productReferences", "billOfMaterials", "localizedBkcTexts",
						"classifications", "marketingReleaseFeatureValue") //
				
				.assertResponseBodyByPathEquals("billOfMaterials.size() > 0", Boolean.TRUE) 
				.assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("classifications.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("marketingReleaseFeatureValue.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='image/jpeg'}.size() > 0", Boolean.TRUE) // min. one picture
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='application/pdf'}.size() > 0", Boolean.TRUE) // min. one document
		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Article by material number with AEM user.")
	@Story("Request article by material number with AEM user for en_US")
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
						"sapModifiedTime", "productReferences", "materialLocalDatas", "eanNumber", "uom",
						"classifications","localizedBkcTexts") //

				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='image/jpeg'}.size() > 0", Boolean.TRUE) // min. one picture
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='application/pdf'}.size() > 0", Boolean.TRUE) // min. one document
				.assertResponseBodyByPathEquals("materialLocalDatas.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("eanNumber.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("uom.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("classifications.size() > 0", Boolean.TRUE)

		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Image classification by material number with AEM user.")
	@Story("Request image classification by material number with AEM user for en_US")
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
