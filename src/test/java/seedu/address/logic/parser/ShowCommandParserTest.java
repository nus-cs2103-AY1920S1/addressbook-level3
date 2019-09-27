package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ShowCommandParserTest {
    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void equals() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();

        assertParseSuccess(parser, NAME_DESC_AMY, new ShowCommand(expectedPerson.getName()));
    }
}
