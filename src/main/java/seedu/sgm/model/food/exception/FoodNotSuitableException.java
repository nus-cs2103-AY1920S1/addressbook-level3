package seedu.sgm.model.food.exception;

/**
 * Signals that the adding food is not suitable for diabetes.
 */
public class FoodNotSuitableException extends RuntimeException {
    public FoodNotSuitableException(String msg) {
        super(msg);
    }
}
