package seedu.guilttrip.model.reminders.messages;

import javafx.scene.Node;

/**
 * Cell represents an item in the displayed generalReminder pop up.
 */
public abstract class Cell {
    private Node node;
    private int xCoordinate;
    private int yCoordinate;

    public abstract Node getNode();
}
