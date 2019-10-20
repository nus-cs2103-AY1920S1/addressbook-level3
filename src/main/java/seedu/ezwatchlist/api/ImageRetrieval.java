package seedu.ezwatchlist.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import javafx.scene.image.Image;

/**
 * A class for the image retrieval.
 */
public class ImageRetrieval {
    private static final String DEFAULT_FILE_SIZE = "w500";
    private final String IMAGE_CACHE_LOCATION;
    private final String FXML_IMAGE_ROOT = "/images/posters/"; //relative to the FXML Image location.
    private String API_BASE_URL;

    private String imageUrl;

    public ImageRetrieval(TmdbApi tmdbApi, String filePath) {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        IMAGE_CACHE_LOCATION = Paths.get(root.toString(),"src", "main", "resources", "images", "posters").toString() + File.separator;
        System.out.println(IMAGE_CACHE_LOCATION);
        TmdbConfiguration configuration = tmdbApi.getConfiguration();
        API_BASE_URL = configuration.getBaseUrl() + DEFAULT_FILE_SIZE;
        imageUrl = API_BASE_URL + filePath;
    }

    public String retrieveImage(String fileName) {
        downloadImage(fileName);
        return FXML_IMAGE_ROOT + fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void downloadImage(String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get(IMAGE_CACHE_LOCATION + fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png"));
        } catch(FileAlreadyExistsException f) {
            System.err.println("Duplicate image");
        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }
}
