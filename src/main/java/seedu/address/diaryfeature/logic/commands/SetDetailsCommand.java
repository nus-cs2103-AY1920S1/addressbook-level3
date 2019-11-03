package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.Details;
import seedu.address.diaryfeature.model.modelExceptions.UnknownUserException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a person to the address book.
 */
public class SetDetailsCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "setDetails";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to the diaryBook. ";


    public static final String MESSAGE_SUCCESS = "DETAILS SET";

    private final Details checker;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SetDetailsCommand(Details input) {
        requireNonNull(input);
        checker = input;
    }

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);

       try {
           model.setDetails(checker);
           return new CommandResult(MESSAGE_SUCCESS + "\n");
       } catch (UnknownUserException error) {
           ErrorCommand myCommand = new ErrorCommand(error);
           return myCommand.execute(model);
       }
    }


}
