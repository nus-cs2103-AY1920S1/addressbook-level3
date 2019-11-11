package seedu.billboard.ui.charts;

import javafx.beans.NamedArg;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BubbleChart;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

/**
 * Implementation of JavaFx {@code BubbleChart} that maintains a proportional bubble.
 */
public class ProportionalBubbleChart<X, Y> extends BubbleChart<X, Y> {

    public ProportionalBubbleChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
        super(xAxis, yAxis);
    }

    @Override
    protected void layoutPlotChildren() {
        super.layoutPlotChildren();
        getData().stream()
                .flatMap(series -> series.getData().stream())
                .map(Data::getNode)
                .map(node -> ((StackPane) node).getShape())
                .forEach(shape -> ((Ellipse) shape).setRadiusX(((Ellipse) shape).getRadiusY()));
    }
}
