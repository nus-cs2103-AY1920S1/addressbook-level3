package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.app.AddCommand;
import seedu.address.logic.commands.app.ClearCommand;
import seedu.address.logic.commands.app.DeleteCommand;
import seedu.address.logic.commands.app.EditCommand;
import seedu.address.logic.commands.app.ExitCommand;
import seedu.address.logic.commands.app.FindCommand;
import seedu.address.logic.commands.app.HelpCommand;
import seedu.address.logic.commands.app.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

/**
 * Parses user input.
 */
public class AppModeParser extends ModeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private List<ClassPair> commandParserClassPairs;

    public AppModeParser() {
        //Class temp = AddCommandParser.class;
        this.commandParserClassPairs = new ArrayList<>();
        commandParserClassPairs.add(new ClassPair(AddCommand.class, AddCommandParser.class));
        commandParserClassPairs.add(new ClassPair(EditCommand.class, EditCommandParser.class));
        commandParserClassPairs.add(new ClassPair(DeleteCommand.class, DeleteCommandParser.class));
        commandParserClassPairs.add(new ClassPair(FindCommand.class, FindCommandParser.class));
        commandParserClassPairs.add(new ClassPair(ClearCommand.class, null));
        commandParserClassPairs.add(new ClassPair(ListCommand.class, null));
        commandParserClassPairs.add(new ClassPair(ExitCommand.class, null));
        commandParserClassPairs.add(new ClassPair(HelpCommand.class, null));
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        Command temp = ClassUtil.getCommandInstance(commandParserClassPairs, commandWord, arguments);
        if (temp == null) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        } else {
            return temp;
        }
    }

    public List<AutoFillAction> getAutoFill(String input) {
        List<AutoFillAction> temp = new ArrayList<>();
        for (String txt : ClassUtil.getAttribute(commandParserClassPairs, "COMMAND_WORD")) {
            if (txt.contains(input)) {
                temp.add(new AutoFillAction(txt));
            }
        }
        return temp;
    }

}
