package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;

// @@author dalisc

public class FilterCommandParserTest {

    private static final String BODY_FLAG = "b";
    private static final String WORKER_FLAG = "w";

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        String bodyArgs = " -b /sex male /cod natural";
        ArgumentMultimap bodyMap = ArgumentTokenizer.tokenize(bodyArgs, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        String workerArgs = " -w /sex female /employmentStatus cleaning";
        ArgumentMultimap workerMap = ArgumentTokenizer.tokenize(workerArgs, PREFIX_PHONE_NUMBER,
                PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

        FilterCommand expectedFilterCommand1 =
                new FilterCommand(bodyMap, BODY_FLAG);
        FilterCommand expectedFilterCommand2 =
                new FilterCommand(workerMap, WORKER_FLAG);

        // no leading and trailing whitespaces
        assertParseSuccess(parser,
                " -b /sex male /cod natural", expectedFilterCommand1);
        assertParseSuccess(parser,
                " -w /sex female /employmentStatus cleaning", expectedFilterCommand2);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                " -b /sex male  /cod  natural  ", expectedFilterCommand1);
        assertParseSuccess(parser,
                " -w /sex female   /employmentStatus cleaning     /dateJoined  20/10/2019  ", expectedFilterCommand2);
    }

}
