package seedu.jarvis.ui.template;

import javafx.scene.Node;

import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.UiPart;

/**
 * Base class of all views.
 *
 * @param <T> JavaFX base node type from which this view is constructed.
 */
public abstract class View<T extends Node> extends UiPart<T> {

    protected MainWindow mainWindow;
    protected Logic logic;
    protected Model model;

    protected View(String fxml, MainWindow mainWindow, Logic logic, Model model) {
        super(fxml);
        this.mainWindow = mainWindow;
        this.logic = logic;
        this.model = model;
    }

    /**
     * The callback function {@code CommandUpdater} to run after executing a command successfully.
     */
    public abstract void fillPage();
}
