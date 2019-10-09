package seedu.mark.logic.commands.commandresult;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class TabCommandResult extends CommandResult {

    private final boolean switchViewDashboard;
    private final boolean switchViewOnline;
    private final boolean switchViewOffline;

    public TabCommandResult(String feedbackToUser, boolean dashboard, boolean online, boolean offline) {
        super(feedbackToUser);
        this.switchViewDashboard = dashboard;
        this.switchViewOnline = online;
        this.switchViewOffline = offline;
    }

    @Override
    public boolean isSwitchViewDashboard() {
        return switchViewDashboard;
    }

    @Override
    public boolean isSwitchViewOnline() {
        return switchViewOnline;
    }

    @Override
    public boolean isSwitchViewOffline() {
        return switchViewOffline;
    }

}
