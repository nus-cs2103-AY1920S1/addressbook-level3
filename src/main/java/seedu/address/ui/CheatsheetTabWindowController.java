package seedu.address.ui;

import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.tag.Tag;

/**
 * Controller class that handles what happens within the Cheatsheet Tab within the Activity Window.
 */
public class CheatsheetTabWindowController {

    private static Optional<CheatSheet> currCheatSheet;

    @FXML
    private TextArea cheatsheetArea;

    @FXML
    private ListView<String> tagArea;

    public static Optional<CheatSheet> getCurrCheatSheet() {
        return currCheatSheet;
    }

    /**
     * Displays empty body of the cheatsheet
     */
    public void loadEmpty() {
        currCheatSheet = Optional.empty();
        cheatsheetArea.setText("");
        tagArea.setItems(null);
    }

    /**
     * Displays the question of the cheatsheet specified in the cheatsheet tab window.
     * @param cheatSheet cheatsheet to be displayed
     */
    public void loadCheatSheet(CheatSheet cheatSheet) {
        currCheatSheet = Optional.of(cheatSheet);

        StringBuilder toDisplay = new StringBuilder();

        for (Content c: cheatSheet.getSortedContents()) {
            toDisplay.append(c.toString())
                    .append("\n");
        }

        cheatsheetArea.setText(toDisplay.toString());

        Set<Tag> tags = cheatSheet.getTags();
        ObservableList<String> items = FXCollections.observableArrayList();

        items.add("List of tags:");
        items.add("All Tags");

        int counter = 1;
        for (Tag t: tags) {
            items.add(counter + ". " + t.getTagName());
            counter++;
        }

        tagArea.setItems(items);

        highlightTabs(1);
    }

    /**
     * Displays only the contents for a specific tag
     * @param tagIndex the tag index shown on the GUI
     */
    public void showSpecificTagContents(int tagIndex) {
        int targetIndex = tagIndex + 1;
        highlightTabs(targetIndex);

        String tagName = tagArea.getItems().get(targetIndex);
        Tag targetTag = new Tag(tagName.substring(3));

        StringBuilder toDisplay = new StringBuilder();

        for (Content c: currCheatSheet.get().getContents()) {
            if (c.getTags().contains(targetTag)) {
                toDisplay.append(c.toString())
                        .append("\n");
            }
        }

        cheatsheetArea.setText(toDisplay.toString());
    }

    private void highlightTabs(int index) {
        tagArea.getSelectionModel().select(index);
    }
}

