package seedu.address.ui;

import javafx.scene.layout.StackPane;
import seedu.address.gamemanager.GameManager;
import seedu.address.logic.Logic;
import seedu.address.ui.modules.PersonListPanel;
import seedu.address.ui.modules.TitleScreenPanel;

public class ModularDisplay {

    private final PersonListPanel personListPanel;
    private final TitleScreenPanel titleScreenPanel;

    public ModularDisplay(GameManager gameManager) {
        personListPanel = new PersonListPanel(gameManager.getFilteredPersonList());
        titleScreenPanel = new TitleScreenPanel();
    }

    public void displayTitle(StackPane paneToDisplay) {
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    public void swapToHome(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(titleScreenPanel.getRoot());
    }

    public void swapToList(StackPane paneToDisplay) {
        paneToDisplay.getChildren().clear();
        paneToDisplay.getChildren().add(personListPanel.getRoot());
    }

}
