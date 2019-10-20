package seedu.address.ui.components.form;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import seedu.address.logic.parser.trips.edit.EditTripCommand;
import seedu.address.model.trip.Photo;
import seedu.address.ui.MainWindow;

import java.util.function.Consumer;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;

public class PhotoFormItem extends FormItem<Photo> {
    private static final String FXML = "components/forms/PhotoFormItem.fxml";
    @FXML
    private Button fileChooseButton;

    @FXML
    private TextField pathTextField;

    Runnable executeChooseFile;
    public PhotoFormItem(String photoFormItemName, String existingPath, Consumer<Photo> executeChangeHandler, Runnable executeChooseFile) {
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

    public PhotoFormItem(String photoFormItemName, Consumer<Photo> executeChangeHandler, Runnable executeChooseFile) {
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
