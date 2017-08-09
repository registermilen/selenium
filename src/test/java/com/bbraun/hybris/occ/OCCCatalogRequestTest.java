package com.bbraun.hybris.occ;

import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder.GrantType;

import io.restassured.http.ContentType;

/**
 * Tests of Hybris OCC {@code /catalogs} webservice operation.
 * 
 * @see https://collaboration.bbraun.com/display/HYBRIS/OCC+Webservice+Documentation#OCCWebserviceDocumentation-RequestCatalogStructure
 * 
 * @author stuestde
 */
public class OCCCatalogRequestTest {

	@Test
	public void testOCCCatalogRequestWithAEMUserEn01() {
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

				.doGet("${hybris.occ.catalog.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathNotNull("categories") //
		;
	}
	
}