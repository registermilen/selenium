package com.bbraun.hybris.occ;

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
public class OCCProductRequestTest {

	@Test
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

				.doGet("${hybris.prid1270.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("url", "/p/PRID00001270") //
				.assertResponseBodyByPathEquals("code", "PRID00001270") //
				.assertResponseBodyEqualsReference("OCC_PRID1270_Expected.json", "sapModifiedTime") //
		;
	}

	@Test
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

				.doGet("${hybris.prid1270.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("resourceStatus", "NOT_MODIFIED") //
				.assertResponseBodyByPathEquals("code", "PRID00001270") //
		;
	}

	@Test
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

				.doGet("${hybris.BPR000000000000000100007757500000.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("OCC_BPR000000000000000100007757500000_Expected.json") //
		;
	}

	@Test
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

				.doGet("${hybris.BPG000000000000000100006550700000.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyEqualsReference("OCC_BPG000000000000000100006550700000_Expected.json") //
		;
	}
}