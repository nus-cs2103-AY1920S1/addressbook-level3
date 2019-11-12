package seedu.address.model.password.analyser.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.exceptions.DictionaryNotFoundException;

class DictionaryTest {

    @Test
    public void constructor_nullDictionary_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dictionary("dummy", null));
    }

    @Test
    public void constructor_invalidFileName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dictionary(null, new HashMap<>()));
    }

    @Test
    public void load_invalidDictionaryName_throwsDictionaryException() {
        assertThrows(DictionaryNotFoundException.class, () -> Dictionary.load("dummy.txt"));
    }

    @Test
    public void build_invalidDictionary_throwsDictionaryException() {
        assertThrows(DictionaryNotFoundException.class, () -> Dictionary.build("dummy.txt"));
    }

    @Test
    public void build_validDictionary() {
        HashMap<String, Integer> test = new HashMap<>();
        test.put("password", 2);
        test.put("29tgl03", null);
        test.put("123123", 11);

        try {
            for (Map.Entry<String, Integer> entry : test.entrySet()) {
                String value = entry.getKey();
                Integer expected = entry.getValue();
                Integer computed = Dictionary.build("passwords.txt").getRank(value);
                assertEquals(expected, computed);
            }
        } catch (DictionaryNotFoundException e) {
            System.out.println("Should not happen");
            e.printStackTrace();
        }
    }

}
