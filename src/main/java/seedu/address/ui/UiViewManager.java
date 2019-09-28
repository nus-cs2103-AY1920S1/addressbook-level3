package seedu.address.ui;

import seedu.address.model.person.Person;

public class UiViewManager {
    private static Ui ui;

    public UiViewManager() {

    }
    
    public static void setUi(Ui ui) {
        UiViewManager.ui = ui;
    }
    
    public void changeUiDetailsView(Person p) {
        ui.changeView(p);
    }
}
