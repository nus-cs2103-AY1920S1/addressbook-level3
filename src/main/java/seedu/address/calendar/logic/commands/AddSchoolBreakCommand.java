package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class AddSchoolBreakCommand extends AddCommand {
    public static final String COMMAND_WORD = "school_break";
    public static final String MESSAGE_USAGE = AddCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a school break to the specified date(s)"
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME "
            + "Example: " + AddCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "8 "
            + CliSyntax.PREFIX_START_MONTH + "Dec " + CliSyntax.PREFIX_START_YEAR + "2019 "
            + CliSyntax.PREFIX_END_DAY + "12 " + CliSyntax.PREFIX_END_MONTH + "January "
            + CliSyntax.PREFIX_END_YEAR + "2020 "
            + CliSyntax.PREFIX_NAME + "2019/20 Semester 1 school break";

    private SchoolBreak schoolBreak;

    public AddSchoolBreakCommand(SchoolBreak schoolBreak) {
        this.schoolBreak = schoolBreak;
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException {
        try {
            calendar.addEvent(schoolBreak);
        } catch (DuplicateEventException | ClashException e) {
            throw new CommandException(e.getMessage());
        }

        String formattedFeedback = String.format(MESSAGE_ADD_SUCCESS, schoolBreak.toString());
        return new CommandResult(formattedFeedback);
    }
}