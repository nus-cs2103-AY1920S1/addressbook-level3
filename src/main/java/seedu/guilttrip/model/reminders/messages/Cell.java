package seedu.guilttrip.model.reminders.messages;

import javafx.scene.Node;

/**
 * Cell represents an item in the displayed reminder pop up.
 */
public abstract class Cell {
    private Node node;
    private int x_coordinate;
    private int y_coordinate;

    public abstract Node getNode();
}
