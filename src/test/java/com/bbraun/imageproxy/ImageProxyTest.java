package com.bbraun.imageproxy;

import org.junit.Ignore;
import org.junit.Test;

import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

/**
 * Test of the image proxy application
 * 
 * @author stefans
 */
@Ignore
public class ImageProxyTest {

	@Test
	public void testGetDefaultImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/article/PRID00000772") //
				.assertResponseHeaderEquals("Content-Type", "image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "17067");
	}

	@Test
	public void testGet01Image() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/01/article/PRID00000772") //
				.assertResponseHeaderEquals("Content-Type", "image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "12385");
	}

	@Test
	public void testGetDEImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/DE/article/PRID00000772") //
				.assertResponseHeaderEquals("Content-Type", "image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "16290");
	}

	@Test
	public void testGetNOImage() {
		new BBMWebServiceTestBuilder<>() // ØÏ
				.withNewRequest() //
				.doGet("http://images.bbraun.com/no/article/PRID00000772") //
				.assertResponseHeaderEquals("Content-Type", "image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "20822");
	}

	@Test
	public void testGetESImage() {
		new BBMWebServiceTestBuilder<>() //
				.withNewRequest() //
				.doGet("http://images.bbraun.com/es/article/PRID00000772") //
				.assertResponseHeaderEquals("Content-Type", "image/jpeg;charset=UTF-8") //
				.assertResponseHeaderEquals("Content-Length", "13098");
	}
}
