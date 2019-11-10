package com.dukeacademy.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Controller class for a drawing canvas ui component. It contains functionality to switch between a pencil and an
 * eraser for sketching purposes. It also has a clear button to empty the drawing canvas. It also exposes methods
 * to extract the sketch as a JavaFx WritableImage instance.
 */
public class NotesCanvas extends UiPart<Region> {
    private static final String FXML = "NotesCanvas.fxml";
    private static final double pencilWidth = 2;
    private static final double eraserWidth = 20;

    @FXML
    private Pane canvasContainer;

    @FXML
    private Canvas canvas;

    @FXML
    private Button pencilButton;

    @FXML
    private Button eraserButton;

    @FXML
    private Button clearButton;

    private final GraphicsContext graphicsContext;

    public NotesCanvas() {
        super(FXML);

        graphicsContext = canvas.getGraphicsContext2D();

        this.initCanvasDimensions();
        this.initDraw(graphicsContext);
        this.initToolbar();
    }

    /**
     * Clears the current drawing canvas of any sketches.
     */
    public void clearCanvas() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Extracts the user's sketch in the drawing as a JavaFX WritableImage instance.
     * @return JavaFX WritableImage instance corresponding to the sketch in the drawing canvas
     */
    public WritableImage getImage() {
        int imageWidth = (int) canvas.getWidth();
        int imageHeight = (int) canvas.getHeight();

        WritableImage image = new WritableImage(imageWidth, imageHeight);
        canvas.snapshot(null, image);
        return image;
    }

    /**
     * Draws the given JavaFX WritableImage instance into the drawing canvas.
     * @param image the JavaFX WritableImage to be drawn
     */
    public void drawImage(WritableImage image) {
        graphicsContext.drawImage(image, 0, 0);
    }

    /**
     * Helper method to initialize the canvas. The canvas will be initialized to have a white background and to use a
     * black pencil.
     * @param graphicsContext the graphics context of the JavaFX Canvas instance
     */
    private void initDraw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fill();

        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(pencilWidth);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new MouseClickHandler());
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new MouseDragHandler());
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new MouseReleaseHandler());
    }

    /**
     * Helper method to clip the canvas to the correct size.
     */
    private void initCanvasDimensions() {
        Rectangle clipRect = new Rectangle(canvasContainer.getWidth(), canvasContainer.getHeight());
        clipRect.heightProperty().bind(canvasContainer.heightProperty());
        clipRect.widthProperty().bind(canvasContainer.widthProperty());

        canvasContainer.setClip(clipRect);
    }

    /**
     * Helper method to initialize the toolbar buttons.
     */
    private void initToolbar() {
        // The pencil option will set the canvas to draw a black line
        pencilButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setLineWidth(pencilWidth);
            canvas.setCursor(Cursor.DEFAULT);
        });

        // The eraser option will set the cursor to a circle and the canvas to erase over mouse drag
        eraserButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            graphicsContext.setStroke(Color.WHITE);
            graphicsContext.setLineWidth(eraserWidth);

            Circle circle = new Circle(eraserWidth / 2, null);

            circle.setStroke(Color.BLACK);

            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);

            Image image = circle.snapshot(sp, null);
            canvas.setCursor(new ImageCursor(image));
        });

        // The clear button will clear the canvas of any sketches
        clearButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> clearCanvas());
    }


    /**
     * Private mouse click handler for the drawing canvas.
     */
    private class MouseClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            graphicsContext.moveTo(event.getX(), event.getY());
            graphicsContext.beginPath();
            graphicsContext.stroke();
        }
    }

    /**
     * Private mouse drag handler for the drawing canvas.
     */
    private class MouseDragHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
        }
    }

    /**
     * Private mouse release handler for the drawing canvas.
     */
    private class MouseReleaseHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
        }
    }
}
