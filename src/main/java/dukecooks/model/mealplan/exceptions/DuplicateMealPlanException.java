package dukecooks.model.mealplan.exceptions;

/**
 * Signals that the operation will result in duplicate Meal Plans (Meal Plans are considered duplicates
 * if they have the same identity).
 */
public class DuplicateMealPlanException extends RuntimeException {
    public DuplicateMealPlanException() {
        super("Operation would result in duplicate meal plans");
    }
}
