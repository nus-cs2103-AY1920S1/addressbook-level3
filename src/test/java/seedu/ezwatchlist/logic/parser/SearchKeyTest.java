package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchKeyTest {
    private SearchKey searchKeyName = SearchKey.KEY_NAME;
    private SearchKey searchKeyType = SearchKey.KEY_TYPE;
    private SearchKey searchKeyActor = SearchKey.KEY_ACTOR;
    private SearchKey searchKeyGenre = SearchKey.KEY_GENRE;
    private SearchKey searchKeyIsWatched = SearchKey.KEY_IS_WATCHED;
    private SearchKey searchKeyFromOnline = SearchKey.KEY_FROM_ONLINE;

    @Test
    public void getKey() {
        assertEquals(searchKeyName.getKey(), "name");
        assertEquals(searchKeyType.getKey(), "type");
        assertEquals(searchKeyActor.getKey(), "actor");
        assertEquals(searchKeyGenre.getKey(), "genre");
        assertEquals(searchKeyIsWatched.getKey(), "is_watched");
        assertEquals(searchKeyFromOnline.getKey(), "from_online");
    }

}
