package kcz.side.lightbulbsbackend.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Getter
public class SceneConfigProvider {
    private JsonObject config;

    @PostConstruct
    public void loadJson() {
        String fileName = "config.json";

        try {
            Path jarDir = Paths.get(System.getProperty("java.class.path")).toAbsolutePath().getParent();
            Path externalPath = jarDir.resolve(fileName);
            if (Files.exists(externalPath)) {
                try (InputStreamReader reader = new InputStreamReader(
                        Files.newInputStream(externalPath), StandardCharsets.UTF_8)) {
                    this.config = new Gson().fromJson(reader, JsonObject.class);
                    return;
                }
            }

            try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (is == null) {
                    throw new RuntimeException("Config.json not found");
                }
                try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                    this.config = new Gson().fromJson(reader, JsonObject.class);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error during loading config.json", e);
        }
    }
}
