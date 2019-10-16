package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.model.file.EncryptedFile;
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
    private FlowPane tags;

    public FileCard(EncryptedFile file, int displayedIndex) {
        super(FXML);
        this.file = file;
        id.setText(displayedIndex + ". ");
        name.setText(file.getFileName().value);
        path.setText("Location:\t\t" + file.getFilePath().value);
        encryptedAt.setText("Encrypted: \t" + DateUtil.formatDateForDisplay(file.getEncryptedAt().value));
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
