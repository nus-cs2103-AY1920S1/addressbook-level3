package seedu.address.websocket.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.websocket.Cache;

/**
 * This class is used to get an image from a api response
 */
public class ImageQuery {

    private static final Logger logger = LogsCenter.getLogger(ImageQuery.class);

    /**
     * This mthod checks if the API response is valid then saves it into the directory
     * @param imageUrl
     * @param filePath
     */

    public static void execute(String imageUrl, String filePath) {
        try {
            if (isValid(imageUrl)) {
                saveImage(imageUrl, filePath);
            } else {
                throw new IllegalValueException(imageUrl + "Not identifiable by gmaps");
            }
        } catch (IllegalValueException | IOException e) {
            logger.warning(e.getMessage());
        }
    }

    /**
     *This method is used to check is the API response is valid.
     * @param imageUrl
     * @return
     */
    private static boolean isValid(String imageUrl) {
        boolean isValid = true;
        try {
            URL url = new URL(imageUrl);
            URLConnection conn = url.openConnection();

            Map<String, List<String>> map = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                if (key != null && key.equals("X-Staticmap-API-Warning")) {
                    isValid = false;
                    String name = imageUrl.split("center=")[1].split("&")[0];
                    if (FileUtil.isFileExists(Path.of(Cache.loadImagePath(name)))) {
                        logger.fine(name + " not available on gmaps but manually added");
                    } else {
                        logger.warning(name + " not on gmaps and not added");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    /**
     * This method is used to save an image from api response
     * @param imageUrl
     * @param destinationFile
     * @throws IOException
     */
    private static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}
