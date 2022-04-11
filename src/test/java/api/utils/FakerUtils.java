package api.utils;

import com.github.javafaker.Faker;
import org.jetbrains.annotations.NotNull;

public class FakerUtils {

    public static @NotNull String generateName(){
        Faker faker = new Faker();
        return "Playlist " + faker.regexify("[A-Za-z0-9 ,_-]{10}");
    }

    public static @NotNull String generateDescription(){
        Faker faker = new Faker();
        return "Description " + faker.regexify("[A-Za-z0-9_@./#&+-]{30}");
    }
 }