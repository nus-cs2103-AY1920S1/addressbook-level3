// @@author sreesubbash
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.switches.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

/**
 * Parses user input.
 */
public class SpecificModeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Used to handle Parser and Command classes.
     */
    private ClassUtil classUtil;

    /**
     * Constructor that instantiates an empty classUtil.
     */
    public SpecificModeParser() {
        this.classUtil = new ClassUtil();
    }

    /**
     * Adds a Command Class and Parser Class into classUtil
     * @param command Command Class
     * @param parser Parser Class if any, else null
     */
    public void add(Class command, Class parser) {
        classUtil.add(new ClassPair(command, parser));
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
        return temp;
    }

    /**
     * Gets AutoFillAction objects based on input string.
     * @param input current user input
     * @return List of AutoFillActions
     */
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
