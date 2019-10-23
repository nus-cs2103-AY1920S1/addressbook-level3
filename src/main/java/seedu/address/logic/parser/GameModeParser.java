package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.appcommands.HelpCommand;
import seedu.address.logic.commands.gamecommands.GuessCommand;
import seedu.address.logic.commands.gamecommands.SkipCommand;
import seedu.address.logic.commands.gamecommands.StopCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

/**
 * Parses user input.
 */
public class GameModeParser extends ModeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    protected ClassUtil classUtil;

    public GameModeParser() {
        this.classUtil = new ClassUtil();
        classUtil.add(new ClassPair(GuessCommand.class, GuessCommandParser.class));
        classUtil.add(new ClassPair(SkipCommand.class, null));
        classUtil.add(new ClassPair(StopCommand.class, null));
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

        Command temp = classUtil.getCommandInstance(commandWord, arguments);
        if (temp == null) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        } else {
            return temp;
        }
    }

    public List<AutoFillAction> getAutoFill(String input) {
        List<AutoFillAction> temp = new ArrayList<>();
        for (String txt : classUtil.getAttribute("COMMAND_WORD")) {
            if (txt.contains(input) || input.contains(txt)) {
                temp.add(new AutoFillAction(txt));
            }
        }
        return temp;
    }

}
