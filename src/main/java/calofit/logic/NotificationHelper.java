package calofit.logic;

import java.util.Optional;

import calofit.model.Model;
import calofit.model.util.Notification;

/**
 * Notification Helper.
 */
public class NotificationHelper {

    public static final String COMMAND_WORD = "notification";

    public static final String MESSAGE_BREAKFAST = "Looks like you have missed your breakfast,"
        + " please take some time out to eat your breakfast!";

    public static final String MESSAGE_LUNCH = "Looks like you have missed your lunch,"
        + " please take some time out to eat your lunch!";

    public static final String MESSAGE_DINNER = "Looks like you have missed your dinner,"
        + " please take some time out to eat your dinner!";

    /**
     * Check the meals for period of the day
     * @param model Model
     * @return Optional String
     */
    public static Optional<String> execute(Model model) {
        Notification notification = new Notification();

        if (model.getMealLog().getTodayMeals().isEmpty()) {
            if (!notification.eatenBreakfast()) {
                return Optional.of(MESSAGE_BREAKFAST);
            }
        } else {
            int lastIndex = model.getMealLog().getTodayMeals().size() - 1;
            if (!notification.eatenLunch(model.getMealLog().getTodayMeals().get(
                lastIndex).getTimestamp())) {
                return Optional.of(MESSAGE_LUNCH);
            } else {
                if (!notification.eatenDinner(model.getMealLog().getTodayMeals().get(
                    lastIndex).getTimestamp())) {
                    return Optional.of(MESSAGE_DINNER);
                }
            }
        }
        return Optional.empty();
    }
}
