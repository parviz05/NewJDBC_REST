package RestAsurePractices;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


public class HR_ORDS_REST_API_TEST {



    /*
     RestAssured.baseURI = "http://18.208.250.251";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
     */

    @BeforeClass
            public static void setUp(){

        baseURI="http://18.208.250.251";
        RestAssured.port=1000;
        RestAssured.basePath="/ords/hr";

    }
    @Test
    public void test_All_Region(){

        Response response=given().accept(ContentType.JSON)    //queryParam("region","Europe")
                 .get("/regions/");

        //response.prettyPrint();

        assertEquals(200,response.statusCode());

        //get limit
        int num=response.jsonPath().getInt("limit");
        System.out.println(num);

        //get first region all info
        String region=response.jsonPath().getString("items[0]");
        System.out.println(region);

        //get first region name
        String regionName=response.jsonPath().getString("items.region_name[0]");
        //or like below
        System.out.println(response.jsonPath().getString("items[0].region_name"));
        System.out.println(regionName);

    }
@Test
public void getAllRegionName(){


        Response response=given().accept(ContentType.JSON).get("/regions/");

     List<String>AllName=response.jsonPath().getList("items.region_name");
    System.out.println(AllName);

}
    @Test
    //get one href
    public void getOnerefFields(){
        Response response=given().accept(ContentType.JSON).get("/regions/");

        String link=response.jsonPath().getString("items[0].links[0].href");
        System.out.println(link);
    }

    @Test
    //get all href
    public void getAllhrefFields(){
        Response response=given().accept(ContentType.JSON).get("/regions/");

       List<String>Allhreft= response.jsonPath().getList("items.links.href");
        System.out.println(Allhreft);
    }

    @Test
    public void getLasthrefFields() {
        Response response = given().accept(ContentType.JSON).get("/regions/");

        String last=response.jsonPath().getString("links[3]");

      assertEquals("first",response.jsonPath().getString("links[3].rel"));
        System.out.println(last);

    }


    @Test
    public void test_singRegion(){
        Response response = given().accept(ContentType.JSON). pathParam("my_id",1).get("/regions/{my_id}");

       response.prettyPrint();

      Map<String,Object>myJsonMap= response.jsonPath().getMap("");
        System.out.println(myJsonMap.get("region_name"));
    }


    @AfterClass
    public static void tearDown(){
        //this will all the set up we made to avoid accidental collusion between different test code
        RestAssured.reset();
    }
}
