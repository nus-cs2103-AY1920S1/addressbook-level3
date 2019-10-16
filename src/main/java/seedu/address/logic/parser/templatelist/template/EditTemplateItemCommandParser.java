package seedu.address.logic.parser.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.templatelist.template.EditTemplateItemCommand;
import seedu.address.logic.commands.templatelist.template.EditTemplateItemCommand.EditTemplateItemDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTemplateItemCommand object
 */
public class EditTemplateItemCommandParser implements Parser<EditTemplateItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTemplateItemCommand
     * and returns an EditTemplateItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTemplateItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTemplateItemCommand.MESSAGE_USAGE), pe);
        }

        EditTemplateItemDescriptor editTemplateItemDescriptor = new EditTemplateItemDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editTemplateItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editTemplateItemDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (!editTemplateItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTemplateItemCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTemplateItemCommand(index, editTemplateItemDescriptor);
    }

}
