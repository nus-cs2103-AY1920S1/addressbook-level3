package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.appcommands.HelpCommand;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.commands.switches.BankCommand;
import seedu.address.logic.commands.switches.HomeCommand;
import seedu.address.logic.commands.switches.LoadScreenCommand;
import seedu.address.logic.commands.switches.StartCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;



/**
 * Parses user input.
 */
public class ParserManager {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private ModeParser modeParser;
    private ModeEnum mode;
    private ModeEnum tempMode;
    private boolean gameIsOver;

    private ClassUtil classUtil;
    private Model model;

    public ParserManager (Model model) {
        this.mode = ModeEnum.LOAD;
        this.modeParser = setModeParser();
        this.tempMode = null;
        this.gameIsOver = true;
        this.classUtil = new ClassUtil();
        this.model = model;
        classUtil.add(new ClassPair(BankCommand.class, BankCommandParser.class));
        classUtil.add(new ClassPair(HomeCommand.class, null));
        classUtil.add(new ClassPair(LoadScreenCommand.class, null));
        classUtil.add(new ClassPair(StartCommand.class, null));
        classUtil.add(new ClassPair(SwitchToSettingsCommand.class, null));
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
        default:
            return null;
        }
    }


    /**
     * Sets new state within parsermanager if command was successful.
     * @param command
     */
    public void updateState(Command command, boolean gameIsOver) {
        this.gameIsOver = gameIsOver;
        if (command.postcondition() && tempMode != null) {
            mode = tempMode;
            tempMode = null;
            this.modeParser = setModeParser();
        } else {
            tempMode = null;
        }
    }

    public List<AutoFillAction> getAutoFill(String input) {
        if (gameIsOver) {
            List<AutoFillAction> temp = new ArrayList<>();
            for (String txt : classUtil.getAttribute("COMMAND_WORD")) {
                if (txt.contains(input) || input.contains(txt)) {
                    temp.add(new AutoFillAction(txt));
                }
            }
            for (AutoFillAction action : modeParser.getAutoFill(input)) {
                temp.add(action);
            }
            return temp;
        } else {
            List<AutoFillAction> temp = new ArrayList<>();
            for (AutoFillAction action : modeParser.getAutoFill(input)) {
                temp.add(action);
            }
            return temp;
        }
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
        switchCommand = checkSwitchMode(userInput);

        if (switchCommand != null) {
            this.tempMode = switchCommand.getNewMode(mode);
            return switchCommand;
        } else {
            return this.modeParser.parseCommand(userInput);
        }
    }

    /**
     * Checks if current input is requesting a switch mode command.
     * @param userInput
     * @return
     * @throws ParseException
     */
    private SwitchCommand checkSwitchMode(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        return (SwitchCommand) classUtil.getCommandInstance(commandWord, arguments);
    }

    public List<ModeEnum> getModes() {
        List<ModeEnum> temp = new ArrayList<>();
        // TODO make it dynamic to switch command;
        temp.add(ModeEnum.APP);
        temp.add(ModeEnum.LOAD);
        temp.add(ModeEnum.GAME);
        temp.add(ModeEnum.SETTINGS);
        return temp;
    }

}
