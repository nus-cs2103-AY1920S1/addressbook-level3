package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEQUENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.SortType;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to guiltTrip(). \n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_SEQUENCE + "SEQUENCE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "Time "
            + PREFIX_SEQUENCE + "Ascending ";

    public static final String MESSAGE_SUCCESS = "Sorted all entries by %s";

    private final SortType type;
    private final String sequence;

    public SortCommand(SortType type, String sequence) {
        this.type = type;
        this.sequence = sequence;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredEntry(type);
        return new CommandResult(String.format(MESSAGE_SUCCESS, type));
    }

}
