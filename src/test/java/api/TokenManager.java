package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken() {
        try {
            if (access_token == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing token...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is okay.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get token");
        }
        return access_token;
    }

    private static Response renewToken() {
        HashMap<String, String> form = new HashMap<String, String>();
        form.put("grant_type", "refresh_token");
        form.put("refresh_token", "AQDZ_EKG2UJAH0oRRYBA-ZMDWq9MQU6Z_r1O2ERjX1_CdvJaS5kPVzglSaXmqDu-zg9UoNc3NIAMHVc9d" +
                "efqdqE8tQEoXCdDhTD4inPhucE1quBJUBO_47EcdPOBycm1v0U");
        form.put("client_id", "0db4937cb09647cab8829682a31dba18");
        form.put("client_secret", "4a79546d63f24781996ce74d5b6a8324");

        Response response = RestResource.postAccount(form);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Renew token failed");
        }

        return response;
    }
}
