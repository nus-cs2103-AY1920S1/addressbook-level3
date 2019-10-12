package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author ryanYtan
 */
public class AndOrOperationMapperUtilTest {
    @Test
    public void resolveType_andOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.AND, AndOrOperationMapperUtil.resolveType("and"));
        assertEquals(AndOrOperation.OR, AndOrOperationMapperUtil.resolveType("or"));
    }

    @Test
    public void resolveType_nonAndOrString_returnsAndOrString() {
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType(null));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("bleh"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("orr"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("andd"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("andor"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("CS1231"));
        assertEquals(AndOrOperation.LEAF, AndOrOperationMapperUtil.resolveType("asdf"));
    }
}
