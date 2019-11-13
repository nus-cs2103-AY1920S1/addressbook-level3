package calofit.ui;

import static calofit.commons.util.ObservableListUtil.map;
import static calofit.commons.util.ObservableUtil.liftA2;
import static calofit.commons.util.ObservableUtil.liftA3;
import static calofit.commons.util.ObservableUtil.map;
import static calofit.commons.util.ObservableUtil.mapToObject;

import java.io.IOException;
import java.net.URL;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Callback;

import org.controlsfx.control.SegmentedBar;

import calofit.commons.util.ObservableListUtil;
import calofit.model.dish.Dish;
import calofit.model.meal.Meal;

/**
 * Progress bar component representing current calorie budget.
 */
public class BudgetBar extends StackPane {
    private static final String FXML = "BudgetBar.fxml";
    private static final Color COLOR_ZERO = Color.GREEN;
    private static final Color COLOR_MAX = Color.ORANGERED;

    private ListProperty<Meal> todayMeals = new SimpleListProperty<>();
    private DoubleProperty budget = new SimpleDoubleProperty(Double.POSITIVE_INFINITY);
    private DoubleProperty bufferFraction = new SimpleDoubleProperty(1.2);

    private DoubleBinding totalConsumed = ObservableListUtil.sum(
        ObservableListUtil.lazyMap(todayMeals, meal -> (double) meal.getDish().getCalories().getValue())
    );

    private DoubleExpression budgetPercent = totalConsumed.divide(budget);

    private ObjectExpression<Color> barColor = mapToObject(budgetPercent, percent -> {
        percent = Math.max(0, Math.min(1, percent));
        Color base = COLOR_ZERO.interpolate(COLOR_MAX, percent);
        return Color.hsb(base.getHue(), base.getSaturation(), 1);
    });

    private StringExpression infoText = Bindings.createStringBinding(() -> {
        if (budget.get() == Double.POSITIVE_INFINITY) {
            return String.format("%.1f", totalConsumed.get());
        }
        return String.format("%.1f / %.1f", totalConsumed.get(), budget.get());
    }, totalConsumed, budget);

    private ObservableList<MealSegment> mealBars = map(todayMeals, MealSegment::new);

    /**
     * Represents the remaining budget available.
     * Auto-expands to the width remaining.
     */
    private class BufferSegment extends SegmentedBar.Segment {
        private BufferSegment() {
            super(0, "buffer");
            valueProperty().bind(liftA3(budget, totalConsumed, bufferFraction, (budgetVal, totalVal, bufferFrac) ->
                budgetVal == Double.POSITIVE_INFINITY
                ? 0
                : Math.max(0, (budgetVal * bufferFrac) - totalVal)
                ));
        }
    }

    /**
     * Represents a meal eaten today.
     */
    private class MealSegment extends SegmentedBar.Segment {
        private final Meal meal;
        private MealSegment(Meal meal) {
            super(meal.getDish().getCalories().getValue(), meal.getDish().getName().fullName);
            this.meal = meal;
        }

        public Meal getMeal() {
            return meal;
        }
    }

    @FXML
    private SegmentedBar<SegmentedBar.Segment> mealSegments;

    @FXML
    private Text infoNode;

    @FXML
    private Line budgetMark;

    @FXML
    private Line budgetExtraMark;

    public BudgetBar() {
        URL x = BudgetBar.class.getResource(UiPart.FXML_FILE_FOLDER + FXML);
        FXMLLoader loader = new FXMLLoader(x);
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        infoNode.textProperty().bind(infoText);
        infoNode.fillProperty().bind(barColor);

        mealSegments.setSegments(ObservableListUtil.concat(mealBars, FXCollections.observableArrayList(
            new BufferSegment()
        )));

        Callback<SegmentedBar.Segment, Node> orig = mealSegments.getSegmentViewFactory();
        mealSegments.setSegmentViewFactory(segment -> {
            SegmentedBar.SegmentView view = (SegmentedBar.SegmentView) orig.call(segment);
            if (segment instanceof BufferSegment) {
                view.getStyleClass().addAll("buffer-segment", "bar-segment");
            } else {
                view.getStyleClass().addAll("meal-segment", "bar-segment");
            }
            return view;
        });
        mealSegments.setInfoNodeFactory(segment -> {
            Label label = new Label();
            label.getStyleClass().add("bar-label");
            if (segment instanceof BufferSegment) {
                label.textProperty().bind(map(
                    liftA2(budget, totalConsumed, (b, total) -> b - total),
                    val -> "Remaining: " + val
                ));
                label.getStyleClass().add("buffer-label");
            } else {
                MealSegment seg = (MealSegment) segment;
                Dish d = seg.getMeal().getDish();
                label.setText(d.getName().fullName + ": " + d.getCalories().getValue());
                label.getStyleClass().add("meal-label");
            }
            return label;
        });

        DoubleExpression budgetXPos = markPosition(Bindings.createDoubleBinding(() -> 1.0));
        budgetMark.startXProperty().bind(budgetXPos);
        budgetMark.endXProperty().bind(budgetXPos);
        budgetMark.visibleProperty().bind(map(budget, val -> Double.isFinite((double) val)));

        DoubleExpression budgetExtraXPos = markPosition(bufferFraction);
        budgetExtraMark.startXProperty().bind(budgetExtraXPos);
        budgetExtraMark.endXProperty().bind(budgetExtraXPos);
        budgetExtraMark.visibleProperty().bind(map(budget, val -> Double.isFinite((double) val)));
    }

    /**
     * Computes the position of the mark, given the fraction it should be relative to the whole bar.
     * @param frac Fractional position relative to the bar
     * @return Computed position of the mark
     */
    private DoubleExpression markPosition(ObservableDoubleValue frac) {
        return Bindings.createDoubleBinding(() -> {
            Bounds bounds = mealSegments.boundsInParentProperty().get();
            double totalVal = totalConsumed.get();
            double budgetVal = budget.get();
            double bufferFrac = bufferFraction.get();
            double markerFrac = frac.get();
            if (totalVal < budgetVal * bufferFrac) {
                return bounds.getMinX() + bounds.getWidth() * markerFrac / bufferFrac;
            } else {
                return bounds.getMinX() + bounds.getWidth() * (budgetVal / totalVal) * (markerFrac / bufferFrac);
            }
        }, mealSegments.boundsInParentProperty(), totalConsumed, budget, bufferFraction, frac);
    }

    public ListProperty<Meal> mealsProperty() {
        return todayMeals;
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

}
