package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MERGE_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.util.PersonBuilder;
import seedu.address.logic.commands.merge.MergePersonCommand;
import seedu.address.logic.commands.merge.MergePersonConfirmedCommand;
import seedu.address.logic.commands.merge.MergePersonRejectedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class AddressBookParserMergePersonTest {

    private final AddressBookParser parser = new AddressBookParser(false);

    private Person person = new PersonBuilder().withPhone(VALID_PHONE_BOB).build();

    @BeforeEach
    public void setUp() throws ParseException {
        parser.setIsMerging(true);
        parser.setCurrentMergeCommand(new MergePersonCommandStub(person));
        parser.setMergeType(AddressBookParser.MERGE_PERSON);
    }

    @Test
    public void parseCommand_yes_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePersonConfirmedCommand.COMMAND_WORD)
                instanceof MergePersonConfirmedCommand);
    }

    @Test
    public void parseCommand_enter_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePersonConfirmedCommand.DEFAULT_COMMAND_WORD)
                instanceof MergePersonConfirmedCommand);
    }

    @Test
    public void parseCommand_no_success() throws ParseException {
        assertTrue(parser.parseCommand(MergePersonRejectedCommand.COMMAND_WORD)
                instanceof MergePersonRejectedCommand);
    }


    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        MergePersonCommandStub mergeCommandStub = new MergePersonCommandStub(person);
        assertThrows(ParseException.class, String.format(MESSAGE_UNKNOWN_MERGE_COMMAND,
                mergeCommandStub.getNextMergePrompt()), (
        ) -> parser.parseCommand("unknownCommand"));
    }

    private class MergePersonCommandStub extends MergePersonCommand {
        /**
         * Creates an Merge Command to update the original {@code Person} to the new {@code Person}
         *
         * @param inputPerson
         */
        private Person originalPerson = new PersonBuilder().build();

        public MergePersonCommandStub(Person inputPerson) {
            super(inputPerson);
        }

        public void updateOriginalPerson(Person editedPerson) {
            this.originalPerson = editedPerson;
        }

        @Override
        public String getNextMergePrompt() {
            StringBuilder mergePrompt = new StringBuilder();
            mergePrompt.append(String.format(MERGE_COMMAND_PROMPT, Phone.DATA_TYPE) + "\n")
                .append(MERGE_ORIGINAL_HEADER + originalPerson.getPhone().value + "\n")
                .append(MERGE_INPUT_HEADER + super.getInputPerson().getPhone().value)
                .append(MERGE_INSTRUCTIONS);
            return mergePrompt.toString();
        }

        public void removeFirstDifferentField() {
        }

        public String getNextMergeFieldType() {
            return Phone.DATA_TYPE;
        }

        public Person getInputPerson() {
            return super.getInputPerson();
        }

        public Person getOriginalPerson() {
            return this.originalPerson;
        }

        public boolean onlyOneMergeLeft() {
            return true;
        }

    }
}
