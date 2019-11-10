package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchKeyTest {
    private SearchKey searchKey = SearchKey.KEY_ACTOR;
    @Test
    void getKey() {
        assertEquals(searchKey.getKey(), "actor");
    }
}
