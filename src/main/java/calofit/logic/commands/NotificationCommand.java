package calofit.logic.commands;

import calofit.model.Model;
import calofit.model.util.Notification;

public class NotificationCommand extends Command{

    public static final String COMMAND_WORD = "notification";

    public static final String MESSAGE_BREAKFAST = "Looks like you have missed your breakfast," +
        " please take some time out to eat your breakfast!";

    public static final String MESSAGE_LUNCH = "Looks like you have missed your lunch," +
        " please take some time out to eat your lunch!";

    public static final String MESSAGE_DINNER = "Looks like you have missed your dinner," +
        " please take some time out to eat your dinner!";

    public static final String MESSAGE_SUCCESS = "You have taken all the needed meals. Good job!";


    @Override
    public CommandResult execute(Model model) {
        Notification notification = new Notification();

        switch (model.getMealLog().getTodayMeals().size()) {
        case 0:
            if (!notification.eatenBreakfast()) {
                return new CommandResult(MESSAGE_BREAKFAST);
            }
            break;
        default:
            if (!notification.eatenLunch(model.getMealLog().getTodayMeals().get(
                model.getMealLog().getTodayMeals().size() - 1).getTimestamp())) {
                return new CommandResult(MESSAGE_LUNCH);
            } else {
                if (!notification.eatenDinner(model.getMealLog().getTodayMeals().get(
                    model.getMealLog().getTodayMeals().size() - 1).getTimestamp())) {
                    return new CommandResult(MESSAGE_DINNER);
                }
            }
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
