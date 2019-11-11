package seedu.billboard.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.billboard.logic.parser.TagCommandParser.MESSAGE_TAG_COMMANDS;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.AddTagCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;


public class TagCommandParserTest {
    private final TagCommandParser parser = new TagCommandParser();

    @Test
    public void parseCommand_addTag() throws Exception {
        final List<String> tagNames = new ArrayList<>();
        tagNames.add("school");
        AddTagCommand command = (AddTagCommand) parser.parse(AddTagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_EXPENSE.getOneBased() + " " + PREFIX_TAG + tagNames.get(0));
        assertEquals(new AddTagCommand(INDEX_FIRST_EXPENSE, tagNames), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_TAG_COMMANDS, ()
            -> parser.parse(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_TAG_COMMANDS, () -> parser.parse("unknownCommand"));
    }
}
