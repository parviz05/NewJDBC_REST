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


public class Spartan_Rest_API_Sunday {



    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.base_url");
        RestAssured.port = Integer.parseInt(ConfigurationReader.getProperty("spartan.base_port"));
        RestAssured.basePath =ConfigurationReader.getProperty("spartan.base_path");
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }



@Test
    public void All_Spartan_test(){

        given().accept(ContentType.JSON)
                .when()
                .get("/spartans/").prettyPeek()
                .then()
                .statusCode(200)
                .body("[1].name",equalTo("Nels"))// if root is json arry u need to put [] and path index
                .body("[2].gender",is("Male"))     // verify the gender
                .body("name",hasItem("Ali"))
                .header("Transfer-Encoding","chunked") // verify the header
                .header("Date",notNullValue());            //check the date header is not null value


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
}
