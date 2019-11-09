package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.timeslots.PersonTimeslot;

/**
 * Builder class for CommandResult.
 */
public class CommandResultBuilder {

    private String feedbackToUser;

    private boolean showHelp;
    private boolean exit;
    private boolean scroll;
    private boolean home;
    private boolean toggleNextWeek;
    private boolean switchTabs;
    private boolean export;

    private boolean select;
    private boolean popUp;
    private boolean filter;

    private Optional<ClosestCommonLocationData> locationData = Optional.empty();
    private Optional<PersonTimeslot> personTimeslotData = Optional.empty();

    public CommandResultBuilder(String feedbackToUser) {
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
    }

    public CommandResultBuilder setShowHelp() {
        this.showHelp = true;
        return this;
    }

    public CommandResultBuilder setExit() {
        this.exit = true;
        return this;
    }

    public CommandResultBuilder setScroll() {
        this.scroll = true;
        return this;
    }

    public CommandResultBuilder setHome() {
        this.home = true;
        return this;
    }

    public CommandResultBuilder setToggleNextWeek() {
        this.toggleNextWeek = true;
        return this;
    }

    public CommandResultBuilder setSwitchTabs() {
        this.switchTabs = true;
        return this;
    }

    public CommandResultBuilder setExport() {
        this.export = true;
        return this;
    }

    public CommandResultBuilder setSelect() {
        this.select = true;
        return this;
    }

    public CommandResultBuilder setPopUp() {
        this.popUp = true;
        return this;
    }

    public CommandResultBuilder setFilter() {
        this.filter = true;
        return this;
    }

    public CommandResultBuilder setLocationData(ClosestCommonLocationData locationData) {
        this.locationData = Optional.of(locationData);
        return this;
    }

    public CommandResultBuilder setPersonTimeslotData(PersonTimeslot personTimeslotData) {
        this.personTimeslotData = Optional.of(personTimeslotData);
        return this;
    }

    /**
     * Builds a CommandResult.
     */
    public CommandResult build() {
        return new CommandResult(
                feedbackToUser,
                showHelp,
                exit,
                scroll,
                home,
                toggleNextWeek,
                switchTabs,
                export,
                select,
                popUp,
                filter,
                locationData,
                personTimeslotData
        );
    }
}
