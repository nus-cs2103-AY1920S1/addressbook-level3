package seedu.address.ui;

import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.ui.modules.LoadBankPanel;
import seedu.address.ui.modules.PersonListPanel;
import seedu.address.ui.modules.TitleScreenPanel;

/**
 * Displays the screen for Dukemon.
 */
public class ModularDisplay {

//    private final LoadBankPanel loadBankPanel;
    private final PersonListPanel personListPanel;
    private final TitleScreenPanel titleScreenPanel;

    /**
     * Changes the screen.
     *
     * @param logic Logic used for rendering lists.
     */
    public ModularDisplay(Logic logic) {
//        loadBankPanel = new LoadBankPanel(logic.getFilteredPersonList());
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        titleScreenPanel = new TitleScreenPanel();
    }

    /**
     * Initially displays title.
     *
     * @param paneToDisplay The view to change.
     */
    public void displayTitle(StackPane paneToDisplay) {
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    /**
     * Changes back to home display.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToHome(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    /**
     * Changes to the word list.
     *
     * @param paneToDisplay The view to change.
     */
    public void swapToList(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Changes to list the word banks.
     *
     * @param paneToDisplay The view to change.
     */
//    public void swapToBanks(StackPane paneToDisplay) {
//        paneToDisplay.getChildren().clear();
//        paneToDisplay.getChildren().add(loadBankPanel.getRoot());
//    }

}
