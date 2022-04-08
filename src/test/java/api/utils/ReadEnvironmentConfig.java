package api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadEnvironmentConfig {

    static final String defaultEnvironment = "staging";
    static final String environmentFilename = "./src/main/resources/environment.json";

    public static EnvironmentConfig GetConfig() {

        EnvironmentConfig environmentConfig = new EnvironmentConfig();
        String environment = System.getProperty("environment", defaultEnvironment);
        File file = new File(environmentFilename);
        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));
            ObjectMapper mapper = new ObjectMapper();
            String configNode = mapper.readTree(content).get(environment).toString();
            environmentConfig = mapper.readValue(configNode, EnvironmentConfig.class);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error getting " + environment + " environment config at " + file.getAbsolutePath());
        }

        return environmentConfig;
    }
}
