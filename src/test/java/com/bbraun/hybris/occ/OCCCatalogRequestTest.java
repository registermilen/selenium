package com.bbraun.hybris.occ;

import io.qameta.allure.*;
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
@Epic("OCC Webservice Tests")
@Feature("OCC Catalog endpoint Tests")
public class OCCCatalogRequestTest {

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Catalog by id with AEM user.")
	@Story("Request Catalog by id with AEM user for en_01")
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

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get Catalog by id with additional name for Russia and AEM user.")
	@Story("Request Catalog by id with AEM user for en_01 with additional name for Russia")
	public void testOCCCatalogRequestWithAdditionalNameForRussia() {
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

				.doGet("${hybris.occ.catalog.url}") //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathNotNull("categories") //
				.assertResponseBodyByPathNotNull("categories[0].supercategories[0].additionalName") //
		;
	}
}