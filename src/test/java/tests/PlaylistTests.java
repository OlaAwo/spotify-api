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
        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        // execute request + get response + assert
        Response response = Playlists.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));

        // deserialize response as pojo + assert
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Test Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = Playlists.get(DataLoader.getInstance().getPlaylistId());
        assertThat(response.statusCode(), equalTo(200));

        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist() {
        Playlist requestPlaylist = new Playlist().
                setName("Update Playlist").
                setDescription("New updated playlist description").
                setPublic(false);

        Response response = Playlists.put(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist().
                setDescription("New playlist description").
                setPublic(false);

        Response response = Playlists.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken() {
        String invalid_token = "12345";

        Playlist requestPlaylist = new Playlist().
                setName("New Playlist").
                setDescription("New playlist description").
                setPublic(false);

        Response response = Playlists.post(requestPlaylist, invalid_token);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
