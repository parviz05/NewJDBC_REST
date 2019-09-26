package RestAsurePractices;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.junit.Assert.*;

public class Deserializing {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.base_url");
        RestAssured.port = Integer.parseInt(ConfigurationReader.getProperty("spartan.base_port"));
        RestAssured.basePath =ConfigurationReader.getProperty("spartan.base_path");
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }

    @Test
    public void Deserializing_Test(){


        //first way

      Spartan2 spartan=  get("/spartans/10").jsonPath()//.prettyPeek()
              .getObject("",Spartan2.class);
        System.out.println(spartan);


        //Second way we do not use map just use as and path the class where u want to store it
        Spartan2 sp2=get("/spartans/15")//.prettyPeek()
                .as(Spartan2.class);
        System.out.println(sp2);


    }
    @Test
    public void Add_new_spartan_NAME_Negative_test() {

        Spartan2 spartan = new Spartan2("Bale", "Female", 1223234545);


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(spartan)
                .when().post("/spartans/")
                .then()
                .statusCode(201)
        ;
    }

}
