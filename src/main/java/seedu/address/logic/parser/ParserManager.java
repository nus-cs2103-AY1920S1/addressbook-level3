// @@author sreesubbash
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.cardcommands.AddCommand;
import seedu.address.logic.commands.cardcommands.ClearCommand;
import seedu.address.logic.commands.cardcommands.DeleteCommand;
import seedu.address.logic.commands.cardcommands.EditCommand;
import seedu.address.logic.commands.cardcommands.FindCommand;
import seedu.address.logic.commands.cardcommands.ListCommand;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.commands.gamecommands.GuessCommand;
import seedu.address.logic.commands.gamecommands.SkipCommand;
import seedu.address.logic.commands.gamecommands.StopCommand;
import seedu.address.logic.commands.settingcommands.AvatarCommand;
import seedu.address.logic.commands.settingcommands.DifficultyCommand;
import seedu.address.logic.commands.settingcommands.HintsCommand;
import seedu.address.logic.commands.settingcommands.ThemeCommand;
import seedu.address.logic.commands.statisticscommands.ResetCommand;
import seedu.address.logic.commands.switches.HelpCommand;
import seedu.address.logic.commands.switches.SelectCommand;
import seedu.address.logic.commands.switches.SwitchCommand;
import seedu.address.logic.commands.switches.SwitchToExitCommand;
import seedu.address.logic.commands.switches.SwitchToHomeCommand;
import seedu.address.logic.commands.switches.SwitchToOpenCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.commands.switches.SwitchToStartCommand;
import seedu.address.logic.commands.wordbankcommands.CreateCommand;
import seedu.address.logic.commands.wordbankcommands.ExportCommand;
import seedu.address.logic.commands.wordbankcommands.ImportCommand;
import seedu.address.logic.commands.wordbankcommands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.game.GuessCommandParser;
import seedu.address.logic.parser.home.CreateCommandParser;
import seedu.address.logic.parser.home.ExportCommandParser;
import seedu.address.logic.parser.home.ImportCommandParser;
import seedu.address.logic.parser.home.RemoveCommandParser;
import seedu.address.logic.parser.home.SelectCommandParser;
import seedu.address.logic.parser.open.AddCommandParser;
import seedu.address.logic.parser.open.ClearCommandParser;
import seedu.address.logic.parser.open.DeleteCommandParser;
import seedu.address.logic.parser.open.EditCommandParser;
import seedu.address.logic.parser.open.FindCommandParser;
import seedu.address.logic.parser.open.ListCommandParser;
import seedu.address.logic.parser.open.ResetCommandParser;
import seedu.address.logic.parser.settings.AvatarCommandParser;
import seedu.address.logic.parser.settings.DifficultyCommandParser;
import seedu.address.logic.parser.settings.HintsCommandParser;
import seedu.address.logic.parser.settings.ThemeCommandParser;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;

