package seedu.address.logic.parser.templateList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.templateList.EditTemplateListCommand;
import seedu.address.logic.commands.templateList.EditTemplateListCommand.EditTemplateListDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.tag.Tag;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Parses input arguments and creates a new EditTemplateListCommand object
 */
public class EditTemplateListCommandParser implements Parser<EditTemplateListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTemplateListCommand
     * and returns an EditTemplateListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTemplateListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTemplateListCommand.MESSAGE_USAGE), pe);
        }

        EditTemplateListDescriptor editTemplateListDescriptor = new EditTemplateListDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTemplateListDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (!editTemplateListDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTemplateListCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTemplateListCommand(index, editTemplateListDescriptor);
    }
}
