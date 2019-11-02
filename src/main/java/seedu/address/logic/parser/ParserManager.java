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
import seedu.address.logic.commands.switches.SwitchCommand;
import seedu.address.logic.commands.switches.SwitchToExitCommand;
import seedu.address.logic.commands.switches.SwitchToHomeCommand;
import seedu.address.logic.commands.switches.SwitchToOpenCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.commands.switches.SwitchToStartCommand;
import seedu.address.logic.commands.wordbankcommands.SelectCommand;
import seedu.address.logic.commands.wordbankcommands.CreateCommand;
import seedu.address.logic.commands.wordbankcommands.ExportCommand;
import seedu.address.logic.commands.wordbankcommands.HelpCommand;
import seedu.address.logic.commands.wordbankcommands.ImportCommand;
import seedu.address.logic.commands.wordbankcommands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.game.GuessCommandParser;
import seedu.address.logic.parser.home.BankCommandParser;
import seedu.address.logic.parser.home.CreateCommandParser;
import seedu.address.logic.parser.home.ExportCommandParser;
import seedu.address.logic.parser.home.ImportCommandParser;
import seedu.address.logic.parser.home.RemoveCommandParser;
import seedu.address.logic.parser.open.AddCommandParser;
import seedu.address.logic.parser.open.DeleteCommandParser;
import seedu.address.logic.parser.open.EditCommandParser;
import seedu.address.logic.parser.open.FindCommandParser;
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
        this.mode = ModeEnum.HOME;
        this.gameIsOver = true;
        this.switchParser = new SpecificModeParser();
        switchParser.add(SwitchToOpenCommand.class, null);
        switchParser.add(SwitchToHomeCommand.class, null);
        switchParser.add(SwitchToStartCommand.class, StartCommandParser.class);
        switchParser.add(SwitchToSettingsCommand.class, null);
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
        switch (this.mode) {
        case OPEN:
            temp.add(AddCommand.class, AddCommandParser.class);
            temp.add(EditCommand.class, EditCommandParser.class);
            temp.add(DeleteCommand.class, DeleteCommandParser.class);
            temp.add(FindCommand.class, FindCommandParser.class);
            temp.add(ClearCommand.class, null);
            temp.add(ListCommand.class, null);
            temp.add(SwitchToExitCommand.class, null);
            return temp;
        case HOME:
            temp.add(SelectCommand.class, BankCommandParser.class);
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
            temp.add(SwitchToExitCommand.class, null);
            return temp;
        default:
            return null;
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
        List<AutoFillAction> temp = new ArrayList<>();
        if (gameIsOver && bankLoaded) {
            for (AutoFillAction action : switchParser.getAutoFill(input)) {
                temp.add(action);
            }
        }
        for (AutoFillAction action : currentParser.getAutoFill(input)) {
            temp.add(action);
        }
        return temp;
    }


    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, ModeSwitchException {
        Command temp = null;
        if (gameIsOver && bankLoaded) {
            temp = switchParser.parseCommand(userInput);
        }
        if (temp != null) {
            SwitchCommand switchCommand = (SwitchCommand) temp;
            mode = switchCommand.getNewMode(mode);
            currentParser = setCurrentParser(mode);
        } else {
            temp = currentParser.parseCommand(userInput);
        }
        if (temp != null) {
            return temp;
        }
        throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
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
