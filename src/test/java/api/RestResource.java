package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static api.SpecBuilder.getRequestSpec;
import static api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + token).
                body(requestPlaylist).
        when().post(path).
        then().spec(getResponseSpec()).
                contentType(ContentType.JSON).
                extract().response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + token).
        when().get(path).
        then().spec(getResponseSpec()).
                contentType(ContentType.JSON).
                extract().response();
    }

    public static Response put(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                header("Authorization", "Bearer " + token).
                body(requestPlaylist).
        when().put(path).
        then().spec(getResponseSpec()).
                extract().response();
    }

    public static Response postAccount(HashMap<String, String> form){
        return given().
                baseUri("https://accounts.spotify.com").
                contentType(ContentType.URLENC).
                formParams(form).
                log().all().
                when().post("/api/token").
                then().spec(getResponseSpec()).
                extract().response();
    }
}
