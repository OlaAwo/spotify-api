package api.controller;

import api.RestResource;
import io.restassured.response.Response;
import pojo.playlist.Playlist;

import static api.TokenManager.getToken;
import static api.constants.Playlists.PLAYLISTS;
import static api.constants.Playlists.USERS;

public class Playlists {

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/ola.awosile" + PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(USERS + "/ola.awosile" + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistID) {
        return RestResource.get(PLAYLISTS + "/" + playlistID, getToken());
    }

    public static Response put(Playlist requestPlaylist, String playlistID) {
        return RestResource.put(PLAYLISTS + "/" + playlistID, getToken(), requestPlaylist);
    }
}
