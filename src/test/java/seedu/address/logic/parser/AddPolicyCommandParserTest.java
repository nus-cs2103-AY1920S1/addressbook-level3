package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COVERAGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.COVERAGE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.CRITERIA_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.END_AGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.END_AGE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COVERAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_AGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_AGE;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NAME_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NAME_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.START_AGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.START_AGE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_DIABETIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COVERAGE_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_AGE_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_HEALTH_INSURANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_AGE_HEALTH_INSURANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPolicy.HEALTH_INSURANCE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.EndAge;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Price;
import seedu.address.model.policy.StartAge;

public class AddPolicyCommandParserTest {
    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Policy expectedPolicy = new PolicyBuilder(HEALTH_INSURANCE).withCriteria().withTags().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new AddPolicyCommand(expectedPolicy));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_FIRE + POLICY_NAME_DESC_HEALTH
            + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_FIRE
            + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));

        // multiple coverages - last coverage accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_FIRE + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));

        // multiple prices - last price accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_FIRE + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));

        // multiple start ages - last start age accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_FIRE + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));

        // multiple end ages - last end age accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_FIRE
            + END_AGE_DESC_HEALTH, new AddPolicyCommand(expectedPolicy));
    }

    @Test
    public void parse_criteriaFieldIncluded_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH
            + CRITERIA_DESC_HEALTH, expectedMessage);
    }

    @Test
    public void parse_tagFieldIncluded_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH
            + TAG_DESC_DIABETIC, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_HEALTH_INSURANCE + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + VALID_DESCRIPTION_HEALTH_INSURANCE
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH, expectedMessage);

        // missing coverage prefix
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + VALID_COVERAGE_HEALTH_INSURANCE + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, expectedMessage);

        // missing price prefix
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + VALID_PRICE_HEALTH_INSURANCE + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_HEALTH_INSURANCE + VALID_DESCRIPTION_HEALTH_INSURANCE
            + VALID_COVERAGE_HEALTH_INSURANCE + VALID_PRICE_HEALTH_INSURANCE
            + VALID_START_AGE_HEALTH_INSURANCE + VALID_END_AGE_HEALTH_INSURANCE,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_POLICY_NAME_DESC + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            PolicyName.MESSAGE_CONSTRAINTS);

        // invalid coverage
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + INVALID_COVERAGE_DESC + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            Coverage.MESSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + INVALID_PRICE_DESC + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            Price.MESSAGE_CONSTRAINTS);

        // invalid start age
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + INVALID_START_AGE + END_AGE_DESC_HEALTH,
            StartAge.MESSAGE_CONSTRAINTS);

        // invalid end age
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + INVALID_END_AGE,
            EndAge.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_POLICY_NAME_DESC + DESCRIPTION_DESC_HEALTH
            + INVALID_COVERAGE_DESC + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            PolicyName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH
            + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
    }
}
