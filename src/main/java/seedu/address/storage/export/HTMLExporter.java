package seedu.address.storage.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Simulates a class which handles the exporting of formatted text to HTML.
 */
public class HTMLExporter extends Exporter {

    /**
     * Exports a String to a HTML file.
     * @param fileId The identifier of the file.
     * @param formattedContent The formatted content for HTML.
     * @return True if the file does not exist, false if the file already exists.
     * @throws IOException The exception to be thrown.
     */
    public static boolean export(String fileId, String formattedContent) throws IOException {
        String fileName = fileId + ".html";
        String filePath = EXPORT_DIRECTORY_PATH + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }

        FileOutputStream fos = new FileOutputStream(new File(filePath));
        fos.write(formattedContent.getBytes());
        fos.flush();
        fos.close();

        return true;
    }

}
