package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AutoCompleterTest extends AutoCompleter {

    @Test
    public void correctnessTest() {
        assertEquals(this.update("a").getSuggestions().size(), 3);
        assertEquals(this.update("ca").getSuggestions().size(), 1);
        assertEquals(this.update("i").getSuggestions().size(), 0);
        assertEquals(this.update("o").getSuggestions().size(), 2);
    }
}
