package com.bbraun.imageproxy;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

/**
 * Test of the image proxy application
 * 
 * @author stefans
 */
@Ignore
@Epic("Microservice Tests")
@Feature("Image Proxy Tests")
@RunOnStage(stages = {"PRD"})
public class ImageProxyTest {

	@Rule
	public RunOnStageRule rule = new RunOnStageRule();

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get default image")
	@Story("Request the default image via Image Proxy for product PRID00010216")
	public void testGetDefaultImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/article/PRID00010216") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "16439");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get global image")
	@Story("Request the global image via Image Proxy for product PRID00010216")
	public void testGet01Image() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/01/article/PRID00010216") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "16439");
	}

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Get product image for Germany")
	@Story("Request the german image via Image Proxy for product PRID00010216")
	public void testGetDEImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/DE/article/PRID00010216") //
				.assertResponseContentType("image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "16439");
	}
}
