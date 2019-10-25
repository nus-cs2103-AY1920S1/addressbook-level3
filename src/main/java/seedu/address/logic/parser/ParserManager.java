package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.appcommands.AddCommand;
import seedu.address.logic.commands.appcommands.ClearCommand;
import seedu.address.logic.commands.appcommands.DeleteCommand;
import seedu.address.logic.commands.appcommands.EditCommand;
import seedu.address.logic.commands.appcommands.ExitCommand;
import seedu.address.logic.commands.appcommands.FindCommand;
import seedu.address.logic.commands.appcommands.HelpCommand;
import seedu.address.logic.commands.appcommands.ListCommand;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.commands.gamecommands.GuessCommand;
import seedu.address.logic.commands.gamecommands.SkipCommand;
import seedu.address.logic.commands.gamecommands.StopCommand;
import seedu.address.logic.commands.loadcommands.BankCommand;
import seedu.address.logic.commands.loadcommands.CreateCommand;
import seedu.address.logic.commands.loadcommands.ExportCommand;
import seedu.address.logic.commands.loadcommands.ImportCommand;
import seedu.address.logic.commands.loadcommands.RemoveCommand;
import seedu.address.logic.commands.settingcommands.DifficultyCommand;
import seedu.address.logic.commands.settingcommands.HintsCommand;
import seedu.address.logic.commands.settingcommands.ThemeCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.OpenCommand;
import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.parser.app.AddCommandParser;
import seedu.address.logic.parser.app.DeleteCommandParser;
import seedu.address.logic.parser.app.EditCommandParser;
import seedu.address.logic.parser.app.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.game.GuessCommandParser;
import seedu.address.logic.parser.load.BankCommandParser;
import seedu.address.logic.parser.load.CreateCommandParser;
import seedu.address.logic.parser.load.ExportCommandParser;
import seedu.address.logic.parser.load.ImportCommandParser;
import seedu.address.logic.parser.load.RemoveCommandParser;
import seedu.address.logic.parser.settings.DifficultyCommandParser;
import seedu.address.logic.parser.settings.HintsCommandParser;
import seedu.address.logic.parser.settings.ThemeCommandParser;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;

/**
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private ModeEnum mode;
    private boolean gameIsOver;
    private boolean bankLoaded;

    private SpecificModeParser switchParser;
    private SpecificModeParser currentParser;

    public ParserManager () {
        this.mode = ModeEnum.HOME;
        this.gameIsOver = true;
        this.switchParser = new SpecificModeParser();
        switchParser.add(OpenCommand.class, null);
        switchParser.add(HomeCommand.class, null);
        switchParser.add(StartCommand.class, StartCommandParser.class);
        switchParser.add(SwitchToSettingsCommand.class, null);
        this.currentParser = setCurrentParser(this.mode);
    }

    public ModeEnum getMode() {
        return mode;
    }

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
            temp.add(ExitCommand.class, null);
            temp.add(HelpCommand.class, null);
            return temp;
        case HOME:
            temp.add(BankCommand.class, BankCommandParser.class);
            temp.add(ImportCommand.class, ImportCommandParser.class);
            temp.add(ExportCommand.class, ExportCommandParser.class);
            temp.add(CreateCommand.class, CreateCommandParser.class);
            temp.add(RemoveCommand.class, RemoveCommandParser.class);
            return temp;
        case SETTINGS:
            temp.add(DifficultyCommand.class, DifficultyCommandParser.class);
            temp.add(HintsCommand.class, HintsCommandParser.class);
            temp.add(ThemeCommand.class, ThemeCommandParser.class);
            return temp;
        case GAME:
            temp.add(GuessCommand.class, GuessCommandParser.class);
            temp.add(SkipCommand.class, null);
            temp.add(StopCommand.class, null);
            return temp;
        default:
            return null;
        }
    }


    /**
     * Updates the current state of the Model and ParserManager based on whether
     * {@code bankLoaded} is true, and whether {@code gameIsOver}.
     */
    public void updateState(boolean bankLoaded, boolean gameIsOver) {
        this.bankLoaded = bankLoaded;
        this.gameIsOver = gameIsOver;
    }

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
