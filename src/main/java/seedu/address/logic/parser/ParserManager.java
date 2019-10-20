package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.app.AddCommand;
import seedu.address.logic.commands.app.ClearCommand;
import seedu.address.logic.commands.app.DeleteCommand;
import seedu.address.logic.commands.app.EditCommand;
import seedu.address.logic.commands.app.ExitCommand;
import seedu.address.logic.commands.app.FindCommand;
import seedu.address.logic.commands.app.HelpCommand;
import seedu.address.logic.commands.app.ListCommand;
import seedu.address.logic.commands.game.GuessCommand;
import seedu.address.logic.commands.game.SkipCommand;
import seedu.address.logic.commands.game.StopCommand;
import seedu.address.logic.commands.settings.DifficultyCommand;
import seedu.address.logic.commands.switches.BankCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.LoadScreenCommand;
import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.util.ModeEnum;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.AppModeParser;
import seedu.address.logic.parser.LoadModeParser;
import seedu.address.logic.parser.SettingsModeParser;
import seedu.address.logic.parser.GameModeParser;

/**
 * Parses user input.
 */
public class ParserManager {

    private ModeParser modeParser;
    private ModeEnum mode;
    private ModeEnum tempMode;
    private List<ClassPair> commandParserClassPairs;

    public ParserManager () {
        this.mode = ModeEnum.LOAD;
        this.modeParser = setModeParser();
        this.tempMode = null;
        this.commandParserClassPairs = new ArrayList<>();
        commandParserClassPairs.add(new ClassPair(BankCommand.class, BankCommandParser.class));
        commandParserClassPairs.add(new ClassPair(HomeCommand.class, null));
        commandParserClassPairs.add(new ClassPair(LoadScreenCommand.class, null));
        commandParserClassPairs.add(new ClassPair(StartCommand.class, null));
        commandParserClassPairs.add(new ClassPair(SwitchToSettingsCommand.class, null));
    }

    public ModeEnum getMode() {
        return mode;
    }

    private ModeParser setModeParser() {
        switch (this.mode) {
        case APP:
            return new AppModeParser();
        case LOAD:
            return new LoadModeParser();
        case SETTINGS:
            return new SettingsModeParser();
        case GAME:
            return new GameModeParser();
        }
        return null;
    }


    public void updateState(Command command) {
        if (command.postcondition() && tempMode != null) {
            mode = tempMode;
            tempMode = null;
            this.modeParser = setModeParser();
        } else {
            tempMode = null;
        }
    }

    public List<AutoFillAction> getAutoFill(String input) {
        List<AutoFillAction> temp = new ArrayList<>();
        for (String txt : ClassUtil.getAttribute(commandParserClassPairs, "COMMAND_WORD")) {
            if (txt.contains(input)) {
                temp.add(new AutoFillAction(txt));
            }
        }
        for (AutoFillAction action : modeParser.getAutoFill(input)) {
            temp.add(action);
        }
        return temp;
    }


    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, ModeSwitchException {
        SwitchCommand switchCommand = null;

        switchCommand = checkSwitchMode(userInput);

        if (switchCommand != null) {
            this.tempMode = switchCommand.getNewMode(mode);
            return switchCommand;
        } else {
            return this.modeParser.parseCommand(userInput);
        }
    }

    private SwitchCommand checkSwitchMode(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return (SwitchCommand) ClassUtil.getCommandInstance(commandParserClassPairs, commandWord, arguments);
    }

}
