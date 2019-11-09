package seedu.savenus.storage.sort;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.sort.CustomSorter;

//@@author seanlowjk
public class JsonAdaptedCustomSortTest {
    @Test
    public void null_adaptedCustomSort_error() {
        CustomSorter sort = null;
        assertThrows(
                NullPointerException.class, () -> new JsonAdaptedCustomSort(sort));
    }

    @Test
    public void invalidFields_error() {
        List<String> fields = new ArrayList<String>();
        fields.add("invalid");
        JsonAdaptedCustomSort sorter = new JsonAdaptedCustomSort(fields);
        assertThrows(
                IllegalValueException.class, () -> sorter.toModelType());
    }
}
