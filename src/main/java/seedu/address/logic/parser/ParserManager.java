package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.Constructor;
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
    private List<String> switchCommandsList;

    public ParserManager () {
        this.mode = ModeEnum.LOAD;
        this.modeParser = getModeParser();
        this.tempMode = null;
        this.switchCommandsList = new ArrayList<>();
        Class cls = BankCommand.class;
        try {
            cls.getConstructor();
            Constructor cons = cls.getConstructor(String.class);
            Command test = (Command)cons.newInstance("addressbook");
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

        }
        System.out.print("111111111111111111: " + cls.getName());
        switchCommandsList.add(BankCommand.COMMAND_WORD);
        switchCommandsList.add(HomeCommand.COMMAND_WORD);
        switchCommandsList.add(LoadScreenCommand.COMMAND_WORD);
        switchCommandsList.add(SwitchToSettingsCommand.COMMAND_WORD);
    }

    public ModeEnum getMode() {
        return mode;
    }

    private ModeParser getModeParser() {
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
        if (command.postcondition()) {
            mode = tempMode;
            tempMode = null;
            this.modeParser = getModeParser();
        }
    }

    public List<AutoFillAction> getAutoFill(String text) {
        List<AutoFillAction> temp = new ArrayList<>();
        for (String s : switchCommandsList) {
            temp.add(new AutoFillAction(s));
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
        switch (commandWord) {

        case BankCommand.COMMAND_WORD:
            return new BankCommandParser().parse(arguments);

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        case LoadScreenCommand.COMMAND_WORD:
            return new LoadScreenCommand();

        case StartCommand.COMMAND_WORD:
            Class cls = StartCommand.class;
            try {
                cls.getConstructor();
                Constructor cons = cls.getConstructor();
                SwitchCommand test = (SwitchCommand)cons.newInstance();
                return test;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            }
            //return new StartCommand();

        case SwitchToSettingsCommand.COMMAND_WORD:
            return new SwitchToSettingsCommand();

        default:
            return null;
        }
    }

}
