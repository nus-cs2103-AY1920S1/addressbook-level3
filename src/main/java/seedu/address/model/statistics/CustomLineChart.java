package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * An extended line chart to add horizontal range markers.
 * Area between horizontal range markers is highlighted by user specified color.
 */
public class CustomLineChart<X, Y> extends LineChart<X, Y> {

    private ObservableList<Data<Y, Y>> horizontalRangeMarkers;

    public CustomLineChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.XValueProperty()});
        horizontalRangeMarkers = FXCollections.observableArrayList(data -> new Observable[] {data.YValueProperty()});
        horizontalRangeMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
    }

    //TODO: create a new marker class.
    /**
     * Adds two horizontal markers and color the area between them.
     * @param marker the y coordinates of the two horizontal markers.
     * @param color fill the region with {@code Color} object.
     */
    public void addHorizontalRangeMarker(Data<Y, Y> marker, Color color) {
        requireNonNull(marker);

        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(color.deriveColor(1, 1, 1, 0.2));

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

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();
        for (Data<Y, Y> horizontalRangeMarker : horizontalRangeMarkers) {
            Rectangle rectangle = (Rectangle) horizontalRangeMarker.getNode();
            rectangle.setY(getYAxis().getDisplayPosition(horizontalRangeMarker.getYValue()));
            rectangle.setHeight(getYAxis().getDisplayPosition(horizontalRangeMarker.getXValue())
                    - getYAxis().getDisplayPosition(horizontalRangeMarker.getYValue()));
            rectangle.setX(0d);
            rectangle.setWidth(getBoundsInLocal().getWidth());
            rectangle.toBack();
        }
    }
}
