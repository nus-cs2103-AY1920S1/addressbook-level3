package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.display.scheduledisplay.GroupScheduleDisplay;
import seedu.address.model.display.scheduledisplay.HomeScheduleDisplay;
import seedu.address.model.display.scheduledisplay.PersonScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Command to show a popup of the details of a Timeslot.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_WEEK + "WEEK_NUMBER] "
            + PREFIX_ID + "ID" + "\n"
            + "WEEK_NUMBER: 1 - 4   (if not specified, current week will be selected)";

    public static final String MESSAGE_SUCCESS = "Selected timeslot";
    public static final String MESSAGE_FAILURE = "Unable to select: %s";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_PERSON_NOT_SPECIFIED = "Please specify a person";
    public static final String MESSAGE_TIMESLOT_NOT_FOUND = "Invalid Timeslot ID";
    public static final String MESSAGE_INVALID_STATE = "Nothing to select";


    private Integer week;
    private Name name;
    private Integer id;

    public SelectCommand(Integer week, Name name, Integer id) {
        this.week = week;
        this.name = name;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleState status = model.getState();
        if (status == ScheduleState.GROUP) {
            try {
                if (name.equals(Name.emptyName())) {
                    return new CommandResultBuilder(
                            String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_SPECIFIED)).build();
                }
                GroupScheduleDisplay groupScheduleDisplay = (GroupScheduleDisplay) model.getScheduleDisplay();
                PersonTimeslot personTimeslot = groupScheduleDisplay.getPersonTimeslot(name, week, id);

                return new CommandResultBuilder(MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND)).build();
            }
        } else if (status == ScheduleState.PERSON) {
            try {
                PersonScheduleDisplay personScheduleDisplay = (PersonScheduleDisplay) model.getScheduleDisplay();
                Name name = personScheduleDisplay.getPersonDisplays().get(0).getName();
                PersonTimeslot personTimeslot = personScheduleDisplay.getPersonTimeslot(name, week, id);

                return new CommandResultBuilder(MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND)).build();
            }
        } else if (status == ScheduleState.HOME) {
            try {
                HomeScheduleDisplay homeScheduleDisplay = (HomeScheduleDisplay) model.getScheduleDisplay();
                Name name = model.getUser().getName();
                PersonTimeslot personTimeslot = homeScheduleDisplay.getPersonTimeslotForToday(name, id);

                return new CommandResultBuilder(MESSAGE_SUCCESS)
                        .setSelect().setPersonTimeslotData(personTimeslot).build();

            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResultBuilder(
                        String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND)).build();
            }
        }
        return new CommandResultBuilder(MESSAGE_INVALID_STATE).build();
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof SelectCommand)) {
            return false;
        } else if (((SelectCommand) command).week.equals(this.week)
                && ((SelectCommand) command).name.equals(this.name)
                && ((SelectCommand) command).id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }
}
