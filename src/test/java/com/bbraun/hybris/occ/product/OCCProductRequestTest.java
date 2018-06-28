package com.bbraun.hybris.occ.product;

import io.qameta.allure.*;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests of Hybris OCC {@code /products} webservice operation.
 * 
 * @see https://collaboration.bbraun.com/display/HYBRIS/OCC+Webservice+Documentation#OCCWebserviceDocumentation-RequestProductData
 * 
 * @author stuestde
 */
@Epic("OCC Webservice Tests")
@Feature("OCC Product endpoint Tests")
public class OCCProductRequestTest {

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get PRID by material number with AEM user.")
	@Story("Request PRID by material number with AEM user for en_01")
	public void testOCCProductRequestPRIDWithAEMUserEn01() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.prid1270.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("url", "/p/PRID00001270") //
				.assertResponseBodyByPathEquals("code", "PRID00001270") //
				.assertResponseBodyEqualsReference("OCC_PRID1270_Expected.json", "sapModifiedTime", "productReferences","localizedBkcTexts") //
				.assertResponseBodyByPathEquals("localizedBkcTexts.size() > 0", Boolean.TRUE)
				.assertResponseBodyByPathEquals("productReferences.findAll {p -> p.target.mimeDetails=='application/pdf'}.size() > 0", Boolean.TRUE) // min. one document) //
		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get PRID by material number and modifiedSince flag with AEM user.")
	@Story("Request PRID by material number and modifiedSince flag with AEM user for en_01")
	public void testOCCProductRequestWithModifiedSinceFlag() {
		long unixTimestampNow = System.currentTimeMillis() / 1000;
		long unixTimestamp5MinutesAgo = unixTimestampNow - (5 * 60);

		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01") //
				.withQueryParam("access_token", "${accessToken}") //
				.withQueryParam("modifiedSince", unixTimestamp5MinutesAgo) //

				.doGet("${hybris.occ.product.prid1270.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("resourceStatus", "NOT_MODIFIED") //
				.assertResponseBodyByPathEquals("code", "PRID00001270") //
		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get BPR by material number with AEM user.")
	@Story("Request BPR by material number with AEM user for en_01")
	public void testOCCProductRequestProductRelatedBPRWithAEMUserEn01() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.BPR000000000000000100007757500000.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("OCC_BPR000000000000000100007757500000_Expected.json") //
		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Image classification by material number with AEM user.")
	@Story("Request image classification by material number with AEM user for en_01")
	public void testOCCProductRequestImageBPGWithAEMUserEn01() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.BPG000000000000000100006550700000.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("OCC_BPG000000000000000100006550700000_Expected.json") //
		;
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get PRID and URL language by material number with AEM user.")
	@Story("Request PRID with and URL language by material number with AEM user for ru_RU")
	public void testOCCProductRequestUrlLanguageTestForRussia() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("${hybris.oauth.url}", //
						GrantType.PASSWORD, //
						"${hybris.oauth.username}", //
						"${hybris.oauth.password}") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "ru_RU") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("${hybris.occ.product.prid988.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathNotNull("additionalName") //
		;
	}
}