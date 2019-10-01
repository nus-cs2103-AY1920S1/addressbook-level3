package seedu.address.transaction.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.logic.exception.NotANumberException;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.ui.Ui;

public class HomeTabParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public Command parseCommand(String userInput) throws ParseException, NotANumberException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(Ui.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);


        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        default:
            throw new ParseException(Ui.NO_SUCH_COMMAND);

        }
    }
}
