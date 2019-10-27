package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.COVERAGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.COVERAGE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.END_AGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.END_AGE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NAME_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_NAME_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.START_AGE_DESC_FIRE;
import static seedu.address.logic.commands.CommandTestUtil.START_AGE_DESC_HEALTH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPolicy.HEALTH_INSURANCE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.DoNotMergePolicyCommand;
import seedu.address.model.policy.Policy;

public class DoNotMergePolicyCommandParserTest {
    private DoNotMergePolicyCommandParser parser = new DoNotMergePolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Policy expectedPolicy = new PolicyBuilder(HEALTH_INSURANCE).withCriteria().withTags().build();

        // whitespace only preamble
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HEALTH
            + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH
            + END_AGE_DESC_HEALTH, new DoNotMergePolicyCommand(expectedPolicy));

        // multiple names - last name accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_FIRE + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH
            + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_FIRE + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH
            + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));

        // multiple coverages - last coverage accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_FIRE + COVERAGE_DESC_HEALTH
            + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));

        // multiple prices - last price accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_FIRE
            + PRICE_DESC_HEALTH + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));

        // multiple start ages - last start age accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH
            + START_AGE_DESC_FIRE + START_AGE_DESC_HEALTH + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));

        // multiple end ages - last end age accepted
        assertParseSuccess(parser, DoNotMergePolicyCommand.COMMAND_WORD + PREAMBLE_WHITESPACE + PREAMBLE_WHITESPACE
            + POLICY_NAME_DESC_HEALTH + DESCRIPTION_DESC_HEALTH + COVERAGE_DESC_HEALTH + PRICE_DESC_HEALTH
            + START_AGE_DESC_HEALTH + END_AGE_DESC_FIRE + END_AGE_DESC_HEALTH,
            new DoNotMergePolicyCommand(expectedPolicy));
    }

}
