package apiCore;


import fileReaders.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import seleniumCore.CoreConstants;

public class CoreAPI {

    @BeforeMethod(alwaysRun = true)
    public void coreAPI() {
        PropertyReader.setProperty(CoreConstants.pathToPropertyFile);
    }

    protected static RequestSpecification getRequestSpecification(String url) {
        return RestAssured.given().baseUri(url);
    }

    protected static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {

        }
    }

    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeTest
    public static void createRequestSpecification() {
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("http://api.zippopotam.us")
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }


    protected RequestSpecification getReqSpec() {
        return requestSpecification;
    }

    protected ResponseSpecification getResSpec() {
        return responseSpecification;
    }

}
