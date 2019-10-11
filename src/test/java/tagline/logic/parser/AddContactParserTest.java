package tagline.logic.parser;

import static tagline.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tagline.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static tagline.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tagline.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tagline.testutil.TypicalContacts.AMY;
import static tagline.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.parser.contact.AddContactParser;
import tagline.model.contact.Contact;
import tagline.testutil.ContactBuilder;

public class AddContactParserTest {
    private AddContactParser parser = new AddContactParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Contact expectedContact = new ContactBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedContact));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedContact));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedContact));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedContact));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedContact));

        // multiple tags - all accepted
        Contact expectedContactMultipleTags = new ContactBuilder(BOB).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new CreateContactCommand(expectedContactMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Contact expectedContact = new ContactBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new CreateContactCommand(expectedContact));
    }
}
