package calofit.logic;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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

    private final Clock clock;

    public NotificationHelper(Clock clock) {
        this.clock = clock;
    }

    /**
     * Check the meals for period of the day
     * @param model Model
     * @return Optional String
     */
    public Optional<String> execute(Model model) {
        LocalDateTime now = LocalDateTime.from(ZonedDateTime.ofInstant(clock.instant(),
            clock.getZone()));
        Notification notification = new Notification(now);

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
