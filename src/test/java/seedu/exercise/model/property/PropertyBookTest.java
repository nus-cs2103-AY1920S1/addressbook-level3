package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.P_SLASH;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.END_DATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.parser.Prefix;

public class PropertyBookTest {

    private final Set<CustomProperty> customPropertySet = Set.of(RATING);
    private PropertyBook testBook = new PropertyBook(new HashSet<>());

    @BeforeEach
    public void setUp() {
        testBook.clearCustomProperties();
    }

    @Test
    public void constructor_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PropertyBook(null));
    }

    @Test
    public void getCustomProperties() {
        testBook.addCustomProperties(customPropertySet);
        assertEquals(customPropertySet, PropertyBook.getCustomProperties());
    }

    @Test
    public void addCustomProperties() {
        Set<CustomProperty> anotherCustomPropertySet = Set.of(RATING, REMARK);
        testBook.addCustomProperties(anotherCustomPropertySet);
        assertEquals(anotherCustomPropertySet, PropertyBook.getCustomProperties());
    }

    @Test
    public void clearCustomProperties() {
        PropertyBook anotherBook = new PropertyBook(customPropertySet);
        anotherBook.clearCustomProperties();
        assertTrue(PropertyBook.getCustomProperties().isEmpty());
    }

    @Test
    public void addCustomProperty_removeCustomProperty() {
        testBook.addCustomProperty(END_DATE);
        Set<CustomProperty> expectedResult = Set.of(END_DATE);
        assertEquals(expectedResult, PropertyBook.getCustomProperties());

        testBook.removeCustomProperty(VALID_FULL_NAME_END_DATE);
        assertEquals(new HashSet<>(), PropertyBook.getCustomProperties());
    }

    @Test
    public void isPrefixUsed_isFullNameUsed() {
        // Prefix and full name used by a custom property -> true
        testBook.addCustomProperty(REMARK);
        assertTrue(testBook.isPrefixUsed(new Prefix("b/")));
        assertTrue(testBook.isFullNameUsed(VALID_FULL_NAME_REMARK));

        // Prefix and full name used by parameters in add/edit command -> true
        assertTrue(testBook.isPrefixUsed(PREFIX_DATE));
        assertTrue(testBook.isFullNameUsed("Date"));

        // Prefix not used by custom property and parameters in add/edit command -> false
        assertFalse(testBook.isPrefixUsed(P_SLASH));
        assertFalse(testBook.isFullNameUsed("Definitely not used"));
    }

    @Test
    public void equals() {
        PropertyBook anotherBook = new PropertyBook(customPropertySet);
        testBook.addCustomProperties(customPropertySet);
        assertTrue(anotherBook.equals(testBook));
    }

    @Test
    public void hashCodeTest() {
        PropertyBook anotherBook = new PropertyBook(new HashSet<>());
        assertTrue(anotherBook.equals(testBook));
    }
}
