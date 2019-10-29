package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

public class Input {

    private static String MESSAGE_CONSTRAINTS_COMMAND_WORD = "(\\S+)";

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");


    String commandWord;
    String arguments;

    public Input(String commandWord, String arguments) {
        requireNonNull(commandWord,arguments);
        checkArgument(isValidCommandWord(commandWord));
        this.commandWord = commandWord;
        this.arguments = arguments;
    }

    private static boolean isValidCommandWord(String commandWord) {
        return commandWord.matches(MESSAGE_CONSTRAINTS_COMMAND_WORD);
    }

    public boolean isGeneric() {
        return GenericCommandWord.isGeneric(commandWord);
    }
}
