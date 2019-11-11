package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.timeslots.PersonTimeslot;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final boolean showHelp;
    private final boolean exit;
    private final boolean scroll;
    private final boolean home;
    private final boolean toggleNextWeek;
    private final boolean switchTabs;
    private final boolean export;

    private final boolean select;
    private final boolean popUp;
    private final boolean filter;

    private final Optional<ClosestCommonLocationData> locationData;
    private final Optional<PersonTimeslot> personTimeslotData;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
        this.showHelp = false;
        this.exit = false;
        this.scroll = false;
        this.home = false;
        this.toggleNextWeek = false;
        this.switchTabs = false;
        this.export = false;
        this.select = false;
        this.popUp = false;
        this.filter = false;
        this.locationData = Optional.empty();
        this.personTimeslotData = Optional.empty();
    }

    public CommandResult(String feedbackToUser,
                         boolean showHelp,
                         boolean exit,
                         boolean scroll,
                         boolean home,
                         boolean toggleNextWeek,
                         boolean switchTabs,
                         boolean export,
                         boolean select,
                         boolean popUp,
                         boolean filter,
                         Optional<ClosestCommonLocationData> locationData,
                         Optional<PersonTimeslot> personTimeslotData) {

        this.feedbackToUser = feedbackToUser;
        this.showHelp = showHelp;
        this.exit = exit;
        this.scroll = scroll;
        this.home = home;
        this.toggleNextWeek = toggleNextWeek;
        this.switchTabs = switchTabs;
        this.export = export;
        this.select = select;
        this.popUp = popUp;
        this.filter = filter;
        this.locationData = locationData;
        this.personTimeslotData = personTimeslotData;
    }



    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isExport() {
        return export;
    }

    public boolean isScroll() {
        return scroll;
    }

    public boolean isPopUp() {
        return popUp;
    }

    public boolean isToggleNextWeek() {
        return toggleNextWeek;
    }

    public boolean isHome() {
        return home;
    }

    public boolean isSwitchTabs() {
        return switchTabs;
    }

    public boolean isSelect() {
        return this.select;
    }

    public boolean isFilter() {
        return this.filter;
    }

    public Optional<PersonTimeslot> getPersonTimeslotData() {
        return this.personTimeslotData;
    }

    public ClosestCommonLocationData getLocationData() throws CommandException {
        if (locationData.isEmpty()) {
            throw new CommandException("Location not found!");
        }
        return locationData.get();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && scroll == otherCommandResult.scroll
                && home == otherCommandResult.home
                && toggleNextWeek == otherCommandResult.toggleNextWeek
                && switchTabs == otherCommandResult.switchTabs
                && export == otherCommandResult.export
                && select == otherCommandResult.select
                && popUp == otherCommandResult.popUp
                && filter == otherCommandResult.filter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, export);
    }

}
