package seedu.address.model.student;

//@@ author SebastianLie

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
     * Overloaded method to load
     * DisplayPicture
     * @param fileName
     */
    public DisplayPicture(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidFormat(fileName));
        imgFileName = fileName;
    }

    /**
     * tries to load img and send it to another component
     * @return String of imgFilePath
     */
    public String getFilePath() {
        return imgFileName;
    }
    public String getDefault() {
        return defaultPicture;
    }
    public void setNewImg(String newImg) {
        imgFileName = newImg;
    }
    public void setToDefault() {
        imgFileName = defaultPicture;
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
        int start = file.length() - 3;
        boolean isFilePng = file.substring(start).equals("png") || file.substring(start).equals("jpg");
        return isFilePng;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisplayPicture // instanceof handles nulls
                && imgFileName.equals(((DisplayPicture) other).imgFileName)); // state check

    }

}
