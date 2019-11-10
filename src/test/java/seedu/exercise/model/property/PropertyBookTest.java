package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.exercise.testutil.CommonTestData.P_SLASH;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_END_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_RATING;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.END_DATE;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.REMARK;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.property.custom.CustomProperty;
import seedu.exercise.testutil.builder.CustomPropertyBuilder;

//@@author weihaw08
public class PropertyBookTest {

    private final Set<CustomProperty> customPropertySet = Set.of(RATING);
    private PropertyBook testBook = PropertyBook.getInstance();

    @BeforeEach
    public void setUp() {
        testBook.clearCustomProperties();
    }


    @Test
    public void getCustomProperties() {
        testBook.addCustomProperties(customPropertySet);
        assertEquals(customPropertySet, testBook.getCustomProperties());
    }

    @Test
    public void addCustomProperties() {
        Set<CustomProperty> anotherCustomPropertySet = Set.of(RATING, REMARK);
        testBook.addCustomProperties(anotherCustomPropertySet);
        assertEquals(anotherCustomPropertySet, testBook.getCustomProperties());
    }

    @Test
    public void clearCustomProperties() {
        testBook.clearCustomProperties();
        assertTrue(testBook.getCustomProperties().isEmpty());
    }

    @Test
    public void addCustomProperty_removeCustomProperty() {
        testBook.addCustomProperty(END_DATE);
        Set<CustomProperty> expectedResult = Set.of(END_DATE);
        assertEquals(expectedResult, testBook.getCustomProperties());

        testBook.removeCustomProperty(VALID_FULL_NAME_END_DATE);
        assertEquals(new HashSet<>(), testBook.getCustomProperties());
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
    public void isFullNameUsedByCustomProperty() {
        testBook.addCustomProperty(REMARK);
        assertTrue(testBook.isFullNameUsedByCustomProperty(VALID_FULL_NAME_REMARK));

        assertFalse(testBook.isFullNameUsedByCustomProperty("Date"));
    }

    @Test
    public void hasClashingPrefixOrName() {
        testBook.addCustomProperty(REMARK);
        // Same name and different prefix -> true
        CustomProperty sameFullName = new CustomPropertyBuilder(RATING)
            .withFullName(VALID_FULL_NAME_REMARK).build();
        assertTrue(testBook.hasClashingPrefixOrName(sameFullName));

        // Different name and same prefix -> true
        CustomProperty samePrefix = new CustomPropertyBuilder(REMARK)
            .withFullName(VALID_FULL_NAME_RATING).build();
        assertTrue(testBook.hasClashingPrefixOrName(samePrefix));

        // Different name and prefix -> false
        assertFalse(testBook.hasClashingPrefixOrName(END_DATE));
    }
}
