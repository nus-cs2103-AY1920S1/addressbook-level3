package calofit.logic.commands;

import calofit.model.Model;
import calofit.model.util.Notification;

public class NotificationCommand extends Command{

    public static final String COMMAND_WORD = "notification";

    public static final String MESSAGE_BREAKFAST = "Looks like you have missed your breakfast," +
        "Please take some time out to eat your breakfast!";

    public static final String MESSAGE_LUNCH = "Looks like you have missed your lunch," +
        "Please take some time out to eat your lunch!";

    public static final String MESSAGE_DINNER = "Looks like you have missed your dinner," +
        "Please take some time out to eat your dinner!";


    @Override
    public CommandResult execute(Model model) {

        Notification notification = new Notification();

        notification.checkBreakfast(model.getMealLog().getMeals().get(0).getTimestamp());

        return new CommandResult(MESSAGE_BREAKFAST);
    }
}
