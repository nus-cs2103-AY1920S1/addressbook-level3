package dukecooks.model.util;

/**
 * Contains utility methods for getting sample images
 */
public class SampleImageUtil {

    // Food
    public static final String PATH_TO_PHO = "/images/pho.jpg";
    public static final String PATH_TO_SUSHI = "/images/sushi.jpg";
    public static final String PATH_TO_STEAK = "/images/steak.jpg";
    public static final String PATH_TO_CHICKEN_RICE = "/images/chicken_rice.jpg";
    public static final String PATH_TO_AYAM_PENYET = "/images/ayampenyet.jpg";
    public static final String PATH_TO_ROJAK = "/images/rojak.jpg";
    public static final String PATH_TO_CARROT_CAKE = "/images/carrotcake.jpg";

    // Exercise
    public static final String PATH_TO_SPRINTS = "/images/sprints.jpg";
    public static final String PATH_TO_PLANKS = "/images/planks.jpg";
    public static final String PATH_TO_ZUMBA = "/images/zumba.jpg";

    // Health
    public static final String PATH_TO_GOJI = "/images/gojiberry.jpg";
    public static final String PATH_TO_LOW_SUGAR = "/images/lowsugar.jpg";
    public static final String PATH_TO_SUPERFOODS = "/images/superfoods.jpg";



    public static String[] getSampleImages() {
        return new String[] {
            PATH_TO_PHO,
            PATH_TO_STEAK,
            PATH_TO_SUSHI,
            PATH_TO_CHICKEN_RICE,
            PATH_TO_AYAM_PENYET,
            PATH_TO_ROJAK,
            PATH_TO_CARROT_CAKE,
            PATH_TO_PLANKS,
            PATH_TO_SPRINTS,
            PATH_TO_ZUMBA,
            PATH_TO_GOJI,
            PATH_TO_LOW_SUGAR,
            PATH_TO_SUPERFOODS
        };
    }
}


