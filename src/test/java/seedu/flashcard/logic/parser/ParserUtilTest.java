package seedu.flashcard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_ANSWER;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_CHOICE;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_DEFINITION;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_QUESTION;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_TAG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_APPLE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_CHOICE_BLUE;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_DEFINITION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_QUESTION_BANANA;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.flashcard.testutil.Assert.assertThrows;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.tag.Tag;




public class ParserUtilTest {

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_FLASHCARD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseQuestion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseWord((String) null));
    }

    @Test
    public void parseQuestion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseWord(INVALID_QUESTION));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Question expectedQuestion = new Question(VALID_QUESTION_BANANA);
        assertEquals(expectedQuestion, ParserUtil.parseWord(VALID_QUESTION_BANANA));
    }

    @Test
    public void parseQuestion_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String questionWithWhitespace = WHITESPACE + VALID_QUESTION_BANANA + WHITESPACE;
        Question expectedName = new Question(VALID_QUESTION_BANANA);
        assertEquals(expectedName, ParserUtil.parseWord(questionWithWhitespace));
    }

    @Test
    public void parseAnswer_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAnswer((String) null));
    }

    @Test
    public void parseAnswer_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAnswer(INVALID_ANSWER));
    }

    @Test
    public void parseAnswer_validValueWithoutWhitespace_returnsAnswer() throws Exception {
        Answer expectedAnswer = new Answer(VALID_ANSWER_BANANA);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(VALID_ANSWER_BANANA));
    }

    @Test
    public void parseAnswer_validValueWithWhitespace_returnsTrimmedAnswer() throws Exception {
        String answerWithWhitespace = WHITESPACE + VALID_ANSWER_APPLE + WHITESPACE;
        Answer expectedAnswer = new Answer(VALID_ANSWER_APPLE);
        assertEquals(expectedAnswer, ParserUtil.parseAnswer(answerWithWhitespace));
    }

    @Test
    public void parseDefinition_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDefinition((String) null));
    }

    @Test
    public void parseDefinition_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDefinition(INVALID_DEFINITION));
    }

    @Test
    public void parseDefinition_validValueWithoutWhitespace_returnsDefinition() throws Exception {
        Definition expectedDefinition = new Definition(VALID_DEFINITION_BANANA);
        assertEquals(expectedDefinition, ParserUtil.parseDefinition(VALID_DEFINITION_BANANA));
    }

    @Test
    public void parseDefinition_validValueWithWhitespace_returnsTrimmedDefinition() throws Exception {
        String definitionWithWhitespace = WHITESPACE + VALID_DEFINITION_BANANA + WHITESPACE;
        Definition expectedDefinition = new Definition(VALID_DEFINITION_BANANA);
        assertEquals(expectedDefinition, ParserUtil.parseDefinition(definitionWithWhitespace));
    }


    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_ROUND);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_ROUND));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_ROUND + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_ROUND);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_ROUND, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_ROUND, VALID_TAG_LONG));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_ROUND), new Tag(VALID_TAG_LONG)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseChoice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseChoice(null));
    }

    @Test
    public void parseChoice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseChoice(INVALID_CHOICE));
    }

    @Test
    public void parseChoice_validValueWithoutWhitespace_returnsChoice() throws Exception {
        Choice expectedChoice = new Choice(VALID_CHOICE_BLUE);
        assertEquals(expectedChoice, ParserUtil.parseChoice(VALID_CHOICE_BLUE));
    }

    @Test
    public void parseChoice_validValueWithWhitespace_returnsTrimmedChoice() throws Exception {
        String choiceWithWhitespace = WHITESPACE + VALID_CHOICE_BLUE + WHITESPACE;
        Choice expectedChoice = new Choice(VALID_CHOICE_BLUE);
        assertEquals(expectedChoice, ParserUtil.parseChoice(choiceWithWhitespace));
    }

    @Test
    public void parseChoices_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseChoices(null));
    }

    @Test
    public void parseChoices_collectionWithInvalidChoices_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseChoices(Arrays.asList(VALID_CHOICE_BLUE,
                INVALID_CHOICE)));
    }

    @Test
    public void parseChoices_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseChoices(Collections.emptyList()).isEmpty());
    }

}
