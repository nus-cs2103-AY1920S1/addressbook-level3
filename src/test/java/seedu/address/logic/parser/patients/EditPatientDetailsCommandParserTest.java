package seedu.address.logic.parser.patients;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.patients.EditPatientDetailsCommand;
import seedu.address.model.Model;
import seedu.address.model.common.Tag;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

public class EditPatientDetailsCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPatientDetailsCommand.MESSAGE_USAGE);

    private Model model = TestUtil.getTypicalModelManager();
    private EditPatientDetailsCommandParser parser = new EditPatientDetailsCommandParser(model);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPatientDetailsCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 l/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
            Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
            Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
            Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                                   + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        Person personToEdit = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_AMY)
                                                  .withPhone(VALID_PHONE_BOB)
                                                  .withEmail(VALID_EMAIL_AMY)
                                                  .withAddress(VALID_ADDRESS_AMY)
                                                  .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                                                  .build();

        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(personToEdit, editedPerson),
                new EditPatientDetailsCommand(editedPerson, personToEdit)
        ));
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        Person editedPerson = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                                                  .withEmail(VALID_EMAIL_AMY).build();

        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(ALICE, editedPerson),
                new EditPatientDetailsCommand(editedPerson, ALICE)
            ));

    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        Person editedPerson = new PersonBuilder(CARL).withName(VALID_NAME_AMY).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        editedPerson = new PersonBuilder(CARL).withPhone(VALID_PHONE_AMY).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        editedPerson = new PersonBuilder(CARL).withEmail(VALID_EMAIL_AMY).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        editedPerson = new PersonBuilder(CARL).withAddress(VALID_ADDRESS_AMY).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        editedPerson = new PersonBuilder(CARL).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                                   + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                                   + TAG_DESC_FRIEND
                                   + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        Person editedPerson = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                                                  .withEmail(VALID_EMAIL_BOB)
                                                  .withAddress(VALID_ADDRESS_BOB)
                                                  .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                                                  .build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(ALICE, editedPerson),
                new EditPatientDetailsCommand(editedPerson, ALICE)
            ));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        Person editedPerson = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(ALICE, editedPerson),
                new EditPatientDetailsCommand(editedPerson, ALICE)
            ));

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                            + PHONE_DESC_BOB;
        editedPerson = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                             .withAddress(VALID_ADDRESS_BOB).build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(ALICE, editedPerson),
                new EditPatientDetailsCommand(editedPerson, ALICE)
            ));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        Person editedPerson = new PersonBuilder(CARL).withTags().build();
        assertParseSuccess(parser, userInput,
            new ReversibleActionPairCommand(
                new EditPatientDetailsCommand(CARL, editedPerson),
                new EditPatientDetailsCommand(editedPerson, CARL)
            ));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        Model model = TestUtil.getTypicalModelManager();
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        String userInput = outOfBoundIndex.getOneBased() + TAG_EMPTY;

        assertParseFailure(new EditPatientDetailsCommandParser(model), userInput,
            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
