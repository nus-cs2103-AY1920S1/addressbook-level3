package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MERGE_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FIRE_INSURANCE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PolicyBuilder;
import seedu.address.logic.commands.merge.MergePolicyCommand;
import seedu.address.logic.commands.merge.MergePolicyConfirmedCommand;
import seedu.address.logic.commands.merge.MergePolicyRejectedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Description;
import seedu.address.model.policy.Policy;

public class AddressBookParserMergePolicyTest {

    private final AddressBookParser parser = new AddressBookParser(false);

    private Policy policy = new PolicyBuilder().withDescription(VALID_DESCRIPTION_FIRE_INSURANCE).build();

    @BeforeEach
    public void setUp() {
        parser.setIsMerging(true);
        parser.setCurrentMergeCommand(new MergePolicyCommandStub(policy));
        parser.setMergeType(AddressBookParser.MERGE_POLICY);
    }

    @Test
    public void parseCommand_yes_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePolicyConfirmedCommand.COMMAND_WORD)
                instanceof MergePolicyConfirmedCommand);
    }

    @Test
    public void parseCommand_enter_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePolicyConfirmedCommand.DEFAULT_COMMAND_WORD)
                instanceof MergePolicyConfirmedCommand);
    }

    @Test
    public void parseCommand_no_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePolicyRejectedCommand.COMMAND_WORD)
                instanceof MergePolicyRejectedCommand);
    }


    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        MergePolicyCommand mergeCommandStub = new MergePolicyCommandStub(policy);
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_MERGE_COMMAND,
                mergeCommandStub.getNextMergePrompt()), (
        ) -> parser.parseCommand("unknownCommand"));
    }

    private class MergePolicyCommandStub extends MergePolicyCommand {
        /**
         * Creates an Merge Command to update the original {@code Policy} to the new {@code Policy}
         *
         * @param inputPolicy
         */
        private Policy originalPolicy = new PolicyBuilder().build();

        public MergePolicyCommandStub(Policy inputPolicy) {
            super(inputPolicy);
        }

        public void updateOriginalPolicy(Policy editedPolicy) {
            this.originalPolicy = editedPolicy;
        }

        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Description.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPolicy.getDescription().description + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPolicy().getDescription().description)
                .append(MERGE_INSTRUCTIONS);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
        }

        public String getNextMergeFieldType() {
            return Description.DATA_TYPE;
        }

        public Policy getInputPolicy() {
            return super.getInputPolicy();
        }

        public Policy getOriginalPolicy() {
            return this.originalPolicy;
        }

        public boolean onlyOneMergeLeft() {
            return true;
        }

    }
}
