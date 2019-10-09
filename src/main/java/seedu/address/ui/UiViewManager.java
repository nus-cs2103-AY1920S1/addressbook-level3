package seedu.address.ui;

import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * A class to manage changes in the ui made from a command.
 */
public class UiViewManager {
    private static Ui ui;

    public UiViewManager() { }

    public static void setUi(Ui ui) {
        UiViewManager.ui = ui;
    }

    public void changeUiDetailsView(PersonDisplay p) {
        ui.changeView(p);
    }

    public void changeUiDetailsView(Group g) {
        ui.changeView(g);
    }

    public void changeUiDetailsView(String message) {
        ui.changeView(message);
    }

    /**
     * Method to export visual representation of a person.
     * @param p The person to be exported.
     */
    public void exportVisual(PersonDisplay p) {
        if (ui == null) {
            return;
        }
        ui.exportVisual(p);
    }

    /**
     * Method to export group visual representation.
     * @param g The group to be exported.
     */
    public void exportVisual(Group g) {
        if (ui == null) {
            return;
        }
        ui.exportVisual(g);
    }
}
