package com.bbraun.hybris.occ.xref;


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
import io.restassured.http.ContentType;

/**
 * Tests the /materialtranscoding endpoint.
 *
 * @author stuestde
 */
@Epic("OCC Webservice Tests")
@Feature("OCC Materialtranscoding Endpoint Tests")
@RunOnStage(stages = {"QAS"})
public class OCCMaterialTranscodingRequestTest {

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

    @TestProperty("hybris.occ.materialtranscoding.url")
    private static String materialTranscodingWsUrl;

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Transcode single competitor material")
    @Story("Transcode a single competitor material with SGDA user.")
    public void testTranscodeSingleCompetitorMaterialWithSGDAUser() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withBasicAuth("trusted_client", "secret") //
                .doAuthenticateOAuth2(oAuthUrl, //
                        BBMWebServiceTestBuilder.GrantType.PASSWORD, //
                        oAuthUsername, //
                        oAuthPassword) //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SGDA")//
                .withQueryParam("fields", "FULL")//
                .withQueryParam("viewId", "de_DE")//
                .withQueryParam("access_token", "${accessToken}") //
                .withQueryParam("materialIds", "160-775") //

                .doGet(materialTranscodingWsUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("materials.size() == 3", Boolean.TRUE)  //
                .assertResponseBodyByPathEquals("materials[0].materialNumber", "160-775") //
                .assertResponseBodyByPathEquals("materials[0].vendorId", "9800001183") //
                .assertResponseBodyByPathEquals("materials[0].vendorName", "George Tiemann & Co") //
                .assertResponseBodyByPathEquals("materials[0].references.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials[0].code", "BH442R") //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials[0].materialsTexts.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.salesClass") //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.salesClassCode") //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.productHierarchy") //
        ;
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Transcode single B. Braun material")
    @Story("Transcode a single B. Braun material with SGDA user.")
    public void testTranscodeSingleBBMMaterialWithSGDAUser() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withBasicAuth("trusted_client", "secret") //
                .doAuthenticateOAuth2(oAuthUrl, //
                        BBMWebServiceTestBuilder.GrantType.PASSWORD, //
                        oAuthUsername, //
                        oAuthPassword) //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SGDA")//
                .withQueryParam("fields", "FULL")//
                .withQueryParam("viewId", "de_DE")//
                .withQueryParam("access_token", "${accessToken}") //
                .withQueryParam("materialIds", "BH110R") //

                .doGet(materialTranscodingWsUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("materials.size() == 1", Boolean.TRUE)  //
                .assertResponseBodyByPathEquals("materials[0].materialNumber", "BH110R") //
                .assertResponseBodyByPathEquals("materials[0].vendorName", "B. Braun") //
                .assertResponseBodyByPathEquals("materials[0].references.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials[0].code", "BH110R") //
                .assertResponseBodyByPathEquals("materials[0].references[0].transcodedMaterials[0].materialsTexts.size() > 0", Boolean.TRUE) //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.salesClass") //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.salesClassCode") //
                .assertResponseBodyByPathNotNull("materials[0].references[0].transcodedMaterials[0].materialsTexts.productHierarchy") //
        ;
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Transcode multiple materials")
    @Story("Transcode multiple B. Braun and competitor materials with SGDA user.")
    public void testTranscodeMultipleaterialWithSGDAUser() {
        new BBMWebServiceTestBuilder<>() //
                .withNewRequest() //
                .withBasicAuth("trusted_client", "secret") //
                .doAuthenticateOAuth2(oAuthUrl, //
                        BBMWebServiceTestBuilder.GrantType.PASSWORD, //
                        oAuthUsername, //
                        oAuthPassword) //
                .withNewRequest() //
                .withQueryParam("applicationKey", "SGDA")//
                .withQueryParam("fields", "FULL")//
                .withQueryParam("viewId", "de_DE")//
                .withQueryParam("access_token", "${accessToken}") //
                .withQueryParam("materialIds", "BH110R,34-702-13-07") //

                .doGet(materialTranscodingWsUrl) //

                .assertResponseHttpStatusCode(HttpStatus.SC_OK) //
                .assertResponseContentType(ContentType.JSON) //
                .assertResponseBodyByPathEquals("materials.size() == 2", Boolean.TRUE)  //
        ;
    }

}
