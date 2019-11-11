package seedu.guilttrip.model.reminders.messages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Users may chose to include an image in their generalReminder message.
 */
public class ReminderImage extends Cell {
    public static final String TYPE = "Image";
    private String imageName;
    private Image image;

    /**
     * Method called when user choses to load an image outside of file.
     * @param imageName assign name to image to load in future.
     * @param imagePath filepath of image.
     * @param willStore whether to store image in program files.
     */
    public ReminderImage(String imageName, String imagePath, Boolean willStore) throws IOException {
        this.imageName = imageName;
        setImage(imageName, imagePath, willStore);
    }

    public ReminderImage(String imageName) throws FileNotFoundException {
        this.imageName = imageName;
        setImage(imageName);
    }

    private Image loadImage(String imageLink) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("/images/guiltTrip()_32.png");
        return new Image(inputStream);
    }

    /**
     * Accesses image from imageLink.
     */
    public Image getImage() {
        return this.image;
    }

    public void setImage(String imageName) throws FileNotFoundException {
        this.imageName = imageName;
        this.image = ImageStorage.loadImage(imageName);
    }

    public void setImage(String imageName, String imagePath, boolean willStore) throws IOException {
        this.imageName = imageName;
        this.image = loadImage(imagePath);
        if (willStore) {
            ImageStorage.saveImage(imageName, image);
        }
    }

    public Node getNode() {
        return new ImageView(image);
    }
}
