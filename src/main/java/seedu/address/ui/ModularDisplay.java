package seedu.address.ui;

import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.modules.PersonListPanel;
import seedu.address.ui.modules.TitleScreenPanel;

/**
 * Displays the screen for Dukemon.
 */
public class ModularDisplay {

    private final PersonListPanel personListPanel;
    private final TitleScreenPanel titleScreenPanel;

    /**
     * Changes the screen.
     *
     * @param logic
     */
    public ModularDisplay(Logic logic) {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        titleScreenPanel = new TitleScreenPanel();
    }

    public void displayTitle(StackPane paneToDisplay) {
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    /**
     * Changes back to home display.
     *
     * @param paneToDisplay
     */
    public void swapToHome(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    /**
     * Changes one screen to another.
     *
     * @param paneToDisplay
     */
    public void swapToList(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(personListPanel.getRoot());
    }

}
