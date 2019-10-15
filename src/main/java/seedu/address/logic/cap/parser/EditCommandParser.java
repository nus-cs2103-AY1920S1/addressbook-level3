package seedu.address.logic.cap.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_CREDIT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.cap.commands.EditCommand;
import seedu.address.logic.cap.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.cap.parser.exceptions.ParseException;
import seedu.address.model.cap.tag.Tag;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_CREDIT, PREFIX_FACULTY, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editPersonDescriptor.setModuleCode(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        }
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editPersonDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_CREDIT).isPresent()) {
            editPersonDescriptor.setCredit(ParserUtil.parseCredit(Integer.parseInt(argMultimap.getValue(PREFIX_CREDIT).get())));
        }
        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()) {
            editPersonDescriptor.setFaculty(ParserUtil.parseFaculty(argMultimap.getValue(PREFIX_FACULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPersonDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
}
