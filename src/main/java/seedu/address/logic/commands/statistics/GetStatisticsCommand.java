package seedu.address.logic.commands.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_NOTES;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class GetStatisticsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets the statistics of how well "
            + "a question has been answered for a subject. "
            + "A pie chart will be returned.\n"
            + "Parameters: "
            + PREFIX_SUBJECT + "SUBJECT "
            + "[" + PREFIX_SUBJECT + "SUBJECT]... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SUBJECT + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Here are the statistics: ";
    public static final String MESSAGE_NO_STATISTICS = "There is no available data for computation of "
            + "statistics, try doing some questions.";

    private final List subjects;

    public GetStatisticsCommand(List subjects) {
        requireNonNull(subjects);
        this.subjects = subjects;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredNoteList(PREDICATE_SHOW_NO_NOTES);
        // need to think of how to extract data from storage
        model.setStatistics();
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
