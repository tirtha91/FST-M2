import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.given;

public class GithubRestAssuredActivity {

    // Declare request specification
    RequestSpecification requestSpec;
    // Declare response specification
    ResponseSpecification responseSpec;

    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDL9yqYsn2NZC7e4lXtTk5VDa/Dl0xBoFp/RhRcb6/Za4v6vIrDIY5Q8cAPKk90miVG/fraJiaVXS0YMyC6L9a5HmuBoCJAV3xBNIw/ZG/BQ4uMhHOy1QYbtYKTJ8ZIA7PMTt5/N06R7Ux/dYAujCEGLwyzsiUkWgOli+5aI+9nNVTbIhfA6+C248SIpEz15YrETQEQNmAJ9u0RYc2TMUQQAinMcWO+yFdeeL8mkcJrBOlAmY2Igr9pHOo029hL4NnXHIN+GVsfBscHnbKGCO6Iw5xgsQxIMwNYv2zicatWrkK+i7RMiIPwwKh2ETRt9VgP1/H2DgYt0vQbkdtzhimSosesI4QP5NMnEPyJzDNtqzl3rNGJgeoPnd4FNr1+WQHOmG7mbVPbmHgP3J1Fhh0HxqS3eAAOlHEAm5efqm2aRxrCcdqKaOhNVoVO4YjmIMOW6GJrnU2ftfpT69Uf1jEiF6Q3y3nEa9B70Ul6F0g1nmI7zcKE1+C0tHEh9OR6Kes= gmx002a5d744@LAPTOP-FCD3VP26";
    int Id;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                // Set base URL
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();

        responseSpec = new ResponseSpecBuilder()
                // Check status code in response
                .expectStatusCode(201)
                // Check response content type
                .expectContentType("application/json")
                // Check if response contains name property
                // .expectBody("status", equalTo("alive"))
                // Build response specification
                .build();
    }
    @Test(priority=1)
    public void addSSH() {

        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQC9DmOh+kN2+PMEN3Er3ZTes2dLNW1GD24Bcqrmv9Gl+Kyjwr0dl7pvZTOFHI/zbFTb8pUI12lE1eJ7FRxQEKw27MZ3cHdbiOmKH4bvz0KJyTQrRHQEyRTPz6M0MddYrl9sVn2b3U2XVd8HAbhS3HAh+Ua4i9j7Z9saW6f5jPi5LmCk34eLWaPV8nzlrBH1d7ATWs7WxVGqskKr7Laoe/9UCrkUpPS8kAYL/Eu6O28pyIQlXKvE9M8dPvBBeokfL7KhLxQpEM823ptx+WdCyodGpAEckvzYQmOyKTwcNbXarvReg8qPZvtT2+QWbxztQbHhiy9hfL/pSUL6j/x+zfCAka5RHd0uEHBY0SwYqsNYtUXfq6BJ7+mKffT/h+a/wfxHJX3Scd8K4tULAUJymV3cHOMJDXzeX9XqPUxJ0AAo0G55R0NyrSnf7MYOVEahIM1jgbJlEORV/g9ia9zcIxOsuB7mR7S49z7ZhNEF1FTY5U9+Z5wkJfk3KpXbnEIntBk=\""
                + "}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys"); // Send POST request

        System.out.println(response.getBody().prettyPrint());

        Id =  response.then().extract().path("id");
        response.then().statusCode(201);
    }

    @Test(priority=2)
    public void getAllSSH() {

        Response response =
                given().spec(requestSpec)
                        // Set headers
                        .when().get("/user/keys"); // Send get request
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        response.then().statusCode(200);
    }

    @Test(priority=3)
    public void deleteSSH() {

        Response response =
                given().spec(requestSpec) // Set headers
                        .pathParam("keyId", Id) // Add path parameter
                        .when().delete("/user/keys/{keyId}");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        response.then().statusCode(204);
    }


}