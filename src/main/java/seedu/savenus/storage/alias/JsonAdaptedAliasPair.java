package seedu.savenus.storage.alias;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.alias.AliasChecker;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.alias.AliasPair;

/**
 * Jackson-friendly version of {@link AliasPair}.
 */
public class JsonAdaptedAliasPair {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AliasPair's %s field is missing!";

    private final String commandWord;
    private final String aliasWord;

    @JsonCreator
    public JsonAdaptedAliasPair(@JsonProperty("commandword") String commandWord,
                                @JsonProperty("aliasword") String aliasWord) {
        this.commandWord = commandWord;
        this.aliasWord = aliasWord;
    }

    public JsonAdaptedAliasPair(AliasPair aliasPair) {
        this.commandWord = aliasPair.getCommandWord();
        this.aliasWord = aliasPair.getAliasWord();
    }

    public String getCommandWord() {
        return this.commandWord;
    }

    public String getAliasWord() {
        return this.aliasWord;
    }

    /**
     * Converts this Jackson-friendly adapted AliasPair object into the model's {@code AliasPair} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted AliasPair.
     */
    public AliasPair toModelType() throws IllegalValueException {
        if (commandWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "commandWord"));
        }

        if (!AliasChecker.isCommandWord(new AliasList().getList(), commandWord)) {
            throw new IllegalValueException("Invalid command!");
        }

        if (aliasWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "aliasword"));
        }

        return new AliasPair(commandWord, aliasWord);
    }
}
