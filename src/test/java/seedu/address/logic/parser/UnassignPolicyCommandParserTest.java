package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POLICY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_DESC_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.POLICY_DESC_LIFE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_LIFE_INSURANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.model.policy.PolicyName;

class UnassignPolicyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignPolicyCommand.MESSAGE_USAGE);

    private UnassignPolicyCommandParser parser = new UnassignPolicyCommandParser();

    @Test
    public void parse_policyNameSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + POLICY_DESC_LIFE;

        PolicyName policyName = new PolicyName(VALID_NAME_LIFE_INSURANCE);
        UnassignPolicyCommand expectedCommand = new UnassignPolicyCommand(targetIndex, policyName);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + POLICY_DESC_HEALTH + POLICY_DESC_LIFE;

        PolicyName policyName = new PolicyName(VALID_NAME_LIFE_INSURANCE);
        UnassignPolicyCommand expectedCommand = new UnassignPolicyCommand(targetIndex, policyName);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_POLICY_DESC, PolicyName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + POLICY_DESC_LIFE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + POLICY_DESC_LIFE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
