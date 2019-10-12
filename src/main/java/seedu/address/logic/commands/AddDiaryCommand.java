package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;

/**
 * Adds a diary to Duke Cooks.
 */
public class AddDiaryCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a diary to Duke Cooks. "
            + "Parameters: "
            + PREFIX_NAME + "NAME ";

    public static final String MESSAGE_SUCCESS = "New diary added: %1$s";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in Duke Cooks";

    private final Diary toAdd;

    /**
     * Creates an AddDiaryCommand to add the specified {@code Diary}
     */
    public AddDiaryCommand(Diary diary) {
        requireNonNull(diary);
        toAdd = diary;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDiary(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DIARY);
        }

        model.addDiary(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDiaryCommand // instanceof handles nulls
                && toAdd.equals(((AddDiaryCommand) other).toAdd));
    }
}
