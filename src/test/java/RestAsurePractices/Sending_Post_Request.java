package RestAsurePractices;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.junit.Assert.*;

public class Sending_Post_Request {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://18.208.250.251";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }
    @Test
    public void Single_spartan_LogAll(){


        given()
                .pathParam("id",3)
                // to be able to see all request info in console
                .log().all()
                .when()
                .get("/spartans/{id}")
                .then()
                //on the validation part we put log to be able to see fo response information on console
                //there multiple option to see exactlynwhen we want to se the log
                // in bellow example we only want osee the response log info any validation failed
                .log().all()
                .log().ifValidationFails()
                .statusCode(200)
        ;


    }

    //Sending post request
    @Test
    public void Add_new_spartan_withStringBody(){
        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body("{\n" +
                        "  \n" +
                        "    \"name\": \"Ali\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"phone\": 2189766768\n" +
                        "}")
                .when()
                .post("/spartans/")
                .then()
                .log().all()
                .statusCode(201)
                .body("success",is ("A Spartan is Born!"))
                .body("data.gender",equalToIgnoringCase("Male"))
                .body("data.phone",hasToString("2189766768"))

        ;


    }
}
