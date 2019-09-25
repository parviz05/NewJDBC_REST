package RestAsurePractices;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.junit.Assert.*;



public class HamCrestLibrary {


    /*
    hamCrest is testing library tonprovide many built in testiing methods
    that comes form hamcrest Matchers
     */


    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://18.208.250.251";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }
    @Test
    public void Calculator_TEST(){


        int a=10, b=20;

        assertEquals(30,10+20);

        assertThat(20,greaterThan(a+b));

    }
    @Test
    public void DoingAssertionWithHamCrestLibrary(){

        given().pathParam("id", 2).
           when()
                        .get("/spartans/{id}").
           then().
                        assertThat().
                contentType(ContentType.JSON).
                statusCode(200).
                        body("id",equalTo(2)).
                        body("gender",equalTo("Male")).
                        body("phone",hasToString("4218971348"));



             //response.prettyPrint();
    }

}
