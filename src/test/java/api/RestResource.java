package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static api.SpecBuilder.*;
import static api.constants.Base.API;
import static api.constants.Base.TOKEN;
import static io.restassured.RestAssured.given;

public class RestResource {

    // generic methods for all controllers
    public static Response post(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                auth().oauth2(token).
                body(requestPlaylist).
        when().post(path).
        then().spec(getResponseSpec()).
                contentType(ContentType.JSON).
                extract().response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpec()).
                auth().oauth2(token).
        when().get(path).
        then().spec(getResponseSpec()).
                contentType(ContentType.JSON).
                extract().response();
    }

    public static Response put(String path, String token, Object requestPlaylist) {
        return given(getRequestSpec()).
                auth().oauth2(token).
                body(requestPlaylist).
        when().put(path).
        then().spec(getResponseSpec()).
                extract().response();
    }

    public static Response postAccount(HashMap<String, String> form){
        return given(getAccountRequestSpec()).
                formParams(form).
                when().post(API + TOKEN).
                then().spec(getResponseSpec()).
                extract().response();
    }
}
