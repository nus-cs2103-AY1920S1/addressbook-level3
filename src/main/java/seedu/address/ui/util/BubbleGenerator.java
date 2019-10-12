package seedu.address.ui.util;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

/**
 * A class to create a bubble with contents inside it.
 */
public class BubbleGenerator {
    private String bubbleContent;
    private StackPane bubbleContainer;
    private double size;
    private double borderSize;

    public BubbleGenerator(int content, double size, double borderSize) {
        this.bubbleContainer = new StackPane();
        this.bubbleContent = content + "";
        this.size = size;
        this.borderSize = borderSize;
    }

    public BubbleGenerator(String content, double size, double borderSize) {
        this.bubbleContainer = new StackPane();
        this.bubbleContent = content;
        this.size = size;
        this.borderSize = borderSize;
    }

    public StackPane getBubble() {
        Ellipse outer = new Ellipse(size / 2, size / 2);
        Ellipse inner = new Ellipse(size / 2 - borderSize, size / 2 - borderSize);
        final Shape bubble = Shape.subtract(outer, inner);
        bubble.setStyle("-fx-fill: white");
        Label bubbleLabel = new Label(bubbleContent);
        bubbleContainer.getChildren().addAll(bubble, bubbleLabel);
        return bubbleContainer;
    }
}