/**
 * Manages SpecificMode Parsers depending on internal state
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");


    /**
     * Used to maintain internal state.
     */
    private ModeEnum mode;
    private boolean gameIsOver;
    private boolean bankLoaded;

    /**
     * Used to parse SwitchCommands.
     */
    private SpecificModeParser switchParser;

    /**
     * Used to parse Commands belonging to current Mode.
     */
    private SpecificModeParser currentParser;

    /**
     * Empty constructor that initialises switchParser
     * and sets currentParser to HomeMode
     */
    public ParserManager() {

        this.gameIsOver = true;

        this.switchParser = new SpecificModeParser();

        switchParser.add(SwitchToOpenCommand.class, null);
        switchParser.add(SwitchToHomeCommand.class, null);
        switchParser.add(SwitchToStartCommand.class, StartCommandParser.class);
        switchParser.add(SwitchToSettingsCommand.class, null);

        this.mode = ModeEnum.HOME;

        this.currentParser = setCurrentParser(this.mode);


    }



    /**
     * Gets current mode from internal state.
     *
     * @return ModeEnum representing current mode
     */
    public ModeEnum getMode() {
        return mode;
    }

    /**
     * Constructs and returns a SpecificModeParser matching mode parameter.
     *
     * @param mode current mode
     * @return SpecificModeParser that matches mode
     */
    private SpecificModeParser setCurrentParser(ModeEnum mode) {

        SpecificModeParser temp = new SpecificModeParser();

        switch (mode) {

        case OPEN:
            temp.add(AddCommand.class, AddCommandParser.class);
            temp.add(EditCommand.class, EditCommandParser.class);
            temp.add(DeleteCommand.class, DeleteCommandParser.class);
            temp.add(FindCommand.class, FindCommandParser.class);
            temp.add(ClearCommand.class, ClearCommandParser.class);
            temp.add(ListCommand.class, ListCommandParser.class);
            temp.add(ResetCommand.class, ResetCommandParser.class);
            temp.add(SwitchToExitCommand.class, null);
            return temp;

        case HOME:
            temp.add(SelectCommand.class, SelectCommandParser.class);
            temp.add(ImportCommand.class, ImportCommandParser.class);
            temp.add(ExportCommand.class, ExportCommandParser.class);
            temp.add(CreateCommand.class, CreateCommandParser.class);
            temp.add(RemoveCommand.class, RemoveCommandParser.class);
            temp.add(HelpCommand.class, null);
            temp.add(SwitchToExitCommand.class, null);
            return temp;

        case SETTINGS:
            temp.add(DifficultyCommand.class, DifficultyCommandParser.class);
            temp.add(HintsCommand.class, HintsCommandParser.class);
            temp.add(ThemeCommand.class, ThemeCommandParser.class);
            temp.add(AvatarCommand.class, AvatarCommandParser.class);
            temp.add(SwitchToExitCommand.class, null);
            return temp;

        case GAME:
            temp.add(GuessCommand.class, GuessCommandParser.class);
            temp.add(SkipCommand.class, null);
            temp.add(StopCommand.class, null);
            return temp;

        default:
            temp.add(SwitchToOpenCommand.class, null);
            temp.add(SwitchToHomeCommand.class, null);
            temp.add(SwitchToStartCommand.class, StartCommandParser.class);
            temp.add(SwitchToSettingsCommand.class, null);
            return temp;

        }
    }

    /**
     * Updates the current state of ParserManager based on input booleans.
     *
     * @param bankLoaded if bank is loaded
     * @param gameIsOver if game is over
     */
    public void updateState(boolean bankLoaded, boolean gameIsOver) {
        this.bankLoaded = bankLoaded;
        this.gameIsOver = gameIsOver;
    }

    /**
     * Gets AutoFillAction objects based on input string.
     *
     * @param input current user input
     * @return List of AutoFillActions
     */
    public List<AutoFillAction> getAutoFill(String input) {

        List<AutoFillAction> result = new ArrayList<>();

        if (gameIsOver && bankLoaded) {
            for (AutoFillAction action : switchParser.getAutoFill(input)) {
                result.add(action);
            }
        }

        if (!(gameIsOver && mode == ModeEnum.GAME)) {
            for (AutoFillAction action : currentParser.getAutoFill(input)) {
                result.add(action);
            }
        }

        return result;
    }


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, ModeSwitchException {

        SwitchCommand switchCommand = null;
        Command currentModeCommand = null;

        if (bankLoaded && gameIsOver) {
            switchCommand = (SwitchCommand) switchParser.parseCommand(userInput);
        }

        if (!(mode == ModeEnum.GAME && gameIsOver)) {
            currentModeCommand = currentParser.parseCommand(userInput);
        }

        if (switchCommand != null) {
            mode = switchCommand.getNewMode(mode);
            currentParser = setCurrentParser(mode);
            return switchCommand;
        } else if (currentModeCommand != null) {
            return currentModeCommand;
        } else if (checkIfModeCommandIsValid(userInput)) {
            throw new ParseException("This command does not work right now\n"
                    + "Try switching mode, selecting WordBank or Stopping game");
        } else if (checkIfSwitchCommandValid(userInput)) {
            throw new ParseException("Unable to switch mode. Try selecting WordBank or stopping game.");
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }

    /**
     * Checks if user input can be parsed by other mode parsers.
     * @param text is user input
     * @return true if it can be parsed
     */
    private boolean checkIfModeCommandIsValid(String text) {
        List<ModeEnum> allModes = new ArrayList<>();
        allModes.add(ModeEnum.OPEN);
        allModes.add(ModeEnum.HOME);
        allModes.add(ModeEnum.SETTINGS);
        allModes.add(ModeEnum.GAME);
        SpecificModeParser tempParser;
        for (ModeEnum modeEnum : allModes) {
            tempParser = setCurrentParser(modeEnum);
            try {
                Command command = tempParser.parseCommand(text);
                if (command != null) {
                    return true;
                }
            } catch (ParseException e) {
                return true;
            }
        }
        return false;

    }

    /**
     * Checks if user input can be parsed by switch parsers.
     * @param text is user input
     * @return true if it can be parsed
     */
    private boolean checkIfSwitchCommandValid(String text) {
        SpecificModeParser tempParser = new SpecificModeParser();
        tempParser.add(SwitchToOpenCommand.class, null);
        tempParser.add(SwitchToHomeCommand.class, null);
        tempParser.add(SwitchToStartCommand.class, StartCommandParser.class);
        tempParser.add(SwitchToSettingsCommand.class, null);
        try {
            Command command = tempParser.parseCommand(text);
            if (command != null) {
                return true;
            }
        } catch (ParseException e) {
            return true;
        }
        return false;

    }

    /**
     * Gets a list of modes available to switch to based on internal state
     *
     * @return a list of ModeEnum
     */
    public List<ModeEnum> getModes() {

        List<ModeEnum> temp = new ArrayList<>();

        if (gameIsOver && bankLoaded) {
            temp.add(ModeEnum.OPEN);
            temp.add(ModeEnum.HOME);
            temp.add(ModeEnum.SETTINGS);
            temp.add(ModeEnum.GAME);
        }

        return temp;
    }

}
