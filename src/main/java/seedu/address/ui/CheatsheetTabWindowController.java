package seedu.address.ui;

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

    @FXML
    private TextArea cheatsheetArea;

    @FXML
    private ListView<String> tagArea;

    /**
     * Displays the question of the flashcard specified in the flashcard tab window.
     * @param cheatSheet flashcard to be displayed
     */
    public void loadCheatSheet(CheatSheet cheatSheet) {

        StringBuilder toDisplay = new StringBuilder();

        for (Content c: cheatSheet.getSortedContents()) {
            toDisplay.append(c.toString())
                    .append("\n");
        }

        cheatsheetArea.setText(toDisplay.toString());

        Set<Tag> tags = cheatSheet.getTags();
        ObservableList<String> items = FXCollections.observableArrayList();

        items.add("List of tags:");
        for (Tag t: tags) {
            items.add(t.toString());
        }

        tagArea.setItems(items);

        highlightTabs(0);
    }

    private void highlightTabs(int index) {
        tagArea.getSelectionModel().select(index);
    }
}

