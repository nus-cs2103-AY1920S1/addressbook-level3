package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AndOrOperationMapperTest {
    @Test
    public void resolveType_andOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.AND, AndOrOperationMapper.resolveType("and"));
        assertEquals(AndOrOperation.OR, AndOrOperationMapper.resolveType("or"));
    }

    @Test
    public void resolveType_nonAndOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType(null));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("bleh"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("orr"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("andd"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("andor"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("CS1231"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapper.resolveType("asdf"));
    }
}
