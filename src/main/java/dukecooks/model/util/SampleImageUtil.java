package dukecooks.model.util;

/**
 * Contains utility methods for getting sample images
 */
public class SampleImageUtil {

    public static final String PATH_TO_PHO = "/images/pho.jpg";
    public static final String PATH_TO_SUSHI = "/images/sushi.jpg";
    public static final String PATH_TO_STEAK = "/images/steak.jpg";

    public static String[] getSampleImages() {
        return new String[] {
            PATH_TO_PHO,
            PATH_TO_STEAK,
            PATH_TO_SUSHI
        };
    }
}


