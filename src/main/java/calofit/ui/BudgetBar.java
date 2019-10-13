package calofit.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.skin.ProgressBarSkin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import calofit.model.meal.Meal;

/**
 * Progress bar component representing current calorie budget.
 */
public class BudgetBar extends StackPane {
    private static final String FXML = "BudgetBar.fxml";

    private ObjectProperty<ObservableList<Meal>> allMeals = new SimpleObjectProperty<>();
    private ObjectBinding<ObservableList<Meal>> filteredMeals = Bindings.createObjectBinding(() -> {
        if (allMeals.get() == null) {
            return FXCollections.emptyObservableList();
        }
        return allMeals.get().filtered(BudgetBar::isMealToday);
    }, allMeals);
    private DoubleBinding totalConsumed = Bindings.createDoubleBinding(() -> {
        double total = 0;
        if (filteredMeals.get() != null) {
            for (Meal m : filteredMeals.get()) {
                total += m.getDish().getCalories().getValue();
            }
        }
        return total;
    }, filteredMeals);
    private DoubleProperty budget = new SimpleDoubleProperty(Double.POSITIVE_INFINITY);
    private DoubleExpression budgetPercent = Bindings.createDoubleBinding(() -> {
        if (budget.getValue() < 0) {
            return ProgressBar.INDETERMINATE_PROGRESS;
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

    @FXML
    private ProgressBar bar;

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

        bar.progressProperty().bind(budgetPercent);
        bar.skinProperty().addListener((observable, oldVal, newVal) -> {
            ProgressBarSkin skin = (ProgressBarSkin) newVal;
            for (Node c : skin.getChildren()) {
                if (c.getStyleClass().contains("bar")) {
                    Region bar = (Region) c;
                    bar.backgroundProperty().bind(
                        Bindings.createObjectBinding(BudgetBar.this::makeBarBg, barColor));
                }
            }
        });
    }

    private Background makeBarBg() {
        return new Background(new BackgroundFill(barColor.get(), CornerRadii.EMPTY, Insets.EMPTY));
    }

    private static boolean isMealToday(Meal meal) {
        return meal.getTimestamp()
            .getDateTime().toLocalDate()
            .equals(LocalDate.now());
    }

    public ObservableList<Meal> getMeals() {
        return allMeals.get();
    }

    public void setMeals(ObservableList<Meal> meals) {
        allMeals.set(meals);
    }

    public DoubleProperty budgetProperty() {
        return budget;
    }


}
