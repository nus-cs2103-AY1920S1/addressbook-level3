package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.AliasTestUtil.ALIAS_ADD_WITH_ARGUMENTS;
import static seedu.address.testutil.AliasTestUtil.ALIAS_A_TO_B;

import org.junit.jupiter.api.Test;


class AliasMappingsTest {

    @Test
    void aliasExists() {
        AliasMappings aliasMappings = new AliasMappings();
        aliasMappings.addAlias(ALIAS_A_TO_B);
        aliasMappings.aliasExists(ALIAS_A_TO_B.getAliasName());
    }

    @Test
    void aliasNameIsReserved() {
        AliasMappings aliasMappings = new AliasMappings();

        assertTrue(aliasMappings.aliasNameIsReserved("add"));
        assertTrue(aliasMappings.aliasNameIsReserved("alias"));
        assertTrue(aliasMappings.aliasNameIsReserved("delete"));
        assertTrue(aliasMappings.aliasNameIsReserved("edit"));
        assertTrue(aliasMappings.aliasNameIsReserved("exit"));
        assertTrue(aliasMappings.aliasNameIsReserved("find"));
        assertTrue(aliasMappings.aliasNameIsReserved("help"));
        assertTrue(aliasMappings.aliasNameIsReserved("list"));
        assertFalse(aliasMappings.aliasNameIsReserved("somethingelse"));
    }

    @Test
    void aliasCommandWordIsAlias() {
        // returns true after alias with that name is added, false before
        assertFalse(new AliasMappings().aliasCommandWordIsAlias("a"));
        assertTrue(
                new AliasMappings().addAlias(ALIAS_A_TO_B)
                        .aliasCommandWordIsAlias(ALIAS_A_TO_B.getAliasName()));
    }

    @Test
    void testEquals() {
        AliasMappings empty = new AliasMappings();
        AliasMappings empty2 = new AliasMappings();
        AliasMappings oneAlias = empty.addAlias(ALIAS_A_TO_B);
        AliasMappings oneAlias2 = empty2.addAlias(ALIAS_A_TO_B);
        AliasMappings oneAlias3 = empty.addAlias(new Alias("a", "b"));
        AliasMappings oneAlias4 = new AliasMappings().addAlias(ALIAS_ADD_WITH_ARGUMENTS);
        // different empty -> true
        assertEquals(empty, empty2);
        // different empty add same -> true
        assertEquals(oneAlias, oneAlias2);
        // same empty add similar -> true
        assertEquals(oneAlias, oneAlias3);
        // different alias inside -> false
        assertNotEquals(empty, oneAlias);
        assertNotEquals(oneAlias, oneAlias4);
    }
}
