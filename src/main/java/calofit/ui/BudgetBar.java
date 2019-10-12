package calofit.ui;

import java.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Skin;
import javafx.scene.control.skin.ProgressBarSkin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import calofit.model.meal.Meal;

/**
 * Progress bar component representing current calorie budget.
 */
public class BudgetBar extends ProgressBar {
    private ObjectProperty<ObservableList<Meal>> allMeals = new SimpleObjectProperty<>();
    private ObjectBinding<ObservableList<Meal>> filteredMeals = new ObjectBinding<ObservableList<Meal>>() {
        {
            bind(allMeals);
        }
        @Override
        protected ObservableList<Meal> computeValue() {
            if (allMeals.get() == null) {
                return FXCollections.emptyObservableList();
            }
            return allMeals.get().filtered(BudgetBar::isMealToday);
        }
    };
    private DoubleBinding totalConsumed = new DoubleBinding() {
        {
            bind(filteredMeals);
        }
        @Override
        protected double computeValue() {
            int total = 0;
            if (filteredMeals.get() != null) {
                for (Meal m : filteredMeals.get()) {
                    total += m.getDish().getCalories().getValue();
                }
            }
            return total;
        }
    };
    private DoubleProperty budget = new SimpleDoubleProperty(Double.POSITIVE_INFINITY);
    private DoubleExpression budgetPercent = new DoubleBinding() {
        {
            bind(totalConsumed, budget);
        }
        @Override
        protected double computeValue() {
            if (budget.getValue() < 0) {
                return ProgressBar.INDETERMINATE_PROGRESS;
            }
            return totalConsumed.get() / budget.get();
        }
    };

    private ObjectExpression<Color> barColor = new ObjectBinding<Color>() {
        {
            bind(budgetPercent);
        }
        @Override
        protected Color computeValue() {
            double percent = budgetPercent.get();
            percent = Math.max(0, Math.min(1, percent));
            return Color.GREEN.interpolate(Color.RED, percent);
        }
    };

    public BudgetBar() {
        this.progressProperty().bind(budgetPercent);

        this.skinProperty().addListener(new ChangeListener<Skin<?>>() {
            @Override
            public void changed(ObservableValue<? extends Skin<?>> observable, Skin<?> oldValue, Skin<?> newValue) {
                ProgressBarSkin skin = (ProgressBarSkin) newValue;
                for (Node c : skin.getChildren()) {
                    if (c.getStyleClass().contains("bar")) {
                        Region bar = (Region) c;
                        bar.backgroundProperty().bind(
                            Bindings.createObjectBinding(BudgetBar.this::makeBarBg, barColor));
                    }
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
