package seedu.address.ui;

import java.util.Comparator;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileStatus;
import seedu.address.model.util.DateUtil;

/**
 * An UI component that displays information of an {@code EncryptedFile}.
 */
public class FileCard extends UiPart<Region> {

    private static final String FXML = "FileListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final EncryptedFile file;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label path;
    @FXML
    private Label encryptedAt;
    @FXML
    private Label modifiedAt;
    @FXML
    private FlowPane tags;

    public FileCard(EncryptedFile file, int displayedIndex) {
        super(FXML);
        this.file = file;
        id.setText(displayedIndex + ". ");
        String statusText;
        if (file.getFileStatus() == FileStatus.ACTIVE) {
            statusText = "";
        } else {
            statusText = " [" + file.getFileStatus() + "]";
        }
        name.setText(file.getFileName().value + statusText);
        path.setText("Location:\t\t" + file.getFilePath().value);
        Date fileEncryptedAt = file.getEncryptedAt().value;
        if (fileEncryptedAt.equals(new Date(0))) {
            encryptedAt.setText("Encrypted:\tUnknown");
        } else {
            encryptedAt.setText("Encrypted: \t" + DateUtil.formatDateForDisplay(fileEncryptedAt));
        }
        Date fileModifiedAt = file.getModifiedAt().value;
        if (fileModifiedAt.equals(new Date(0))) {
            modifiedAt.setText("Last edited:\tUnknown");
        } else {
            modifiedAt.setText("Last edited:\t" + DateUtil.formatDateForDisplay(fileModifiedAt));
        }
        file.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FileCard)) {
            return false;
        }

        // state check
        FileCard card = (FileCard) other;
        return id.getText().equals(card.id.getText())
                && file.equals(card.file);
    }
}
