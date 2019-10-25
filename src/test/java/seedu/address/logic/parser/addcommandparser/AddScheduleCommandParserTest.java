package seedu.address.logic.parser.addcommandparser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MONDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;

public class AddScheduleCommandParserTest {

    private AddCustomerCommandParser parser = new AddCustomerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Schedule expectedSchedule = new ScheduleBuilder().withTags(VALID_TAG_MONDAY).build();

        Customer expectedCustomer = new CustomerBuilder(ALICE).withTags(VALID_TAG_RICH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BEN + NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_ALICE + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_BEN
                + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE
                + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE
                + EMAIL_DESC_BEN + EMAIL_DESC_ALICE
                + TAG_DESC_RICH, new AddCustomerCommand(expectedCustomer));

        // multiple tags - all accepted
        Customer expectedCustomerMultipleTags = new CustomerBuilder(ALICE).withTags(VALID_TAG_RICH, VALID_TAG_REGULAR)
                .build();
        assertParseSuccess(parser, NAME_DESC_ALICE + CONTACT_NUMBER_DESC_ALICE + EMAIL_DESC_ALICE
                + TAG_DESC_RICH + TAG_DESC_REGULAR, new AddCustomerCommand(expectedCustomerMultipleTags));
    }

}
