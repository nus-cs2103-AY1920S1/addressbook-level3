package com.dukeacademy.logic.commands.tab;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Command used to switch tabs within the application
 */
public class TabCommand implements Command {
    public static final String FEEDBACK = "Toggling tab to : ";
    private final ApplicationState applicationState;

    public TabCommand(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Activity currentActivity = applicationState.getCurrentActivity();

        if (currentActivity == null) {
            this.applicationState.setCurrentActivity(Activity.DASHBOARD);
            String userFeedback = FEEDBACK + Activity.DASHBOARD.toString();
            return new CommandResult(userFeedback, false);
        }

        Activity[] activities = Activity.values();
        int numActivities = activities.length;

        OptionalInt currentActivityIndex = IntStream.range(0, numActivities)
                .filter(i -> activities[i] == currentActivity).findFirst();

        if (currentActivityIndex.isEmpty()) {
            this.applicationState.setCurrentActivity(Activity.DASHBOARD);
            String userFeedback = FEEDBACK + Activity.DASHBOARD.toString();
            return new CommandResult(userFeedback, false);
        }

        int nextActivityIndex = (currentActivityIndex.getAsInt() + 1) % numActivities;
        Activity nextActivity = activities[nextActivityIndex];

        this.applicationState.setCurrentActivity(nextActivity);
        String userFeedback = FEEDBACK + nextActivity.toString();
        return new CommandResult(userFeedback, false);
    }
}
