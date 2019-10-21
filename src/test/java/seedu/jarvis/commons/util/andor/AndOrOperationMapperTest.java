package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.commons.util.andor.AndOrOperationMapper.KEYWORD_AND_NODE;
import static seedu.jarvis.commons.util.andor.AndOrOperationMapper.KEYWORD_OR_NODE;

import org.junit.jupiter.api.Test;

/**
 * @author ryanYtan
 */
public class AndOrOperationMapperTest {
    @Test
    public void resolveType_andOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.AND, AndOrOperationMapper.ofType(KEYWORD_AND_NODE));
        assertEquals(AndOrOperation.OR, AndOrOperationMapper.ofType(KEYWORD_OR_NODE));
    }

    @Test
    public void resolveType_nonAndOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("bleh"));
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("orr"));
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("andd"));
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("andor"));
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("CS1231"));
        assertEquals(AndOrOperation.DATA, AndOrOperationMapper.ofType("asdf"));
    }
}
