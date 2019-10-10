package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CORRECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRONG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.answerable.McqAnswer;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
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
                        PREFIX_DIFFICULTY, PREFIX_CATEGORY, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditAnswerableDescriptor editAnswerableDescriptor = new EditCommand.EditAnswerableDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editAnswerableDescriptor.setQuestion(ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get()));
        }
        //TODO: Implement Answerable
        if (argMultimap.getValue(PREFIX_CORRECT).isPresent()) {
//            editAnswerableDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_CORRECT).get()));
            editAnswerableDescriptor.setAnswer(new McqAnswer("Correct Answer"));
        }
        if (argMultimap.getValue(PREFIX_WRONG).isPresent()) {
//            editAnswerableDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_CORRECT).get()));
            editAnswerableDescriptor.setAnswer(new McqAnswer("Wrong Answer"));
        }
        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editAnswerableDescriptor.setDifficulty(ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editAnswerableDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAnswerableDescriptor::setTags);

        if (!editAnswerableDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editAnswerableDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
