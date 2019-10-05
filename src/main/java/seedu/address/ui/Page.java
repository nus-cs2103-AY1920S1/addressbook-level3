package seedu.address.ui;

import javafx.scene.Node;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

/**
 * Base class of all pages.
 * @param <T> JavaFX base node type from which this page is constructed.
 */
public abstract class Page<T extends Node> extends UiPart<T> {

    protected MainWindow mainWindow;
    protected Logic logic;
    protected Model model;


    protected Page(String fxml, MainWindow mainWindow, Logic logic, Model model) {
        super(fxml);
        this.mainWindow = mainWindow;
        this.logic = logic;
        this.model = model;
        fillInnerParts();
    }

    /**
     * Base switchTo method, that activates the switch handler in the mainWindow.
     * Passes
     */
    public void switchTo() {
        mainWindow.switchHandler(getRoot(), this::fillInnerParts);
    }

    protected abstract void fillInnerParts();
}
