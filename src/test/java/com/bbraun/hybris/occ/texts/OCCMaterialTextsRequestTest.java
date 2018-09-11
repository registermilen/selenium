package com.bbraun.hybris.occ.texts;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

@Epic("OCC Webservice Tests")
@Feature("OCC material text Endpoint Tests")
@RunOnStage(stages = {"QAS"})
public class OCCMaterialTextsRequestTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @ClassRule
    public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

    @TestProperty("hybris.occ.materialtexts.url")
    private static String materialTextWsUrl;

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Get BKC text for single B. Braun material.")
    @Story("Get BKC text of type XNAME in multiple languages for material.")
    public void testGetBkcTextMultipleLanguagesWithSAPPOUserWithoutAuthentication() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SAPPO")//
                .withQueryParam("materialIds", "BC017R") //
                .withQueryParam("textTypeFilter", "XNAME") //
                .withQueryParam("viewIdFilter", "de_DE,en_01") //

                .doGet(materialTextWsUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("products.size() == 1", Boolean.TRUE)  //
                .assertResponseBodyByPathEquals("products[0].code", "bc017r")  //
                .assertResponseBodyByPathEquals("products[0].size() == 2", Boolean.TRUE) //
                .assertResponseBodyByPathEquals("products[0].localizedBkcTexts.size() > 0", Boolean.TRUE) //
        ;
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Get BKC text for multiple B. Braun material.")
    @Story("Get BKC text of type XNAME and XBESC in multiple languages for multiple materials.")
    public void testGetBkcTextMultipleLanguagesMultipleProductsWithSAPPOUserWithoutAuthentication() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SAPPO")//
                .withQueryParam("materialIds", "BC017R,BH110R") //
                .withQueryParam("textTypeFilter", "XNAME,XBESC") //
                .withQueryParam("viewIdFilter", "de_DE,en_01") //

                .doGet(materialTextWsUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("products.size() == 2", Boolean.TRUE)  //
                .assertResponseBodyByPathEquals("products[0].localizedBkcTexts.size() > 0", Boolean.TRUE) //
        ;
    }
}
