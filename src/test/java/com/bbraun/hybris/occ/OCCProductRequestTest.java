package com.bbraun.hybris.occ;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMOAuth2WebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMOAuth2WebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests of Hybris OCC {@code /products} webservice operation.
 * 
 * @author stuestde
 *
 */
public class OCCProductRequestTest {

	@Test
	public void testOCCProductsAEMUser() {
		new BBMOAuth2WebServiceTestBuilder() //
				.withNewRequest() //
				.withBasicAuth("trusted_client", "secret") //
				.doAuthenticateOAuth2("https://qas-ws.hybris.bbraun.com/authorizationserver/oauth/token", //
						GrantType.PASSWORD, "bbraunoccs", "bbraunoccs", "accessToken")

				.withNewRequest() //
				.withQueryParam("applicationKey", "AEM2015")//
				.withQueryParam("viewId", "en_01")
				.withQueryParam("access_token", "cc85ec80-20b1-4217-8ed4-980255bdd81a") //
				.doGet("https://qas-ws.hybris.bbraun.com/bbraunocc/v2/bbraun/products/PRID00001252") //
				
				.assertResponseContentType(ContentType.JSON)

		;
	}

}
