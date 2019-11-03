package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.InvalidTimeslotException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Command to show a popup of the details of a Timeslot.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_WEEK + "WEEK_NUMBER "
            + PREFIX_DAY + "DAY_NUMBER "
            + PREFIX_ID + "ID";

    private static final String MESSAGE_SUCCESS = "Selected timeslot";
    private static final String MESSAGE_FAILURE = "Unable to select: %s";

    private static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    private static final String MESSAGE_INVALID_TIMESLOT = "Invalid Timeslot ID";


    private Integer week;
    private Name name;
    private Integer day;
    private Integer id;

    public SelectCommand(Integer week, Name name, Integer day, Integer id) {
        this.week = week;
        this.name = name;
        this.day = day;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleWindowDisplayType status = model.getState();
        if (status == ScheduleWindowDisplayType.GROUP) {
            try {
                PersonTimeslot personTimeslot = model.getScheduleWindowDisplay()
                        .getPersonTimeslot(week, name, day, id);

                CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
                commandResult.setIsSelect(true);
                commandResult.setPersonTimeslotData(personTimeslot);

                System.out.println(personTimeslot.toString());

                return commandResult;

            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
            } catch (InvalidTimeslotException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_INVALID_TIMESLOT));
            }
        } else if (status == ScheduleWindowDisplayType.PERSON) {
            try {
                Name name = model.getScheduleWindowDisplay().getPersonDisplays().get(0).getName();
                PersonTimeslot personTimeslot = model.getScheduleWindowDisplay()
                        .getPersonTimeslot(week, name, day, id);

                CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);
                commandResult.setIsSelect(true);
                commandResult.setPersonTimeslotData(personTimeslot);

                return commandResult;

            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
            } catch (InvalidTimeslotException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_INVALID_TIMESLOT));
            }
        } else if (status == ScheduleWindowDisplayType.HOME) {
            PersonTimeslot personTimeslot = model.getScheduleWindowDisplay().getPersonSchedules()
                    .get(week).get(0).getScheduleDisplay().get(LocalDateTime.now().getDayOfWeek()).get(id);

            CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS, false, false);

            commandResult.setIsSelect(true);
            commandResult.setPersonTimeslotData(personTimeslot);
            return commandResult;
        }


        return null;
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
