package seedu.address.commons.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * A class for converting byte arrays to human readable formats.
 */
public class PreviewUtil {
    /**
     * Converts an input byte array to string.
     * @param input the input array.
     * @return converted string.
     */
    public static String convertToString(byte[] input) {
        return new String(input);
    }

    /**
     * Converts an input byte array to a buffered image.
     * @param input the input array.
     * @return converted buffered image.
     * @throws IOException if the input cannot be converted to an image.
     */
    public static BufferedImage convertToImage(byte[] input) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(input));
    }

    /**
     * Converts an input byte array to a PDF document.
     */
    public static PDDocument convertToPdf(byte[] input) throws IOException {
        return PDDocument.load(input);
    }

    /**
     * Converts an input byte array to a MS Word document.
     */
    public static XWPFDocument convertToWord(byte[] input) throws IOException {
        try {
            return new XWPFDocument(new ByteArrayInputStream(input));
        } catch (NotOfficeXmlFileException e) {
            throw new IOException("");
        }
    }
}
