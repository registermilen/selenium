package com.bbraun.hybris.occ.gtin;

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
 * Test of the {@code /article/gtin} OCC webservice endpoint.
 * 
 * @author stuestde
 */
@Epic("OCC Webservice Tests")
@Feature("OCC GTIN endpoint Tests")
public class OCCGTINRequestTest {

	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

	@TestProperty("hybris.oauth.username")
	private static String oAuthUsername;

	@TestProperty("hybris.oauth.password")
	private static String oAuthPassword;

	@TestProperty("hybris.oauth.url")
	private static String oAuthUrl;

	@TestProperty("hybris.occ.gtin.softasept.url")
	private static String gtinSoftaseptUrl;

	@TestProperty("hybris.occ.gtin.unknown.url")
	private static String gtinUnknownProductUrl;

	@Ignore
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Get article by GTIN")
	@Story("Request article by GTIN with AEM user for de_DE")
	public void testGetArticleSoftaSeptByGtinDeAEMUser() {
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

				.doGet(gtinSoftaseptUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("code", "000000000003887138") //
				.assertResponseBodyByPathEquals("materialShortText", "SOFTASEPT-N UNCOLORED SPRAY \"DE\" 250ML") //
		;
	}

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Get unknown article by GTIN")
	@Story("Request unknown article by GTIN with AEM user for de_DE")
	public void testGetUnknownArticleByGtinDeAEMUser() {
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
		
		.doGet(gtinUnknownProductUrl) //
		
		.assertResponseContentType(ContentType.JSON) //
		.assertResponseBodyEqualsReference("OCC_GTIN_Unknown_Expected.json")
		;
	}

	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Get article by GTIN with anonymous user.")
	@Story("Request article by GTIN with anonymous user for de_DE")
	public void testGetArticleSoftaSeptByGtinDeAnonymousBarcodeAppUser() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.withQueryParam("applicationKey", "BarcodeApp")//
				.withQueryParam("viewId", "de_DE") //

				.doGet(gtinSoftaseptUrl) //

				.assertResponseContentType(ContentType.JSON) //
				.assertResponseBodyByPathEquals("code", "000000000003887138") //
				.assertResponseBodyByPathEquals("materialShortText", "SOFTASEPT-N UNCOLORED SPRAY \"DE\" 250ML") //
		;
	}
}
