package seedu.elisa.game;

import javafx.scene.paint.Color;

/**
 * A class to represent food that takes up only one square.
 *
 */
public class Food {
    public static final Color COLOR = Color.ROSYBROWN;

    private Point point;

    Food(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}