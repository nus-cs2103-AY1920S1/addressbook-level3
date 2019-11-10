package seedu.elisa.game;

import static seedu.elisa.game.Grid.SIZE;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * This is the class that colors the snake.
 */
public class Painter {

    private static Snake snake;

    /**
     * The method to paint the snake.
     * @param grid
     * @param gc
     */
    public static void paint(Grid grid, GraphicsContext gc) {
        gc.setFill(Grid.COLOR);
        gc.fillRect(0, 0, grid.getWidth(), grid.getHeight());

        // Now the Food
        gc.setFill(Food.COLOR);
        paintPoint(grid.getFood().getPoint(), gc);

        // Now the Wall
        if (grid.isHard()) {
            List<Wall> walls = grid.getWalls();
            for (Wall w : walls) {
                gc.setFill(Wall.COLOR);
                paintPoint(w.getPoint(), gc);
            }
        }

        // Now the snake
        snake = grid.getSnake();
        gc.setFill(Snake.COLOR);
        snake.getPoints().forEach(point -> paintPoint(point, gc));
        if (!snake.isSafe()) {
            gc.setFill(Snake.DEAD);
            paintPoint(snake.getHead(), gc);
        }

        // The score
        snake.setCurrentScore(100 * (snake.getPoints().size() - 1));
        gc.setFill(Color.BEIGE);
        gc.fillText("Current score : " + snake.getCurrentScore(), 10, 470);
    }

    /**
     * Paints the point.
     * @param point
     * @param gc
     */
    private static void paintPoint(Point point, GraphicsContext gc) {
        gc.fillRect(point.getX() * SIZE, point.getY() * SIZE, SIZE, SIZE);
    }

    /**
     * Paints the grid that the snake collides with itself.
     * @param gc
     */
    public static void paintResetMessage(GraphicsContext gc) {
        gc.setFill(Color.AQUAMARINE);
        gc.fillText("\nHit E for (E)asy Mode. \nHit H for (H)ard Mode. \nHit ESC to get back to WORK!!!", 10, 10);
    }

    public static int getCurrentScore() {
        return snake.getCurrentScore();
    }

    public static void resetScore() {
        snake.setCurrentScore(0);
    }

    /**
     * Display high score.
     * @param gc
     * @param score
     */
    public static void paintHighScore(GraphicsContext gc, int score) {
        gc.setFill(Color.BEIGE);
        if (getCurrentScore() > score) {
            score = getCurrentScore();
        }
        gc.fillText("High score: " + String.valueOf(score), 10, 490);
    }
}
