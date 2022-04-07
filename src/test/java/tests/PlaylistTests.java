package tests;

import api.StatusCode;
import api.controller.Playlists;
import api.utils.ConfigLoader;
import api.utils.DataLoader;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import org.jetbrains.annotations.NotNull;
import pojo.error.Error;
import pojo.playlist.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static api.utils.FakerUtils.*;

public class PlaylistTests {

    @Description("This api should create a playlist with the correct parameters")
    @Test (description = "Should be able to create a playlist")
    public void ShouldBeAbleToCreateAPlaylist() {
        // set payload request as a pojo class
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);

        // execute request + get response + assert
        Response response = Playlists.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201.getCode());

        // deserialize response and assert
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test (description = "Should be able to get a playlist")
    public void ShouldBeAbleToGetAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Test Playlist", "New playlist description", false);
        Response response = Playlists.get(DataLoader.getInstance().getPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());
        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test (description = "Should be able to update a playlist")
    public void ShouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Update Playlist", "New updated playlist description", false);
        Response response = Playlists.put(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200.getCode());
    }

    @Test (description = "Should not be able to create a playlist without a name")
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = playlistBuilder("", generateDescription(), false);
        Response response = Playlists.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_400.getCode());
        assertError(response.as(Error.class), StatusCode.CODE_400.getCode(), StatusCode.CODE_400.getMessage());
    }

    @Test (description = "Should not be able to create a playlist with an expired token")
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String invalid_token = "12345";
        Playlist requestPlaylist = playlistBuilder(generateName(), generateDescription(), false);
        Response response = Playlists.post(requestPlaylist, invalid_token);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401.getCode());
        assertError(response.as(Error.class), StatusCode.CODE_401.getCode(), StatusCode.CODE_401.getMessage());
    }

    public Playlist playlistBuilder(String name, String description, Boolean isPublic){
        return Playlist.builder().
                name(name).
                description(description).
                _public(isPublic).build();
    }

    // re-usable methods
    public void assertPlaylistEqual(@NotNull Playlist responsePlaylist, @NotNull Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(@NotNull Error responseError, int expectedStatusCode, String expectedErrorMessage){
        assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getError().getMessage(), equalTo(expectedErrorMessage));
    }
}
