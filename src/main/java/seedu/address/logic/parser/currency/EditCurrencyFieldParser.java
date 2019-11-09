package seedu.address.logic.parser.currency;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMBOL;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses the arguments to return a {@code EditCurrencyFieldCommand}.
 */
public class EditCurrencyFieldParser implements Parser<EditCurrencyFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCurrencyFieldCommand
     * and returns an EditCurrencyFieldCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCurrencyFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_SYMBOL,
                        PREFIX_RATE);

        Optional<Index> index;

        try {
            index = Optional.ofNullable(ParserUtil.parseIndex(argMultimap.getPreamble()));
        } catch (ParseException pe) {
            index = Optional.empty();
        }

        if (!index.isEmpty()) {
            //edit by field specified by index only
            throw new UnsupportedOperationException("Parsing edit currency by index not yet supported.");

        }
        //edit by prefixes
        EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor =
                new EditCurrencyFieldCommand.EditCurrencyDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCurrencyDescriptor.setName(CurrencyParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_SYMBOL).isPresent()) {
            editCurrencyDescriptor.setSymbol(CurrencyParserUtil.parseSymbol(argMultimap.getValue(PREFIX_SYMBOL).get()));
        }
        if (argMultimap.getValue(PREFIX_RATE).isPresent()) {
            editCurrencyDescriptor.setRate(
                    CurrencyParserUtil.parseRate(argMultimap.getValue(PREFIX_RATE).get()));
        }

        if (!editCurrencyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCurrencyFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCurrencyFieldCommand(editCurrencyDescriptor);
    }
}
