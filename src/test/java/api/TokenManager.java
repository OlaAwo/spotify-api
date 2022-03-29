package api;

import api.utils.ConfigLoader;
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
        form.put("grant_type", ConfigLoader.getInstance().getGrantType());
        form.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        form.put("client_id", ConfigLoader.getInstance().getClientId());
        form.put("client_secret", ConfigLoader.getInstance().getClientSecret());

        Response response = RestResource.postAccount(form);

        if (response.statusCode() != 200) {
            throw new RuntimeException("Renew token failed");
        }

        return response;
    }
}
