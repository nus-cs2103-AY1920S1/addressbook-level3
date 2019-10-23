package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author ambervoong
class ReligionTest {

    @Test
    void enumerateReligion_indexOne_correct() {
        assertEquals(Religion.ISLAM.toString(), "ISLAM");
    }

    @Test
    void enumerateReligion_indexOne_wrong() {
        assertNotEquals(Religion.ISLAM.toString(), "12345");
    }

    @Test
    void enumerateReligion_parseReligion_success() throws ParseException {
        assertEquals(ParserUtil.parseReligion(Religion.ISLAM.toString()), Religion.ISLAM);
    }


}
