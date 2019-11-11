package seedu.revision.logic.parser.main;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.revision.logic.commands.main.EditCommand.MESSAGE_CANNOT_EDIT_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.parser.ArgumentMultimap;
import seedu.revision.logic.parser.ArgumentTokenizer;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.ParserUtil;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.category.Category;

/**
 * Parses input arguments and creates a new EditCommand object
 * @@author jxofficial
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION_TYPE, PREFIX_QUESTION, PREFIX_CORRECT, PREFIX_WRONG,
                        PREFIX_DIFFICULTY, PREFIX_CATEGORY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_QUESTION_TYPE).isPresent()) {
            throw new ParseException(MESSAGE_CANNOT_EDIT_TYPE);
        }

        EditCommand.EditAnswerableDescriptor editAnswerableDescriptor = new EditCommand.EditAnswerableDescriptor();

        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editAnswerableDescriptor.setQuestion(
                    ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get()));
        }

        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editAnswerableDescriptor.setDifficulty(
                    ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get()));
        }

        if (argMultimap.getValue(PREFIX_CORRECT).isPresent()) {
            editAnswerableDescriptor.setCorrectAnswerList(
                    ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_CORRECT)));
        }
        if (argMultimap.getValue(PREFIX_WRONG).isPresent()) {
            editAnswerableDescriptor.setWrongAnswerList(
                    ParserUtil.parseAnswers(argMultimap.getAllValues(PREFIX_WRONG)));
        }

        parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY))
                .ifPresent(editAnswerableDescriptor::setCategories);

        if (!editAnswerableDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editAnswerableDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Optional<Set<Category>> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;

        if (categories.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseCategories(categories));
    }

}
