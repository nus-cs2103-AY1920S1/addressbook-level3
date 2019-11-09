package seedu.elisa.game;

import javafx.scene.paint.Color;

/**
 * A class to represent wall that takes up only one square.
 *
 */
public class Wall {
    public static final Color COLOR = Color.BLUE;

    private Point point;

    Wall(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
