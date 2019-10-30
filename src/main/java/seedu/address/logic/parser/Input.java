package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

import seedu.address.logic.commands.GenericCommandWord;

/**
 * Input to pass to parser.
 */
public class Input {

    /**
     * Used for initial separation of command word and args.
     */
    static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String MESSAGE_CONSTRAINTS_COMMAND_WORD = "(\\S+)";
    private String commandWord;
    private String arguments;

    public Input(String commandWord, String arguments) {
        requireNonNull(commandWord, arguments);
        checkArgument(isValidCommandWord(commandWord));
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    private static boolean isValidCommandWord(String commandWord) {
        return commandWord.matches(MESSAGE_CONSTRAINTS_COMMAND_WORD);
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getArguments() {
        return arguments;
    }

    public boolean isGeneric() {
        return GenericCommandWord.isGeneric(commandWord);
    }
}
