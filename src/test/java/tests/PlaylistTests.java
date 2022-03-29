package tests;

import api.controller.Playlists;
import api.utils.ConfigLoader;
import api.utils.DataLoader;
import pojo.error.Error;
import pojo.playlist.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {

    @Test
    public void ShouldBeAbleToCreateAPlaylist() {
        // set payload request as a pojo class
        Playlist requestPlaylist = playlistBuilder("New Playlist", "New playlist description", false);

        // execute request + get response + assert
        Response response = Playlists.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);

        // deserialize response as pojo + assert
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Test Playlist", "New playlist description", false);
        Response response = Playlists.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), 200);
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Update Playlist", "New updated playlist description", false);
        Response response = Playlists.put(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), 200);
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = playlistBuilder("", "New playlist description", false);
        Response response = Playlists.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);
        assertError(response.as(Error.class), 400, "Missing required field: name");
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder("New Playlist", "New playlist description", false);
        Response response = Playlists.post(requestPlaylist, invalid_token);
        assertStatusCode(response.statusCode(), 401);
        assertError(response.as(Error.class), 401, "Invalid access token");
    }

    public Playlist playlistBuilder(String name, String description, Boolean isPublic){
        return new Playlist().
                setName(name).
                setDescription(description).
                setPublic(isPublic);
    }

    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error responseError, int expectedStatusCode, String expectedErrorMessage){
        assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getError().getMessage(), equalTo(expectedErrorMessage));
    }
}
