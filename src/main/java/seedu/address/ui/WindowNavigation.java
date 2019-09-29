package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

@FunctionalInterface
public interface WindowNavigation {

    public void switchToThisWindow(Stage stage, Logic logic, Model model);

}
