package calofit.ui;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import calofit.commons.util.ObservableUtil;
import calofit.model.meal.Meal;

/**
 * Progress bar component representing current calorie budget.
 */
public class BudgetBar extends StackPane {
    private static final String FXML = "BudgetBar.fxml";

    private ListProperty<Meal> todayMeals = new SimpleListProperty<>();

    private DoubleBinding totalConsumed = Bindings.createDoubleBinding(() -> {
        double total = 0;
        if (todayMeals.get() != null) {
            for (Meal m : todayMeals.get()) {
                total += m.getDish().getCalories().getValue();
            }
        }
        return total;
    }, todayMeals);
    private DoubleProperty budget = new SimpleDoubleProperty(Double.POSITIVE_INFINITY);
    private DoubleExpression budgetPercent = Bindings.createDoubleBinding(() -> {
        if (budget.getValue() <= 0) {
            return 0.0;
        }
        return totalConsumed.get() / budget.get();
    }, totalConsumed, budget);

    private ObjectExpression<Color> barColor = Bindings.createObjectBinding(() -> {
        double percent = budgetPercent.get();
        percent = Math.max(0, Math.min(1, percent));
        return Color.GREEN.interpolate(Color.RED, percent);
    }, budgetPercent);

    private StringExpression infoText = Bindings.createStringBinding(() -> {
        if (budget.get() == Double.POSITIVE_INFINITY) {
            return String.format("%.1f", totalConsumed.get());
        }
        return String.format("%.1f / %.1f", totalConsumed.get(), budget.get());
    }, totalConsumed, budget);

    private ObservableValue<Function<Meal, ColumnConstraints>> columnWidthFunc =
        Bindings.createObjectBinding(() -> this::buildWidth, budget);
    private ObservableList<ColumnConstraints> colWidths = ObservableUtil.map(todayMeals, columnWidthFunc);
    private ObservableList<Node> mealBars = ObservableUtil.mapWithIndex(todayMeals, (index, meal) -> {
        Label mealBox = new Label(meal.getDish().getName().fullName);
        mealBox.getStyleClass().add("budgetbar-meal");
        mealBox.setAlignment(Pos.CENTER);
        GridPane.setColumnIndex(mealBox, index);
        GridPane.setHgrow(mealBox, Priority.ALWAYS);
        GridPane.setVgrow(mealBox, Priority.ALWAYS);
        return mealBox;
    });

    @FXML
    private GridPane mealBarPane;

    @FXML
    private Text infoNode;

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

        Bindings.bindContent(mealBarPane.getColumnConstraints(), colWidths);
        Bindings.bindContent(mealBarPane.getChildren(), mealBars);
        mealBarPane.backgroundProperty().bind(Bindings.createObjectBinding(() ->
            new Background(new BackgroundFill(barColor.get(), CornerRadii.EMPTY, Insets.EMPTY)), barColor));
    }

    /**
     * Utility method to construct the appropriate {@link ColumnConstraints} for a given {@link Meal} value.
     * @param meal Meal to represent in the bar.
     * @return Constraint sized for the meal.
     */
    private ColumnConstraints buildWidth(Meal meal) {
        ColumnConstraints col = new ColumnConstraints();
        col.percentWidthProperty().bind(Bindings.createDoubleBinding(() -> {
            if (budget.get() == Double.POSITIVE_INFINITY) {
                //-1.0 indicates unconstrained.
                return -1.0;
            } else {
                double calories = meal.getDish().getCalories().getValue();
                return 100.0 * calories / budget.get();
            }
        }, budget));
        return col;
    }


    public ListProperty<Meal> mealsProperty() {
        return todayMeals;
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }

}
