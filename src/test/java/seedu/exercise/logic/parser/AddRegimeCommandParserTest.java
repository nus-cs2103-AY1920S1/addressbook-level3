package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_INDEXES;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_NAME_CARDIO;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.AddRegimeCommand;
import seedu.exercise.model.property.Name;

public class AddRegimeCommandParserTest {

    private static final String REGIME = "regime";
    private static final String VALID_REGIME_NAME_DESC = " " + PREFIX_NAME + VALID_REGIME_NAME_CARDIO;
    private static final String VALID_INDEX_DESC = " " + PREFIX_INDEX + "1";
    private static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + "a";
    private static final String REGIME_DESC = " " + PREFIX_CATEGORY + REGIME;
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, REGIME_DESC + VALID_REGIME_NAME_DESC + VALID_INDEX_DESC,
                new AddRegimeCommand(VALID_REGIME_INDEXES, new Name(VALID_REGIME_NAME_CARDIO)));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        assertParseSuccess(parser, REGIME_DESC + VALID_REGIME_NAME_DESC,
                new AddRegimeCommand(new ArrayList<>(), new Name(VALID_REGIME_NAME_CARDIO)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRegimeCommand.MESSAGE_USAGE);

        assertParseFailure(parser, REGIME_DESC + VALID_INDEX_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, REGIME_DESC + VALID_REGIME_NAME_DESC + INVALID_INDEX_DESC,
                Index.MESSAGE_CONSTRAINTS);
    }
}
