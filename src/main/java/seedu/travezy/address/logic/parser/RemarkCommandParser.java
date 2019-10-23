package seedu.travezy.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.travezy.commons.core.index.Index;
import seedu.travezy.commons.exceptions.IllegalValueException;
import seedu.travezy.address.logic.commands.RemarkCommand;
import seedu.travezy.logic.parser.ArgumentMultimap;
import seedu.travezy.logic.parser.ArgumentTokenizer;
import seedu.travezy.logic.parser.Parser;
import seedu.travezy.logic.parser.ParserUtil;
import seedu.travezy.logic.parser.exceptions.ParseException;
import seedu.travezy.address.model.person.Remark;
import seedu.travezy.commons.core.Messages;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        }

        String remark = argMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
}
