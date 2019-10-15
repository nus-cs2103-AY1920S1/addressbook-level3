package seedu.ezwatchlist.api.model;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.storage.UserPrefsStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageRetrieval {
    private static final String DEFAULT_FILE_SIZE = "w500";
    private static final String IMAGE_CACHE_LOCATION = null;
    private String API_BASE_URL;

    private String imageUrl;

    public ImageRetrieval(TmdbApi tmdbApi, String filePath) {
        TmdbConfiguration configuration = tmdbApi.getConfiguration();
        API_BASE_URL = configuration.getBaseUrl() + DEFAULT_FILE_SIZE;
        imageUrl = API_BASE_URL + filePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void downloadImage() {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get("D:/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
