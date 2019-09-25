package RestAsurePractices;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


public class SpartanRest_Weekend {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://18.208.250.251";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        // above will generate a BASE REQUEST URL OF http://52.23.254.102:8000/api
    }

    @Test
    public void test1() {

        //RequestSpecification reqSpec = given().accept(ContentType.JSON);
        Response response =
                // RequestSpecification
                given()
                        .accept(ContentType.JSON).
                        when()
                        // Actual request is being sent using HTTP verbs methods with URL
                        .get("/spartans");
        // eventually it will return a Response object

    }

    @Test
    public void test2() {

        //RequestSpecification reqSpec = given().accept(ContentType.JSON);
        Response response =
                // RequestSpecification
                given()
                        .accept(ContentType.JSON).
                        queryParam("gender", "Male").
                        when()
                        // Actual request is being sent using HTTP verbs methods with URL
                        .get("/spartans/search");
        // eventually it will return a Response object
        assertEquals(200, response.statusCode());
        assertFalse(response.asString().contains("Female"));
        response.prettyPrint();
    }

    @Test
    public void test3() {

        Response response =
                given().accept(ContentType.JSON).
                        get("/spartans/");

        // response.prettyPrint();

        System.out.println(response.path("pageable.sort.empty").toString());
    }

    @Test
    public void JsonPath() {

        //jsonPath -->> just like Xpath, it's for finding elements in json object/ document

        Response response =
                given().accept(ContentType.JSON).
                        queryParam("gender", "Female").
                        get("/spartans/search");


        boolean isempty = response.jsonPath().getBoolean("pageable.sort.empty");
        assertTrue(isempty);
        assertTrue("Asertion Does not Pass", isempty);

    }

    @Test
    public void FindTotalElement() {
        Response response =
                given().accept(ContentType.JSON).
                        get("/spartans/search");

        int b = response.path("totalElements");
        int a = response.path("numberOfElements");

        System.out.println(a);
        System.out.println(b);

        //System.out.println(response.path("totalElements","").toString());

    }

    @Test
    public void getFirstThreePhoneNumber() {

        Response response = given().accept(ContentType.JSON).get("/spartans/search");

        //find first 3 guys phone number

        System.out.println(response.jsonPath().getLong("content[0].phone"));
        System.out.println(response.jsonPath().get("content[0].phone").toString());
        response.jsonPath().getLong("content[0].phone");
        //list of number
        List<Long> Phones = response.jsonPath().getList("content.phone");
        System.out.println(Phones);


    }
    /*
    jsonPath for content return a json array
    in order to get single json object we need to use content[indexNumer].fileName

    EX:
        content[1].phone --> the second items is phone number

        if us want to store entire phone as a List
        we can use getList methods with jsonPath by taking index out
        EX:
           List<Long> Phones = response.jsonPath().getList("content.phone");

     */

    @Test
    public void getAllName() {

        Response response = given().accept(ContentType.JSON).get("/spartans/search");
//get List of Names
        List<String>Name=response.jsonPath().getList("content.name");
        System.out.println(Name);

    }

    @Test
    public void getSingleSpartan(){

        Response response =
                given().accept(ContentType.JSON).pathParam("id",2).
                        get("/spartans/{id}");

        Map<String,Object>spartanMap=response.jsonPath().getMap("");
        System.out.println(spartanMap.get("name"));
        System.out.println(spartanMap.get("id"));
        System.out.println(spartanMap.get("gender"));
        System.out.println(spartanMap.get("phone"));
        assertEquals("Nels",spartanMap.get("name"));
    }
    @Test
    public void all_SpartanList_map(){

        // TO STORE SPARTAN AS LIST OF MAP
        Response response = get("/spartans/");

       List<Map<String,Object>>ListOfMap= response.jsonPath().getList("");

       // System.out.println(ListOfMap);

        for (Map<String,Object>each:ListOfMap) {
            System.out.println(each);

        }
    }

    @Test
    public void Search_Spartanc_ListOfMap(){
        Response response =
                given().accept(ContentType.JSON).
                        queryParam("gender", "Female").
                        get("/spartans/search");

        List<Map<String,Object>>ListOfMap= response.jsonPath().getList("content");
        for (Map<String,Object>each:ListOfMap) {
            System.out.println(each);

        }


    }
}