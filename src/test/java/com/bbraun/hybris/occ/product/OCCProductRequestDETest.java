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
						"sapModifiedTime", "productReferences", "billOfMaterials", "localizedBkcTexts",
                        "classifications", "marketingReleaseFeatureValue") //
				
				.assertResponseBodyByPathEquals("billOfMaterials.size() > 0", Boolean.TRUE)  //
				.assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("classifications.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("marketingReleaseFeatureValue.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='image/jpeg'}.size() > 0", Boolean.TRUE) // min. one picture
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='application/pdf'}.size() > 0", Boolean.TRUE) // min. one document

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
						"sapModifiedTime", "productReferences", "materialLocalDatas", "eanNumber", "uom", "localizedBkcTexts", "classifications") //

				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='image/jpeg'}.size() > 0", Boolean.TRUE) // min. one picture
				.assertResponseBodyByPathEquals("materialLocalDatas.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("eanNumber.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("uom.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("classifications.size() > 0", Boolean.TRUE)
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
