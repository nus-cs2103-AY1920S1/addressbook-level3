package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import java.util.NoSuchElementException;

public class DeleteSchoolBreakCommand extends DeleteCommand {
    public static final String COMMAND_WORD = "school_break";
    public static final String MESSAGE_USAGE = DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the specified school break that happens on the specified date(s)"
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME " + "\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " " + COMMAND_WORD + " " + CliSyntax.PREFIX_START_DAY + "8 "
            + CliSyntax.PREFIX_START_MONTH + "Dec " + CliSyntax.PREFIX_START_YEAR + "2019 "
            + CliSyntax.PREFIX_END_DAY + "12 " + CliSyntax.PREFIX_END_MONTH + "January "
            + CliSyntax.PREFIX_END_YEAR + "2020 "
            + CliSyntax.PREFIX_NAME + "2019/20 Semester 1 school break";

    private SchoolBreak schoolBreak;

    public DeleteSchoolBreakCommand(SchoolBreak schoolBreak) {
        this.schoolBreak = schoolBreak;
    }

    @Override
    public CommandResult execute(Calendar calendar) throws CommandException, NoSuchElementException {
        calendar.deleteEvent(schoolBreak);
        String formattedFeedback = String.format(MESSAGE_DELETE_SUCCESS, schoolBreak.toString());
        return new CommandResult(formattedFeedback);
    }
}
