package stepDefinitions;

import POJO.AddPlace;
import POJO.Location;

import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;



import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;


public class StepDefinition {

    RequestSpecification res;
    ResponseSpecification responseSpec;
    Response response;


    @Given("Add Place Payload test")
    public void add_Place_Payload_test() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("wiejdjhwebdjwhebdjhwbd");
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        AddPlace place = new AddPlace();
        place.setAccuracy(50);
        place.setAddress("195, Private street");
        place.setLanguage("French-IN");
        place.setPhone_number("123456789");
        place.setWebsite("https://rahulshettyacademy.com/");
        place.setName("Some House");
        List<String> myList = new ArrayList<String>();
        myList.add("shor park");
        myList.add("some test text");
        place.setTypes(myList);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        place.setLocation(location);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        res = given().spec(req)
                .body(place);

        Response response = res.when().post("/maps/api/place/add/json")
                .then().spec(responseSpec).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }



    @When("user calls {string} with POST http request")
    public void user_calls_with_POST_http_request(String string) {
        response = res.when().post("/maps/api/place/add/json")
                .then().spec(responseSpec).extract().response();
    }
    @Then("the API call is success with status code {int}")
    public void the_API_call_is_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(),200);
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(key).toString(), value);
    }




}
