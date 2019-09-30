package seedu.address.ui;

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

    public void changeUiDetailsView(Person p) {
        ui.changeView(p);
    }
}
