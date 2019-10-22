package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.appcommands.AddCommand;
import seedu.address.logic.commands.appcommands.ClearCommand;
import seedu.address.logic.commands.appcommands.DeleteCommand;
import seedu.address.logic.commands.appcommands.EditCommand;
import seedu.address.logic.commands.appcommands.ExitCommand;
import seedu.address.logic.commands.appcommands.FindCommand;
import seedu.address.logic.commands.appcommands.HelpCommand;
import seedu.address.logic.commands.appcommands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class AppModeParser extends ModeParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private ClassUtil classUtil;
    private Model model;

    public AppModeParser(Model model) {
        //Class temp = AddCommandParser.class;
        this.classUtil = new ClassUtil(model);
        this.model = model;
        classUtil.add(new ClassPair(AddCommand.class, AddCommandParser.class));
        classUtil.add(new ClassPair(EditCommand.class, EditCommandParser.class));
        classUtil.add(new ClassPair(DeleteCommand.class, DeleteCommandParser.class));
        classUtil.add(new ClassPair(FindCommand.class, FindCommandParser.class));
        classUtil.add(new ClassPair(ClearCommand.class, null));
        classUtil.add(new ClassPair(ListCommand.class, null));
        classUtil.add(new ClassPair(ExitCommand.class, null));
        classUtil.add(new ClassPair(HelpCommand.class, null));
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
            if (txt.contains(input)) {
                temp.add(new AutoFillAction(txt));
            }
        }
        return temp;
    }

}
