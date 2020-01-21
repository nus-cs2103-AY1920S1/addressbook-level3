package seedu.algobase.storage.util;

import java.util.ArrayList;
import java.util.List;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.storage.JsonAdaptedKeyword;

/**
 * A container for Storage specific utility functions
 */
public class StorageUtil {

    public static List<Keyword> getKeywordsFromJson(
        List<JsonAdaptedKeyword> jsonKeywords, String missingFieldMessageFormat) throws IllegalValueException {

        if (jsonKeywords == null) {
            throw new IllegalValueException(String.format(missingFieldMessageFormat, "Keywords"));
        }

        final List<Keyword> predicateKeywords = new ArrayList<>();

        for (JsonAdaptedKeyword keyword: jsonKeywords) {
            if (keyword == null) {
                throw new IllegalValueException(
                    String.format(missingFieldMessageFormat,
                        Keyword.class.getSimpleName()));
            }
            predicateKeywords.add(keyword.toModelType());
        }

        return predicateKeywords;

    }

}
