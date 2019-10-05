package seedu.address.ui.template;

import javafx.scene.Node;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * Base class of all pages.
 * It is the responsibility of implementing classes to ensure components
 * specific to them are initialised and filled with data properly.
 *
 * @param <T> JavaFX base node type from which this page is constructed.
 */
public abstract class Page<T extends Node> extends UiPart<T> {

    protected MainWindow mainWindow;
    protected Logic logic;
    protected Model model;
    /**
     * The callback function to execute, after executing a command successfully.
     * It must be defined by the implementing classes.
     */
    protected MainWindow.CommandUpdater commandUpdater;


    protected Page(String fxml, MainWindow mainWindow, Logic logic, Model model) {
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
