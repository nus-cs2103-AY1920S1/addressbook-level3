package seedu.ezwatchlist.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;

/**
 * A class for the image retrieval.
 */
public class ImageRetrieval {
    private static final String DEFAULT_FILE_SIZE = "w500";
    private final String imageCacheLocation;
    private final String fxmlImageRoot = "/images/posters/"; //relative to the FXML Image location.
    private String apiBaseUrl;

    private String imageUrl;

    public ImageRetrieval(TmdbApi tmdbApi, String filePath) {
        Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
        imageCacheLocation = Paths.get(root.toString(), "src", "main", "resources", "images", "posters")
                .toString() + File.separator;
        TmdbConfiguration configuration = tmdbApi.getConfiguration();
        apiBaseUrl = configuration.getBaseUrl() + DEFAULT_FILE_SIZE;
        imageUrl = apiBaseUrl + filePath;
    }

    /**
     * Retrives image from database.
     * @param fileName file name
     * @return String file name
     */
    public String retrieveImage(String fileName) {
        downloadImage(fileName);
        return fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Downloads image from online database.
     * @param fileName file name of the show.
     */
    public void downloadImage(String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get(imageCacheLocation
                    + fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png"));
        } catch (FileAlreadyExistsException f) {
            System.err.println("Duplicate image");
        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }
}
