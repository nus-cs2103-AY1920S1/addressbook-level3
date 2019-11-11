package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BrandTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Brand(null));
    }

    @Test
    public void constructor_invalidBrand_throwsIllegalArgumentException() {
        String invalidBrand = "";
        assertThrows(IllegalArgumentException.class, () -> new Brand(invalidBrand));
    }

    @Test
    public void isValidBrand() {
        // null brand
        assertThrows(NullPointerException.class, () -> Brand.isValidBrand(null));

        // invalid brand
        assertFalse(Brand.isValidBrand("")); // empty string
        assertFalse(Brand.isValidBrand(" ")); // spaces only

        // valid brand
        assertTrue(Brand.isValidBrand("orange juice")); // alphabets only
        assertTrue(Brand.isValidBrand("8888")); // numbers only
        assertTrue(Brand.isValidBrand("forever 21")); // alphanumeric characters
        assertTrue(Brand.isValidBrand("New Balance")); // with capital letters
        assertTrue(Brand.isValidBrand("Xiaomi Futuristic Ultra 3000 Foldable Dual Screen")); // long names
        assertTrue(Brand.isValidBrand("*Victoria's Secret*")); // with symbols
    }
}
