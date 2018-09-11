package com.bbraun.imageproxy;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import org.junit.Ignore;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

/**
 * Test of the image proxy application
 * 
 * @author stefans
 */
@Epic("Microservice Tests")
@Feature("Image Proxy Tests")
public class ImageProxyTest {

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get default image")
	@Story("Request the default image via Image Proxy for product PRID00000772")
	public void testGetDefaultImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/article/PRID00000772") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "17067");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get global image")
	@Story("Request the global image via Image Proxy for product PRID00000772")
	public void testGet01Image() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/01/article/PRID00000772") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "17067");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get product image for Germany")
	@Story("Request the german image via Image Proxy for product PRID00000772")
	public void testGetDEImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/DE/article/PRID00000772") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "16290");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get product image for Norway")
	@Story("Request the norwegian image via Image Proxy for product PRID00000772")
	public void testGetNOImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/no/article/PRID00000772") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "20822");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get product image for Spain")
	@Story("Request the spain image via Image Proxy for product PRID00000772")
	public void testGetESImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/es/article/PRID00000772") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "13298");
	}
}
