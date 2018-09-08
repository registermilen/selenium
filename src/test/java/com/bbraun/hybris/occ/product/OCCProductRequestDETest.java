package com.bbraun.hybris.occ.product;

import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import io.qameta.allure.*;
import org.junit.ClassRule;
import org.junit.Ignore;
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
@Epic("OCC Webservice Tests")
@Feature("OCC Product endpoint Tests")
public class OCCProductRequestDETest {

	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

	@TestProperty("hybris.oauth.username")
	private static String oAuthUsername;

	@TestProperty("hybris.oauth.password")
	private static String oAuthPassword;

	@TestProperty("hybris.oauth.url")
	private static String oAuthUrl;

	@TestProperty("hybris.occ.product.prid1011.url")
	private static String prid1011Url;

	@TestProperty("hybris.occ.product.prid1011.expectedFile")
	private static String prid1011ExpectedFile;

	@TestProperty("hybris.occ.product.article3887138.url")
	private static String articleUrl;

	@TestProperty("hybris.occ.product.article3887138.expectedFile")
	private static String articleExpectedFile;

	@TestProperty("hybris.occ.product.sofasept.bpg.url")
	private static String softaseptBpgUrl;

	@TestProperty("hybris.occ.product.sofasept.bpg.expectedFile")
	private static String softaseptBgpExpectedFile;

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get PRID by material number with AEM user.")
	@Story("Request PRID by material number with AEM user for de_DE")
	public void testGetPRIDIntrocanSafetyDEWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2(oAuthUrl, //
						GrantType.PASSWORD, //
						oAuthUsername, //
						oAuthPassword) //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "de_DE") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet(prid1011Url) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference(prid1011ExpectedFile,
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

	@Ignore
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Article by material number with AEM user.")
	@Story("Request article by material number with AEM user for de_DE")
	public void testGetArticleSoftaseptDEWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2(oAuthUrl, //
						GrantType.PASSWORD, //
						oAuthUsername, //
						oAuthPassword) //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "de_DE") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet(articleUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference(articleExpectedFile,
						"sapModifiedTime", "productReferences", "materialLocalDatas", "eanNumber", "uom", "localizedBkcTexts", "classifications") //

				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='image/jpeg'}.size() > 0", Boolean.TRUE) // min. one picture
				.assertResponseBodyByPathEquals("materialLocalDatas.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("eanNumber.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("uom.size() > 0", Boolean.TRUE)
                .assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("classifications.size() > 0", Boolean.TRUE)
		;
	}

	@Ignore
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Image classification by material number with AEM user.")
	@Story("Request image classification by material number with AEM user for de_DE")
	public void testGetBPGImageClassificationSoftaseptDEWithAEMUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2(oAuthUrl, //
						GrantType.PASSWORD, //
						oAuthUsername, //
						oAuthPassword) //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "de_DE") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet(softaseptBpgUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyPartEqualsReference("classifications",
						softaseptBgpExpectedFile) //
		;
	}
}
