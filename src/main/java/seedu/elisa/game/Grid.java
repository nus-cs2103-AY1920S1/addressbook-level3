package seedu.elisa.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

/**
 * Grid is the position for every point.
 */
public class Grid {
    /**
     * The side length of each square point in the grid.
     */
    public static final int SIZE = 10;
    public static final Color COLOR = new Color(0.1, 0.1, 0.1, 1);

    private final int cols;
    private final int rows;

    private boolean hard;
    private Snake snake;
    private Food food;
    private List<Wall> walls;

    public Grid(final double width, final double height) {
        rows = (int) width / SIZE;
        cols = (int) height / SIZE;

        this.hard = false;

        // initialize the snake at the centre of the screen
        snake = new Snake(this, new Point(rows / 2, cols / 2));

        // put the food at a random location
        food = new Food(getRandomPoint());
    }

    public Grid(final double width, final double height, boolean hard) {
        rows = (int) width / SIZE;
        cols = (int) height / SIZE;

        this.hard = hard;

        // initialize the snake at the centre of the screen
        snake = new Snake(this, new Point(rows / 2, cols / 2));

        // put the food at a random location
        food = new Food(getRandomPoint());

        //put the wall at a random location
        walls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            walls.add(new Wall(getRandomPoint()));
        }
    }
    /**
     * Ensures the snake does not go beyond the screen.
     * @param point
     * @return
     */
    public Point wrap(Point point) {
        int x = point.getX();
        int y = point.getY();
        if (x >= rows) {
            x = 0;
        }
        if (y >= cols) {
            y = 0;
        }
        if (x < 0) {
            x = rows - 1;
        }
        if (y < 0) {
            y = cols - 1;
        }
        return new Point(x, y);
    }

    private Point getRandomPoint() {
        Random random = new Random();
        Point point;
        do {
            point = new Point(random.nextInt(rows), random.nextInt(cols));
        } while (point.equals(snake.getHead()));
        return point;
    }

    /**
     * This method is called in every cycle of execution.
     */
    public void update() {
        if (food.getPoint().equals(snake.getHead())) {
            snake.extend();
            food.setPoint(getRandomPoint());
        } else {
            if (hard) {
                for (Wall w : walls) {
                    if (w.getPoint().equals(snake.getHead())) {
                        snake.markAsUnsafe();
                        return;
                    }
                }
            }
            snake.move();
        }
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public double getWidth() {
        return rows * SIZE;
    }

    public double getHeight() {
        return cols * SIZE;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public boolean isHard() {
        return hard;
    }
}
