package seedu.ezwatchlist.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.config.TmdbConfiguration;
import info.movito.themoviedbapi.tools.MovieDbException;
import javafx.scene.image.Image;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;

/**
 * A class for the image retrieval.
 */
public class ImageRetrieval {
    private static final String DEFAULT_FILE_SIZE = "w500";
    private static final String ROOT = defaultDirectory();
    public static final String IMAGE_CACHE_LOCATION = ROOT + File.separator + "Ezwatchlist" + File.separator + "posters";
    private String API_BASE_URL;

    private String imageUrl;

    public ImageRetrieval(TmdbApi tmdbApi, String filePath) throws OnlineConnectionException {
        try {
            //Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            //IMAGE_CACHE_LOCATION = Paths.get(root.toString(), "src", "main", "resources", "images", "posters").toString() + File.separator;
            TmdbConfiguration configuration = tmdbApi.getConfiguration();
            API_BASE_URL = configuration.getBaseUrl() + DEFAULT_FILE_SIZE;
            imageUrl = API_BASE_URL + filePath;
        } catch (MovieDbException e) {
            throw new OnlineConnectionException("Internet Connection failed at Image Retrieval");
        }
    }

    //adapted from https://stackoverflow.com/questions/6561172/find-directory-for-application-data-on-linux-and-macintosh
    static String defaultDirectory() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return System.getenv("APPDATA");
        else if (os.contains("mac"))
            return System.getProperty("user.home") + "/Library/Application Support";
        else if (os.contains("nux"))
            return System.getProperty("user.home");
        else
            return System.getProperty("user.dir");
    }

    public String retrieveImage(String fileName) throws OnlineConnectionException {
        try {
            downloadImage(fileName);
        } finally {
            return fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png";
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void downloadImage(String fileName) throws OnlineConnectionException {
        try (InputStream in = new URL(imageUrl).openStream()) {
            File parent = new File(IMAGE_CACHE_LOCATION);

            if (!parent.exists()) {
                parent.mkdirs();
            }

            Files.copy(in, Paths.get(IMAGE_CACHE_LOCATION + File.separator +
                    fileName.replaceAll("[^A-Za-z0-9\\[\\]]", "") + ".png"));
        } catch(FileAlreadyExistsException f) {
            System.err.println("Duplicate image");
        } catch (IOException e) {
            System.err.println(e.getCause());
            throw new OnlineConnectionException("No internet connection at downloading image");
        }
    }
}
