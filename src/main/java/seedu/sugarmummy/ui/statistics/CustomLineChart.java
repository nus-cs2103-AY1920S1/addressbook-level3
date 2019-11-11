package seedu.sugarmummy.ui.statistics;

import static java.util.Objects.requireNonNull;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//@@author chen-xi-cx

/**
 * An extended version of JavaFX {@code LineChart} to add horizontal range markers.
 * Area between horizontal range markers is highlighted by user specified color.
 */
public class CustomLineChart<X, Y> extends LineChart<X, Y> {

    private ObservableList<Data<Y, Y>> horizontalRangeMarkers;

    public CustomLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[]{
                data.XValueProperty()
        });
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[]{
                data.YValueProperty()
        });
        horizontalRangeMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
    }

    /**
     * Adds two horizontal markers and color the area between them.
     *
     * @param marker the y coordinates (Y1,Y2) of the two horizontal markers, Y2 must be greater than Y1.
     * @param color  fills the region within horizontal range marker with {@code Color} object color.
     */
    public void addHorizontalRangeMarker(Data<Y, Y> marker, Color color) {
        requireNonNull(marker);

        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(color);

        marker.setNode(rectangle);

        getPlotChildren().add(rectangle);
        horizontalRangeMarkers.add(marker);
    }

    /**
     * Removes all horizontal range marker from the chart.
     */
    public void removeAllHorizontalRangeMarker() {
        getPlotChildren().clear();
    }

    /**
     * Updates and layout the plot children (including horizontal range markers).
     */
    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();
        for (Data<Y, Y> horizontalRangeMarker : horizontalRangeMarkers) {
            Rectangle rectangle = (Rectangle) horizontalRangeMarker.getNode();
            Double upperYDisplayPos = Math.max(getYAxis().getDisplayPosition(horizontalRangeMarker.getYValue()), 0.0);
            rectangle.setY(upperYDisplayPos);
            rectangle.setHeight(getYAxis().getDisplayPosition(horizontalRangeMarker.getXValue())
                    - upperYDisplayPos);
            rectangle.setX(0d);
            rectangle.setWidth(getBoundsInLocal().getWidth());
            rectangle.toBack();
        }
    }
}
