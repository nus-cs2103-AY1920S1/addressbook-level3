package seedu.flashcard.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.flashcard.commons.core.index.Index;
import seedu.flashcard.commons.util.StringUtil;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.tag.Tag;

/**
 * Assistance class stores common features for all parsers.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DURATION = "Duration is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBaseIndex) throws ParseException {
        String trimmedIndex = oneBaseIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code duration} into an int duration and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Integer parseDuration(String duration) throws ParseException {
        String trimmedDuration = duration.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedDuration)) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }
        return Integer.parseInt(trimmedDuration);
    }

    /**
     * Parses a {@code String word} into a {@code Question}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code word} is invalid.
     */
    public static Question parseWord(String question) throws ParseException {
        requireNonNull(question);
        String trimmedQuestion = question.trim();
        if (!Question.isValidQuestion(trimmedQuestion)) {
            throw new ParseException(Question.MESSAGE_CONSTRAINTS);
        }
        return new Question(trimmedQuestion);
    }

    /**
     * Parses a {@code String choice} into a {@code Choice}.
     * Leading and trailing whitespaces will be trimmed.
     * @param choice contains the string representing a choice
     * @throws ParseException if the given {@code Choice} is invalid.
     */
    public static Choice parseChoice(String choice) throws ParseException {
        requireNonNull(choice);
        String trimmedChoice = choice.trim();
        if (!Choice.isValidChoice(trimmedChoice)) {
            throw new ParseException(Choice.MESSAGE_CONSTRAINTS);
        }
        return new Choice(trimmedChoice);
    }

    /**
     * Parses {@code Collection<String> Choices} into a {@code List<Choices>}.
     */
    public static List<Choice> parseChoices(Collection<String> choices) throws ParseException {
        requireNonNull(choices);
        final List<Choice> choiceList = new ArrayList<>();
        for (String choice : choices) {
            choiceList.add(parseChoice(choice));
        }
        return choiceList;
    }

    /**
     * Parses a {@code String definition} into a {@code Definition}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code definition} is invalid.
     */
    public static Definition parseDefinition(String definition) throws ParseException {
        requireNonNull(definition);
        String trimmedDefinition = definition.trim();
        if (!Definition.isValidDefinition(trimmedDefinition)) {
            throw new ParseException(Definition.MESSAGE_CONSTRAINTS);
        }
        return new Definition(trimmedDefinition);
    }

    /**
     * Parses a {@code String definition} into a {@code Definition}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code definition} is invalid.
     */
    public static Answer parseAnswer(String answer) throws ParseException {
        String trimmedAnswer = answer.trim();
        if (!Answer.isValidAnswer(trimmedAnswer)) {
            throw new ParseException(Answer.MESSAGE_CONSTRAINTS);
        }
        return new Answer(trimmedAnswer);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
