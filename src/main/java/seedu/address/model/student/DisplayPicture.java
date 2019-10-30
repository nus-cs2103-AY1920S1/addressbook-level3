package seedu.address.model.student;

import java.io.FileNotFoundException;

/**
 * Class to handle location of image on student card
 */
public class DisplayPicture {

    private String imgFileName;
    private final String defaultPicture = "/images/themyth.png";

    public DisplayPicture() {
        imgFileName = defaultPicture;
    }

    /**
     * tries to load img and send it to another component
     * @return Image
     * @throws FileNotFoundException
     */
    public String getImage() {
        return imgFileName;
    }
    public String getDefault() {
        return defaultPicture;
    }
    public void setNewImg(String newImg) {
        imgFileName = newImg;
    }

    /**
     * asserts that picture chosen must be png
     * @param file
     * @return
     */
    public static boolean isValidFormat(String file) {
        if (file.length() < 5) {
            return false;
        }
        int start = file.length()-3;
        boolean isFilePng = file.substring(start).equals("png")  || file.substring(start).equals("jpg");
        return isFilePng;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && imgFileName.equals(((DisplayPicture) other).imgFileName)); // state check

    }

}
