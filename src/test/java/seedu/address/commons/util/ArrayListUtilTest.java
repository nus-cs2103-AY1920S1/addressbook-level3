package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ArrayListUtilTest {

    @Test
    void toStringCommaSpaced() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Foo", "Bar", "Foo"));
        assertEquals(ArrayListUtil.toStringCommaSpaced(list), "Foo, Bar, Foo");
    }
}
