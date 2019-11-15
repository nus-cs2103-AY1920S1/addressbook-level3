package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.parser.CliSyntax;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;

/**
 * Adds a diary to Duke Cooks.
 */
public class AddDiaryCommand extends AddCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a diary to Duke Cooks. "
            + "Parameters: "
            + CliSyntax.PREFIX_DIARY_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " n/ Asian Food";


    public static final String MESSAGE_SUCCESS = "You have added a new diary with name: %1$s";
    public static final String MESSAGE_DUPLICATE_DIARY = "This diary already exists in Duke Cooks!";

    private static Event event;
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

        // Navigate to diary tab
        event = Event.getInstance();
        event.set("diary", "all");

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDiaryCommand // instanceof handles nulls
                && toAdd.equals(((AddDiaryCommand) other).toAdd));
    }
}
