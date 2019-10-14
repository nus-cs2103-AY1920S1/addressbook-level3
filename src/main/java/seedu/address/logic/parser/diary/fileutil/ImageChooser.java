package seedu.address.logic.parser.diary.fileutil;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImageChooser {

    private final Stage fileChooserStage;
    private final FileChooser fileChooser;

    public ImageChooser() {
        fileChooserStage = new Stage();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif"));
    }

    public File showDialog() {
        File f =  fileChooser.showOpenDialog(fileChooserStage);
        fileChooserStage.close();

        return f;
    }
}

