/*
@@author shihaoyap
 */

package seedu.address.ui;

import javafx.scene.Node;
import seedu.address.logic.Logic;
import seedu.address.model.Model;

/**
 * Represents a distinct part of the UI - in particular Tabs
 * It contains a scene graph with a root node of type {@code T}.
 */
public abstract class Tabs<T extends Node> extends UiPart<T> {
    protected MainWindow mainWindow;
    protected Logic logic;
    protected Model model;

    protected Tabs(String fxml, MainWindow mainWindow, Logic logic) {
        super(fxml);
        this.mainWindow = mainWindow;
        this.logic = logic;
    }

}
