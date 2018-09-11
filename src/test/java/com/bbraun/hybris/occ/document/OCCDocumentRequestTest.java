package com.bbraun.hybris.occ.document;

import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

@Epic("OCC Webservice Tests")
@Feature("OCC Document endpoint Tests")
public class OCCDocumentRequestTest {

    @ClassRule
    public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

    @TestProperty("hybris.aagmdb.oauth.username")
    private static String oAuthAagMdbUsername;

    @TestProperty("hybris.aagmdb.oauth.password")
    private static String oAuthAagMdbPassword;

    @TestProperty("hybris.oauth.url")
    private static String oAuthUrl;

    @TestProperty("hybris.occ.bbraunsapdocuments.url")
    private static String documentWsEndpoint;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Get Document with Mediendatenbank user")
    @Story("Request a document by id with Mediendatenbank user for default view config")
    public void testOCCDocumentRequestWithAAGMDBUser() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withBasicAuth("trusted_client", "secret") //
                .doAuthenticateOAuth2(oAuthUrl, //
                        BBMWebServiceTestBuilder.GrantType.PASSWORD, //
                        oAuthAagMdbUsername, //
                        oAuthAagMdbPassword) //
                .withNewRequest() //
                .withQueryParam("applicationKey", "AAGMDB")//
                .withQueryParam("access_token", "${accessToken}") //

                .doGet(documentWsEndpoint) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType("application/pdf")
                .assertResponseHeaderEquals("Content-Length", "825235") //
                .assertResponseHeaderEquals("Content-Disposition", "form-data; name=\"inline\"; filename=\"C63401 11-11.pdf\"") //
        ;
    }

}
