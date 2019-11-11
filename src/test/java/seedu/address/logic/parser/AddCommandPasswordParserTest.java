package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.PasswordBuilder.DEFAULT_PASSWORD_DESCRIPTION;
import static seedu.address.testutil.PasswordBuilder.DEFAULT_PASSWORD_VALUE;
import static seedu.address.testutil.PasswordBuilder.DEFAULT_USERNAME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.password.Password;
import seedu.address.testutil.PasswordBuilder;



public class AddCommandPasswordParserTest {
    private AddPasswordCommandParser addPasswordCommandParser = new AddPasswordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Password expectedPassword = new PasswordBuilder().build();
        String testString = " u/" + DEFAULT_USERNAME + " p/"
                + DEFAULT_PASSWORD_VALUE + " d/" + DEFAULT_PASSWORD_DESCRIPTION;
        assertEquals(addPasswordCommandParser.parse(testString), new AddPasswordCommand(expectedPassword));
    }

}
