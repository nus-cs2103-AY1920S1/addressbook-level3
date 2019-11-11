package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;
import static seedu.address.testutil.BodyBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalBodies.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.logic.parser.utility.UpdateWorkerDescriptor;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;
import seedu.address.testutil.BodyBuilder;
import seedu.address.testutil.WorkerBuilder;

//@@author ambervoong
public class UpdateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        String expectedError = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        // no index specified
        assertParseFailure(parser, DEFAULT_NAME, expectedError);

        // no field specified
        assertParseFailure(parser, "1", expectedError);

        // no index and no field specified
        assertParseFailure(parser, "", expectedError);

        // negative index
        assertParseFailure(parser, "-5", expectedError);

        // zero index
        assertParseFailure(parser, "0", expectedError);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", expectedError);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", expectedError);

        // invalid characters. Unicode is not allowed.
        assertParseFailure(parser, "@#!$!@#$raf3,1947889''", expectedError);
        assertParseFailure(parser, " شتا تاتا تاتا [كاملة", expectedError);
        assertParseFailure(parser, "汉字汉字汉字", expectedError);

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
    public void parseBody_fieldsPresent_success() {
        UniqueIdentificationNumberMaps.clearAllEntries();

        Body expectedBody = new BodyBuilder(ALICE).build(1);
        UpdateBodyDescriptor descriptor = new UpdateBodyDescriptor();
        descriptor.setSex(Sex.MALE);

        // Update command only requires one field to be specified at minimum
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male",
                new UpdateCommand(expectedBody.getIdNum(), descriptor));

        descriptor.setCauseOfDeath("asphyxiation");
        // Two fields specified
        assertParseSuccess(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " male " + PREFIX_CAUSE_OF_DEATH + " asphyxiation",
                new UpdateCommand(expectedBody.getIdNum(), descriptor));
    }

    @Test
    public void parse_noFieldsPresent_failure() {
        // Update command requires one field to be specified at minimum
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1",
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + PREFIX_FLAG + "w " + PREFIX_IDENTIFICATION_NUMBER + " 1",
                MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, " " + PREFIX_FLAG + "f " + PREFIX_IDENTIFICATION_NUMBER + " 1",
                MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidId_failure() {
        String expectedIndex = String.format(MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX + ". Please give a "
                + "positive non-zero number.");
        String expectedError = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " -1 "
                        + PREFIX_SEX + " male",
                expectedError);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 0 "
                        + PREFIX_SEX + " male",
                expectedIndex);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " abc "
                        + PREFIX_SEX + " male",
                expectedIndex);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " "
                        + PREFIX_SEX + " male",
                expectedIndex);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + ""
                        + PREFIX_SEX + " male",
                expectedError);
    }

    @Test
    public void parse_invalidFields_failure() {

        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_NAME + "!!",
                Name.MESSAGE_CONSTRAINTS);

        // Invalid gender
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                    + PREFIX_SEX + "Gerbil",
                Sex.MESSAGE_CONSTRAINTS);

        // Invalid date
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "ءىءءءى!@!$#%ىىىىىىىىى",
                MESSAGE_INVALID_DATE);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "1aaa1212",
                MESSAGE_INVALID_DATE);
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_OF_BIRTH + "00/00/a000",
                MESSAGE_INVALID_DATE);
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
                MESSAGE_INVALID_DATE);

        // Multiple invalid fields. Error output is for the first invalid field.
        assertParseFailure(parser, " " + PREFIX_FLAG + "b " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_SEX + " Gerbil "
                        + PREFIX_DATE_OF_BIRTH + " aaa",
                Sex.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parseWorker_fieldsPresent_success() throws ParseException {
        UniqueIdentificationNumberMaps.clearAllEntries();
        Worker worker = new WorkerBuilder().build();
        UpdateWorkerDescriptor descriptor = new UpdateWorkerDescriptor();
        descriptor.setDateJoined(ParserUtil.parseDate("01/02/1313"));

        // Update command only requires one field to be specified at minimum
        assertParseSuccess(parser, " " + PREFIX_FLAG + "w " + PREFIX_IDENTIFICATION_NUMBER + " 1 "
                        + PREFIX_DATE_JOINED + " 01/02/1313",
                new UpdateCommand(worker.getIdNum(), descriptor));
    }
}
//@@author
