package api.controller;

import api.RestResource;
import api.utils.ConfigLoader;
import io.restassured.response.Response;
import pojo.playlist.Playlist;

import static api.TokenManager.getToken;
import static api.constants.Playlists.PLAYLISTS;
import static api.constants.Playlists.USERS;

public class Playlists {

    // Playlist api reusebale methods
    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, getToken(), requestPlaylist);
    }

    public static Response post(Playlist requestPlaylist, String token) {
        return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS, token, requestPlaylist);
    }

    public static Response get(String playlistID) {
        return RestResource.get(PLAYLISTS + "/" + playlistID, getToken());
    }

    public static Response put(Playlist requestPlaylist, String playlistID) {
        return RestResource.put(PLAYLISTS + "/" + playlistID, getToken(), requestPlaylist);
    }
}
