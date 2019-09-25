package RestAsurePractices;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

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
    public void Single_spartan_LogAll() {


        given()
                .pathParam("id", 3)
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

    /*
    This is one way that we familiar
     */
    @Test
    public void Add_new_spartan_withStringBody() {
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
                .body("success", is("A Spartan is Born!"))
                .body("data.gender", equalToIgnoringCase("Male"))
                .body("data.phone", hasToString("2189766768"))

        ;


    }

    /*
    This is second way we create Map<> and according to key and value we pasth all data
    as a json format and we path the map object as a body
    in that part we need Serialization to be able to convert our java code to json format
    or we can use DeSerialization to be able to convert json format to java code

    There two popular library for serialization and DeSerialization
    1)-Jackson DataBind
    2)-GSon

    I prefer Jackson DataBind

    therefore We need to serialization Jackson databind plugin in our pom.XML jar file


         */
    @Test
    public void Add_new_spartan_withMapAsBody() {
        //map is interface        hasMap is implementation
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", "Parviz");
        bodyMap.put("age", "32");
        bodyMap.put("gender", "Male");
        bodyMap.put("phone", "8677866834");

        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(bodyMap)
                .when()
                .post("/spartans/")
                .then()
                .log().all()
                .statusCode(201)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", equalToIgnoringCase("Parviz"))
                .body("data.gender", equalToIgnoringCase("Male"))
                .body("data.phone", hasToString("8677866834"))

        ;


    }

    @Test
    public void Add_new_spartan_withPojoAsBody() {

        Spartan spartan = new Spartan("Bulud", "Female", 1223234545);


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(spartan)
                .when()
                .post("/spartans/")
                .then()
                .log().all()
                .statusCode(201)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", equalToIgnoringCase("Bulud"))
                .body("data.gender", equalToIgnoringCase("Female"))
                .body("data.phone", hasToString("1223234545"))

        ;

    }

    @Test
    public void Add_new_spartan_NAME_Negative_test() {

        Spartan spartan = new Spartan("B", "Female", 1223234545);


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(spartan)
                .when()
                .post("/spartans/")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Bad Request"))
                .body("errors[0].defaultMessage", is("name should be at least 2 character and max 15 character"))


        ;
    }

    @Test
    public void Add_new_spartan_EvertingNegative_test() {

        Spartan spartan = new Spartan("B", "F", 1223);


        given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(spartan)
                .when()
                .post("/spartans/")
                .then()
                .log().all()
                .statusCode(400)
//                .body("error",is("Bad Request"))
//                .body("errors[0].defaultMessage",is("name should be at least 2 character and max 15 character"))
//                .body("errors[1].defaultMessage",is("Gender should be either Male or Female"))
                .body("errors.defaultMessage",hasSize(3))
                .body("errors.defaultMessage", hasItems("name should be at least 2 character and max 15 character", "Gender should be either Male or Female","Phone number should be at least 10 digit"))
                .body("message",containsString("Error count: 3"))


        ;
    }
}