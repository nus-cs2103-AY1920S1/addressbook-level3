package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDiaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Name;

/**
 * Parses input arguments and creates a new EditDiaryCommand object
 */
public class EditCommandParser implements Parser<EditDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDiaryCommand
     * and returns an EditDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDiaryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] argsArr = args.trim().split(" ", 2);

        Index index;

        try {
            index = ParserUtil.parseIndex(argsArr[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDiaryCommand.MESSAGE_USAGE), pe);
        }

        EditDiaryCommand.EditDiaryDescriptor editDiaryDescriptor = new EditDiaryCommand.EditDiaryDescriptor();
        editDiaryDescriptor.setName(new Name(argsArr[1]));

        if (!editDiaryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditDiaryCommand.MESSAGE_NOT_EDITED);
        }

        return new EditDiaryCommand(index, editDiaryDescriptor);
    }
}
