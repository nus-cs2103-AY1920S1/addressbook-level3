package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

/**
 * Abstraction of an interface supporting a window navigation operation that switches to
 * the implementing window.
 */
@FunctionalInterface
public interface WindowNavigation {
    void switchToThisWindow(MainWindow mainWindow, Logic logic, Model model);
}
