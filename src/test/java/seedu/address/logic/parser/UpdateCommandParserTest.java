package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.BodyBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.testutil.BodyBuilder;

//@@author ambervoong
public class UpdateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DEFAULT_NAME, IdentificationNumber.MESSAGE_CONSTRAINTS);

        // no field specified
        assertParseFailure(parser, "1", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // no index and no field specified
        assertParseFailure(parser, "", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // negative index
        assertParseFailure(parser, "-5", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // zero index
        assertParseFailure(parser, "0", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", IdentificationNumber.MESSAGE_CONSTRAINTS);

        // invalid characters
        assertParseFailure(parser, "@#!$!@#$raf3,1947889''", IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " شتا تاتا تاتا [كاملة", IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "汉字汉字汉字", IdentificationNumber.MESSAGE_CONSTRAINTS);

    }

    /* To be added after the name prefix has been removed from person.
    @Test
    public void parse_allfieldsPresent_success() {
        Body expectedBody = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor(BOB);

        .withDateOfAdmission("01/01/1991")
                .withName("Bob Chachki")
                .withSex(Sex.MALE)
                .withNric("S1224567A")
                .withReligion(Religion.CHRISTIANITY)
                .withCauseOfDeath("NECROSIS")
                .withOrgansForDonation(new ArrayList<>())
                .withStatus(BodyStatus.ARRIVED)
                .withFridgeId(1)
                .withDateOfBirth("02/09/1982")
                .withDateOfDeath("01/06/1971")
                .withNextOfKin("Ben Chachki")
                .withRelationship("Father")
                .withKinPhoneNumber("87120919")

        // Update command only requires one field to be specified at minimum
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male",
                new UpdateCommand(expectedBody.getBodyIdNum(), descriptor));

        descriptor.setCauseOfDeath("asphyxiation");
        // Two fields specified
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male " + PREFIX_CAUSE_OF_DEATH + " asphyxiation",
                new UpdateCommand(expectedBody.getBodyIdNum(), descriptor));
    }

     */

    @Test
    public void parse_fieldsPresent_success() {
        Body expectedBody = new BodyBuilder(ALICE).build();
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        descriptor.setSex(Sex.MALE);

        // Update command only requires one field to be specified at minimum
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male",
                new UpdateCommand(expectedBody.getBodyIdNum(), descriptor));

        descriptor.setCauseOfDeath("asphyxiation");
        // Two fields specified
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male " + PREFIX_CAUSE_OF_DEATH + " asphyxiation",
                new UpdateCommand(expectedBody.getBodyIdNum(), descriptor));
    }

    @Test
    public void parse_noFieldsPresent_failure() {
        // Update command requires one field to be specified at minimum
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1",
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidId_failure() {
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " -1 "
                        + PREFIX_SEX + " male",
                IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 0 "
                        + PREFIX_SEX + " male",
                IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " abc "
                        + PREFIX_SEX + " male",
                IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " "
                        + PREFIX_SEX + " male",
                IdentificationNumber.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + ""
                        + PREFIX_SEX + " male",
                IdentificationNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidFields_failure() {
        // Todo: Invalid name. After first name/last name prefix is removed.
        /*
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_NAME + "1111",
                Name.MESSAGE_CONSTRAINTS);

         */
        // Invalid gender
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                    + PREFIX_SEX + "Gerbil",
                Sex.MESSAGE_CONSTRAINTS);

        // Invalid date
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "12//1212",
                "Wrong date format");
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "1aaa1212",
                "Wrong date format");
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "00/00/a000",
                "Wrong date format");
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "",
                UpdateCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + " ",
                UpdateCommand.MESSAGE_NOT_EDITED);

        // Valid field plus invalid field.
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male "
                        + PREFIX_DATE_OF_BIRTH + " aaa",
                "Wrong date format");

        // Multiple invalid fields. Error output is for the first invalid field.
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " Gerbil "
                        + PREFIX_DATE_OF_BIRTH + " aaa",
                Sex.MESSAGE_CONSTRAINTS);
    }

    /*


    @Test
    public void parse_allFieldsSpecified_success() {
        IdentificationNumber targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        IdentificationNumber targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        IdentificationNumber targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        IdentificationNumber targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        IdentificationNumber targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        IdentificationNumber targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

     */
}
