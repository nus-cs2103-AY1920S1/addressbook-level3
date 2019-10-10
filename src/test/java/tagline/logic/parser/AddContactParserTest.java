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
import static tagline.testutil.TypicalPersons.AMY;
import static tagline.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import tagline.logic.commands.contact.CreateContactCommand;
import tagline.logic.parser.contact.AddContactParser;
import tagline.model.person.Person;
import tagline.testutil.PersonBuilder;

public class AddContactParserTest {
    private AddContactParser parser = new AddContactParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB, new CreateContactCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                new CreateContactCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new CreateContactCommand(expectedPerson));
    }
}
