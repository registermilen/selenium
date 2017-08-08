package com.bbraun.hybris.occ;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests of Hybris OCC {@code /products} webservice operation.
 * 
 * @author stuestde
 */
public class OCCProductRequestTest {

	@Test
	public void testOCCProductOperationWithAEMUserEn01() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token", //
						GrantType.PASSWORD, //
						"bbraunoccs", //
						"bbraunoccs") //
				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01") //
				.withQueryParam("access_token", "${accessToken}") //

				.doGet("https://qas-ws.hybris.bbraun.com/bbraunocc/v2/bbraun/products/PRID00001270") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("url", "/p/PRID00001270") //
				.assertResponseBodyByPathEquals("code", "PRID00001270") //
				.assertResponseBodyEqualsReference("OCC_PRID1270_Expected.json", "sapModifiedTime") //
		;
	}
}