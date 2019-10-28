package seedu.exercise.logic.parser;

import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_CONFLICT_INDEX_NOT_ENGLISH;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_CONFLICT_INDEX_ZERO;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_INDEX_ALPHABETS;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_NAME_NOT_ENGLISH;
import static seedu.exercise.testutil.CommonTestData.INVALID_PREFIX_NAME_SYMBOLS;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.VALID_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_CARDIO;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_CONFLICT_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_INDEX_2;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_CARDIO;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_LEGS;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.model.property.Name;
import seedu.exercise.testutil.typicalutil.TypicalIndexes;

public class ResolveCommandParserTest {

    private ResolveCommandParser parser = new ResolveCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        List<Index> scheduledIndex = new ArrayList<>();
        List<Index> conflictIndex = new ArrayList<>();
        scheduledIndex.add(TypicalIndexes.INDEX_ONE_BASED_FIRST);
        scheduledIndex.add(TypicalIndexes.INDEX_ONE_BASED_SECOND);
        conflictIndex.add(TypicalIndexes.INDEX_ONE_BASED_FIRST);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_PREFIX_NAME_CARDIO,
                new ResolveCommand(new Name(VALID_NAME_CARDIO), new ArrayList<>(), new ArrayList<>()));

        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_PREFIX_NAME_LEGS + VALID_PREFIX_NAME_CARDIO ,
                new ResolveCommand(new Name(VALID_NAME_CARDIO), new ArrayList<>(), new ArrayList<>()));

        // multiple indexes - all accepted
        assertParseSuccess(parser, VALID_PREFIX_NAME_CARDIO
                        + VALID_PREFIX_INDEX + VALID_PREFIX_INDEX_2 + VALID_PREFIX_CONFLICT_INDEX,
                new ResolveCommand(new Name(VALID_NAME_CARDIO), scheduledIndex, conflictIndex));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ResolveCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_CARDIO + VALID_PREFIX_INDEX, expectedMessage);

        // all prefix missing
        assertParseFailure(parser, VALID_NAME_CARDIO + VALID_INDEX, expectedMessage);

    }

    @Test
    public void parse_invalidValues_failure() {
        String expectedInvalidNameMessage = Name.MESSAGE_CONSTRAINTS;
        String expectedInvalidIndexMessage = Index.MESSAGE_CONSTRAINTS;

        //Invalid name types
        assertParseFailure(parser, INVALID_PREFIX_NAME_NOT_ENGLISH, expectedInvalidNameMessage);
        assertParseFailure(parser, INVALID_PREFIX_NAME_SYMBOLS, expectedInvalidNameMessage);

        //Invalid index types
        assertParseFailure(parser, VALID_PREFIX_NAME_CARDIO + INVALID_PREFIX_INDEX_ALPHABETS,
                expectedInvalidIndexMessage);
        assertParseFailure(parser, VALID_PREFIX_NAME_CARDIO + INVALID_PREFIX_CONFLICT_INDEX_ZERO,
                expectedInvalidIndexMessage);
        assertParseFailure(parser, VALID_PREFIX_NAME_CARDIO + INVALID_PREFIX_CONFLICT_INDEX_NOT_ENGLISH,
                expectedInvalidIndexMessage);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_PREFIX_INDEX + VALID_PREFIX_NAME_CARDIO,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ResolveCommand.MESSAGE_USAGE));
    }
}
