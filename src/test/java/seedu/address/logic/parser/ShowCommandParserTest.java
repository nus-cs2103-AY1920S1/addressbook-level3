package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.personutil.PersonBuilder;

public class ShowCommandParserTest {
    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void equals() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY, new ShowCommand<Name>(expectedPerson.getName()));
    }
}
