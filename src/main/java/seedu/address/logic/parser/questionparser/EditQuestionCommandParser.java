package seedu.address.logic.parser.questionparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand.EditQuestionDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditQuestionCommand object.
 */
public class EditQuestionCommandParser implements Parser<EditQuestionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditQuestionCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditQuestionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_SUBJECT, PREFIX_DIFFICULTY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditQuestionCommand.MESSAGE_USAGE),
                    pe);
        }

        EditQuestionDescriptor editQuestionDescriptor = new EditQuestionDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editQuestionDescriptor.setQuestionBody(ParserUtil.parseQuestionBody(argMultimap
                    .getValue(PREFIX_QUESTION).get()));
        }
        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editQuestionDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap
                    .getValue(PREFIX_ANSWER).get()));
        }
        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editQuestionDescriptor.setSubject(ParserUtil.parseSubject(argMultimap
                    .getValue(PREFIX_SUBJECT).get()));
        }
        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editQuestionDescriptor.setDifficulty(ParserUtil.parseDifficulty(argMultimap
                    .getValue(PREFIX_DIFFICULTY).get()));
        }

        if (!editQuestionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditQuestionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditQuestionCommand(index, editQuestionDescriptor);
    }
}
