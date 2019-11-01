package seedu.savenus.storage.alias;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.alias.AliasChecker;
import seedu.savenus.model.alias.AliasList;
import seedu.savenus.model.alias.AliasPair;

/**
 * An Immutable AliasList that is serializable to JSON format.
 */
@JsonRootName(value = "savenus")
public class JsonSerializableAliasList {

    public static final String INVALID_FIELD_MESSAGE_FORMAT = "AliasPairs have an error!";
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AliasPairs are missing!";

    private List<JsonAdaptedAliasPair> aliasList;

    /**
     * Constructs a {@code JsonSerializableAliasList} with the given recommendations.
     */
    @JsonCreator
    public JsonSerializableAliasList(@JsonProperty("aliasList") List<JsonAdaptedAliasPair> aliasList) {
        this.aliasList = new ArrayList<JsonAdaptedAliasPair>();
        for (JsonAdaptedAliasPair pair : aliasList) {
            this.aliasList.add(pair);
        }
    }

    /**
     * Converts a given {@code AliasList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAliasList}.
     */
    public JsonSerializableAliasList(AliasList source) {
        this.aliasList = new ArrayList<JsonAdaptedAliasPair>();
        List<AliasPair> list = source.getList();
        for (AliasPair pair : list) {
            this.aliasList.add(new JsonAdaptedAliasPair(pair));
        }
    }

    /**
     * Converts this AliasList into the model's {@code AliasList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AliasList toModelType() throws IllegalValueException {
        AliasList list = new AliasList();

        AliasList originalAliasList = new AliasList();
        int numberOfMissingCommands = originalAliasList.getList().size();

        for (JsonAdaptedAliasPair pair : aliasList) {
            if (AliasChecker.doesCommandWordExist(originalAliasList.getList(), pair.getCommandWord())) {
                numberOfMissingCommands--;
                list.changeAliasWord(pair.getCommandWord(), pair.getAliasWord());
            } else {
                throw new IllegalValueException(INVALID_FIELD_MESSAGE_FORMAT);
            }
        }

        if (numberOfMissingCommands != 0) {
            throw new IllegalValueException(MISSING_FIELD_MESSAGE_FORMAT);
        }

        return list;
    }
}
