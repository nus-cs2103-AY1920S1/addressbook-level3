package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.statistics.Type.OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Gets statistics of how well the user has attempted the questions.
 */
public class GetOverviewqCommand extends Command {
    public static final String COMMAND_WORD = "overviewq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets an overview of the types of "
            + "questions that have been added into the app.\n"
            + "An optional time period can be included.\n"
            + "A stacked bar chart, sorted by subjects, will be returned.\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "START_DATE] "
            + "[" + PREFIX_DATE + "END_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "dt/08/09/2019 "
            + "dt/10/09/2019";

    public static final String MESSAGE_SUCCESS = "Here is an overview of the questions: ";
    public static final String MESSAGE_NO_STATISTICS = "There are no questions done, try doing some questions.";

    private List<String> dates;

    public GetOverviewqCommand(List<String> dates) {
        requireNonNull(dates);
        this.dates = dates;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //model.updateFilteredNoteList(PREDICATE_SHOW_NO_NOTES);
        //model.setStatistics(); overload set statistics method? or use list of strings for all commands
        CommandResult c = new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
        c.setType(OVERVIEW);
        return c;
    }
}
