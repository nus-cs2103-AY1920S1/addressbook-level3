package seedu.ezwatchlist.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import info.movito.themoviedbapi.TmdbApi;

import info.movito.themoviedbapi.model.config.TmdbConfiguration;

public class ImageRetrieval {
    private static final String DEFAULT_FILE_SIZE = "w500";
    private final String IMAGE_CACHE_LOCATION;
    private String API_BASE_URL;

    private String imageUrl;

    public ImageRetrieval(TmdbApi tmdbApi, String filePath) {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        IMAGE_CACHE_LOCATION = Paths.get(root.toString(),"src", "main", "resources", "posters").toString() + File.separator;
        System.out.println(IMAGE_CACHE_LOCATION);
        TmdbConfiguration configuration = tmdbApi.getConfiguration();
        API_BASE_URL = configuration.getBaseUrl() + DEFAULT_FILE_SIZE;
        imageUrl = API_BASE_URL + filePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void downloadImage(String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get(IMAGE_CACHE_LOCATION + fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
