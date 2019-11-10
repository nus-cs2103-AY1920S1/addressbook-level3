package seedu.address.ui.components.form;

import java.util.function.Consumer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import seedu.address.model.itinerary.Photo;

/**
 * Abstraction of a form object that handle photos.
 */
public class DayPhotoFormItem extends FormItem<Photo> {
    private static final String FXML = "components/forms/PhotoFormItem.fxml";

    private Runnable executeChooseFile;

    @FXML
    private Button fileChooseButton;

    @FXML
    private TextField pathTextField;

    public DayPhotoFormItem(String photoFormItemName, String existingPath,
                            Consumer<Photo> executeChangeHandler, Runnable executeChooseFile) {
        super(FXML, executeChangeHandler);
        formItemLabel.setText(photoFormItemName);
        pathTextField.setText(existingPath);
        pathTextField.focusedProperty().addListener((observableVal, oldVal, newVal) -> {
            if (!newVal) {
                executeChangeHandler.accept(getValue());
            }
        });
        this.executeChooseFile = executeChooseFile;
    }

    public DayPhotoFormItem(String photoFormItemName, Consumer<Photo> executeChangeHandler,
                            Runnable executeChooseFile) {
        this(photoFormItemName, "", executeChangeHandler, executeChooseFile);
    }

    @Override
    public Photo getValue() {
        return new Photo(pathTextField.getText());
    }

    @Override
    public void setValue(Photo photo) {
        pathTextField.setText(photo.getImageFilePath());
    }

    @FXML
    private void handleChange() {
        executeChangeHandler.accept(getValue());
    }

    @FXML
    private void handleChooseFile() {
        executeChooseFile.run();
    }
}
