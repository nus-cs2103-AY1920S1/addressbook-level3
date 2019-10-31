package seedu.savenus.logic.parser;

import seedu.savenus.model.alias.AliasList;

/**
 * Represents a Parser to parse the keyword given, as it may be an alias of a command word.
 */
public class CommandWordParser {

    /**
     * Parses the keyword into a command word if it is an alias.
     * @param aliasList the aliasList retrieved from the model.
     * @param keyword the command or alias word.
     * @return the word after being parsed.
     */
    public static String parseWord(AliasList aliasList, String keyword) {
        String commandWord = aliasList.getCommandWord(keyword);
        return commandWord;
    }
}
