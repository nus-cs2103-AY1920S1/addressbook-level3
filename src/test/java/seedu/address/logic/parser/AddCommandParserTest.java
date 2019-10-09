package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_JOINED_DESC_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.DATE_JOINED_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_ADMISSION_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_DEATH_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_FLAG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_ADMISSION_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_DEATH_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKER_FLAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBodies.JOHN;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalWorkers.XENIA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.WorkerBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        Worker expectedWorker = new WorkerBuilder(XENIA).withEmploymentStatus("").withDateOfBirth("").withPhone("")
                .withDesignation("").build();
        Body expectedBody = new BodyBuilder(JOHN).withCauseOfDeath("").withFridgeId("").withKinPhoneNumber("")
                .withNric("").withNextOfKin("").withOrgansForDonation("").withRelationship("").withReligion("")
                        .withStatus("").withDateOfBirth("").build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // Worker: multiple names - last name accepted
        assertParseSuccess(parser, VALID_WORKER_FLAG + NAME_DESC_ZACH + NAME_DESC_XENIA + DATE_JOINED_DESC_XENIA
                + SEX_DESC_XENIA, new AddCommand(expectedWorker));

        // Worker: multiple dateJoined - last dateJoined accepted
        assertParseSuccess(parser, VALID_WORKER_FLAG + NAME_DESC_XENIA + DATE_JOINED_DESC_ZACH
                + DATE_JOINED_DESC_XENIA + SEX_DESC_XENIA, new AddCommand(expectedWorker));

        // Worker:  multiple sexes - last sex accepted
        assertParseSuccess(parser, VALID_WORKER_FLAG + NAME_DESC_XENIA + DATE_JOINED_DESC_XENIA + SEX_DESC_ZACH
                + SEX_DESC_XENIA, new AddCommand(expectedWorker));

        // Body: multiple names - last name accepted
        assertParseSuccess(parser, VALID_BODY_FLAG + NAME_DESC_JANE + NAME_DESC_JOHN
            + DATE_OF_ADMISSION_DESC_JOHN + SEX_DESC_JOHN + DATE_OF_DEATH_DESC_JOHN, new AddCommand(expectedBody));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        //Worker: no phone
        Worker expectedWorker = new WorkerBuilder(XENIA).withEmploymentStatus("").withDateOfBirth("").withPhone("")
            .withDesignation("").build();
        assertParseSuccess(parser, VALID_WORKER_FLAG + NAME_DESC_XENIA + DATE_JOINED_DESC_XENIA + SEX_DESC_XENIA,
                new AddCommand(expectedWorker));

        //Body: no cause of death
        Body expectedBody = new BodyBuilder(JOHN).withCauseOfDeath("").withFridgeId("").withKinPhoneNumber("")
            .withNric("").withNextOfKin("").withOrgansForDonation("").withRelationship("").withReligion("")
            .withStatus("").withDateOfBirth("").build();
        System.out.println(VALID_BODY_FLAG + NAME_DESC_JOHN + SEX_DESC_JOHN
            + DATE_OF_ADMISSION_DESC_JOHN + DATE_OF_DEATH_DESC_JOHN);
        assertParseSuccess(parser, VALID_BODY_FLAG + NAME_DESC_JOHN + SEX_DESC_JOHN
                + DATE_OF_ADMISSION_DESC_JOHN + DATE_OF_DEATH_DESC_JOHN, new AddCommand(expectedBody));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // Worker: missing name prefix
        assertParseFailure(parser, VALID_WORKER_FLAG + VALID_NAME_XENIA + DATE_JOINED_DESC_XENIA + SEX_DESC_XENIA,
                expectedMessage);

        // Worker: all prefixes missing
        assertParseFailure(parser, VALID_WORKER_FLAG + VALID_NAME_XENIA + VALID_SEX_XENIA
                + VALID_DATE_JOINED_XENIA, expectedMessage);

        // Body: missing name prefix
        assertParseFailure(parser, VALID_BODY_FLAG + VALID_NAME_JOHN + SEX_DESC_JOHN
                + DATE_OF_ADMISSION_DESC_JOHN + DATE_OF_DEATH_DESC_JOHN, expectedMessage);

        // Worker: all prefixes missing
        assertParseFailure(parser, VALID_BODY_FLAG + VALID_NAME_JOHN + VALID_SEX_JOHN
            + VALID_DATE_OF_ADMISSION_JOHN + VALID_DATE_OF_DEATH_JOHN, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
