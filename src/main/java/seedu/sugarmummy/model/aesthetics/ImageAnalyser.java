package seedu.sugarmummy.model.aesthetics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

// Modified code with credits to: Zaz Gmy via
// https://stackoverflow.com/questions/10530426/how-can-i-find-dominant-color-of-an-image
// and mhshams via https://stackoverflow.com/questions/3607858/convert-a-rgb-color-value-to-a-hexadecimal-string

/**
 * Class that analyses image files and is able to determine the dominant colour in an image.
 */
public class ImageAnalyser {

    /**
     * Returns a hexadecimal colour representing the dominant colour in a given image.
     *
     * @param imagePath File path of an image of which dominant colour is to be obtained
     * @return Hexadecimal colour representing the dominant colour in given image.
     */
    public static String getDominantColour(String imagePath) {
        File file = new File(imagePath);

        try {
            ImageInputStream is = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(is);

            ImageReader imageReader = iter.next();
            imageReader.setInput(is);

            BufferedImage image = imageReader.read(0);

            int height = image.getHeight();
            int width = image.getWidth();

            Map<Integer, Integer> colourMap = new HashMap<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = image.getRGB(i, j);
                    Integer counter = colourMap.get(rgb);
                    if (counter == null) {
                        counter = 0;
                    }
                    counter++;
                    colourMap.put(rgb, counter);
                }
            }
            return getMostCommonColour(colourMap);
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * Returns the most common colour given a map that maps an integer pixel to its count.
     *
     * @param colourMap Colour map that maps an integer pixel to its count.
     * @return Hexadecimal representation of most common integer pixel counted.
     */
    private static String getMostCommonColour(Map<Integer, Integer> colourMap) {
        List<Entry<Integer, Integer>> list = new LinkedList<>(colourMap.entrySet());
        list.sort(Comparator.comparing(Entry::getValue));
        // Returns the rgb with the rgb array of the rgb colour with the highest count.
        Entry<Integer, Integer> me = list.get(list.size() - 1);
        int[] rgb = getRgbArr(me.getKey());
        return String.format("#%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Returns a rgb array given a value representing a pixel.
     *
     * @param pixel Integer pixel as defined in the BufferedImage class.
     * @return Rgb array of the given integer pixel.
     */
    private static int[] getRgbArr(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red, green, blue};
    }

    /**
     * Returns whether or not a given rgb array represents a colour that is gray
     *
     * @param rgbArr Rgb array representing a colour.
     * @return Whether or not the given rgb array representing a colour is gray.
     */
    private static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            return rbDiff <= tolerance && rbDiff >= -tolerance;
        }
        return true;
    }
}
