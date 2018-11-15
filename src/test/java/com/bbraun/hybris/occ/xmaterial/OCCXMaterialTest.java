package com.bbraun.hybris.occ.xmaterial;


import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ws.BBMWebServiceTestBuilder;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Tests the /competitormaterial endpoint.
 *
 */
@Epic("OCC Webservice Tests")
@Feature("OCC XMaterial Endpoint Tests")
@RunOnStage(stages = {"LOCAL", "QAS"})
public class OCCXMaterialTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @ClassRule
    public static TestPropertyRule testPropertiesRule = new TestPropertyRule();

    @TestProperty("hybris.luser.oauth.username")
    private static String oAuthUsername;

    @TestProperty("hybris.luser.oauth.password")
    private static String oAuthPassword;

    @TestProperty("hybris.oauth.url")
    private static String oAuthUrl;

    @TestProperty("hybris.occ.xmaterial.url")
    private static String xMaterialUrl;

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Data for a competitor material.")
    @Story("Retrieve data for a competiror material with SAM user.")
    public void testTranscodeSingleCompetitorMaterialWithSAMUser() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withBasicAuth("trusted_client", "secret") //
                .doAuthenticateOAuth2(oAuthUrl, //
                        BBMWebServiceTestBuilder.GrantType.PASSWORD, //
                        oAuthUsername, //
                        oAuthPassword) //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SAM")//
                .withQueryParam("materialIds", "nonexisting,34-702-13-07,V317H")//
                .withQueryParam("access_token", "${accessToken}") //

                .doGet(xMaterialUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("materials.size() > 1", Boolean.TRUE)  //
                .assertResponseBodyByPathEquals("materials[0].materialNumber", "V317H") //
                .assertResponseBodyByPathEquals("materials[0].competitorName", "Ethicon Inc") //
                .assertResponseBodyByPathEquals("materials[0].materialsTexts.size() == 1", Boolean.FALSE) //
                .assertResponseBodyByPathEquals("materials[0].materialsTexts[1].isoCode", "DE") //
                .assertResponseBodyByPathEquals("materials[0].materialsTexts[1].text", "VICRYL VIO GEFL 3/2-0 70CM SH PLUS") //
        ;
    }

}
