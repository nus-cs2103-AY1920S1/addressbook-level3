package seedu.flashcard.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.logic.commands.EditCommand;
import seedu.flashcard.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.tag.Tag;

/**
 * Parse input argument to generate a {@Code EditCommand}
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parse the given context in the edit command
     * @param args The input string from the user
     * @return new edit command
     */
    @Override
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_CHOICE,
                    PREFIX_DEFINITION, PREFIX_TAG, PREFIX_ANSWER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + EditCommand.MESSAGE_USAGE), pe);
        }

        EditFlashcardDescriptor editFlashcardDescriptor = new EditFlashcardDescriptor();
        if (argMultimap.getValue(PREFIX_QUESTION).isPresent()) {
            editFlashcardDescriptor.setQuestion(ParserUtil.parseWord(argMultimap.getValue(PREFIX_QUESTION).get()));
        }

        if (argMultimap.getValue(PREFIX_DEFINITION).isPresent()) {
            editFlashcardDescriptor.setDefinition(ParserUtil
                    .parseDefinition(argMultimap.getValue(PREFIX_DEFINITION).get()));
        }

        if (argMultimap.getValue(PREFIX_ANSWER).isPresent()) {
            editFlashcardDescriptor.setAnswer(ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editFlashcardDescriptor::setTags);

        parseChoicesForEdit(argMultimap.getAllValues(PREFIX_CHOICE)).ifPresent(editFlashcardDescriptor::setChoices);

        if (!editFlashcardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFlashcardDescriptor);
    }

    /**
     * Parses {@code Collection<String> choices} into a {@code Set<Choice>} if {@code choices} is non-empty.
     * If {@code choices} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero choices.
     */
    private Optional<List<Choice>> parseChoicesForEdit(Collection<String> choices) throws ParseException {
        assert choices != null;

        if (choices.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> choiceList = choices.size() == 1 && choices.contains("") ? Collections.emptyList() : choices;
        return Optional.of(ParserUtil.parseChoices(choiceList));
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
