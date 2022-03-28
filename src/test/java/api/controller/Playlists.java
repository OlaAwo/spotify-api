package api.controller;

import api.RestResource;
import io.restassured.response.Response;
import pojo.playlist.Playlist;

import static api.TokenManager.getToken;

public class Playlists {

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post("/users/ola.awosile/playlists", getToken(), requestPlaylist);
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post("/users/ola.awosile/playlists", token, requestPlaylist);
    }

    public static Response get(String playlistID) {
        return RestResource.get("/playlists/" + playlistID, getToken());
    }

    public static Response put(Playlist requestPlaylist, String playlistID) {
        return RestResource.put("/playlists/" + playlistID, getToken(), requestPlaylist);
    }
}
