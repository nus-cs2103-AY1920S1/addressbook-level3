package seedu.savenus.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.sorter.CustomSorter;

/**
 * An Immutable Recommendation Set that is serializable to JSON format.
 */
@JsonRootName(value = "savenus")
public class JsonSerializableCustomSort {
    private JsonAdaptedCustomSort sort;

    /**
     * Constructs a {@code JsonSerializableRecs} with the given recommendations.
     */
    @JsonCreator
    public JsonSerializableCustomSort(@JsonProperty("customSort") JsonAdaptedCustomSort customSort) {
        this.sort = customSort;
    }

    /**
     * Converts a given {@code CustomSorter} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCustomSort}.
     */
    public JsonSerializableCustomSort(CustomSorter source) {
        sort = new JsonAdaptedCustomSort(source.getComparator().getFieldList());
    }

    /**
     * Converts this UserRecommendations into the model's {@code CustomSorter} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CustomSorter toModelType() throws IllegalValueException {
        return sort.toModelType();
    }
}
