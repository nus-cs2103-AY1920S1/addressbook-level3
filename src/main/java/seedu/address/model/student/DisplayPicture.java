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
    public void setNewImg(String newImg) {
        imgFileName = newImg;
    }

}
