package seedu.savenus.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.logic.parser.FieldParser;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.sorter.CustomSorter;

/**
 *  Jackson-friendly version of {@link CustomSorter}.
 */
public class JsonAdaptedCustomSort {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CustomSorter fields is missing!";

    private List<String> fields;

    /**
     * Constructs a {@code JsonAdaptedRecs} with the given user's recommendations.
     */
    @JsonCreator
    public JsonAdaptedCustomSort(@JsonProperty("fields") List<String> fields) {
        this.fields = fields;
    }

    /**
     * Converts a given {@code UserRecommendations} into this class for Jackson use.
     */
    public JsonAdaptedCustomSort(CustomSorter source) {
        fields = source.getComparator().getFieldList();
    }

    /**
     * Converts this Jackson-friendly adapted wallet object into the model's {@code UserRecommendations} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted UserRecommendations.
     */
    public CustomSorter toModelType() throws IllegalValueException {
        CustomSorter sorter;

        if (fields == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        } else {
            try {
                FieldParser fieldParser = new FieldParser();
                fieldParser.checkKeywords(fields);
            } catch (ParseException e) {
                throw new IllegalValueException(e.getMessage());
            }
        }

        sorter = new CustomSorter();
        sorter.setComparator(fields);
        return sorter;
    }
}
