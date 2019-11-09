package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.display.schedulewindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
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

    private static final String MESSAGE_SUCCESS = "Selected timeslot";
    private static final String MESSAGE_FAILURE = "Unable to select: %s";

    private static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    private static final String MESSAGE_PERSON_NOT_SPECIFIED = "Please specify a person";
    private static final String MESSAGE_TIMESLOT_NOT_FOUND = "Invalid Timeslot ID";


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

        ScheduleWindowDisplayType status = model.getState();
        if (status == ScheduleWindowDisplayType.GROUP) {
            try {
                if (name.equals(Name.emptyName())) {
                    return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_SPECIFIED));
                }

                PersonTimeslot personTimeslot = model.getScheduleWindowDisplay()
                        .getPersonTimeslot(name, week, id);

                CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
                commandResult.setIsSelect(true);
                commandResult.setPersonTimeslotData(personTimeslot);

                return commandResult;

            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND));
            }
        } else if (status == ScheduleWindowDisplayType.PERSON) {
            try {
                Name name = model.getScheduleWindowDisplay().getPersonDisplays().get(0).getName();
                PersonTimeslot personTimeslot = model.getScheduleWindowDisplay()
                        .getPersonTimeslot(name, week, id);

                CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
                commandResult.setIsSelect(true);
                commandResult.setPersonTimeslotData(personTimeslot);

                return commandResult;

            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND));
            }
        } else if (status == ScheduleWindowDisplayType.HOME) {
            try {
                Name name = model.getUser().getName();
                PersonTimeslot personTimeslot = model.getScheduleWindowDisplay()
                        .getPersonTimeslotForToday(name, id);

                CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);

                commandResult.setIsSelect(true);
                commandResult.setPersonTimeslotData(personTimeslot);
                return commandResult;
            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
            } catch (PersonTimeslotNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_TIMESLOT_NOT_FOUND));
            }
        }


        return null;
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
