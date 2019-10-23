package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.AliasTestUtil.ALIAS_ADD_WITH_ARGUMENTS;
import static seedu.address.testutil.AliasTestUtil.ALIAS_A_TO_B;
import static seedu.address.testutil.AliasTestUtil.ALIAS_B_TO_C;
import static seedu.address.testutil.AliasTestUtil.ALIAS_C_TO_A;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_ADD;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_ALIAS;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_CLEAR;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_DELETE;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_EDIT;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_EXIT;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_FIND;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_HELP;
import static seedu.address.testutil.AliasTestUtil.ALIAS_NAME_LIST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.RecursiveAliasException;


class AliasMappingsTest {

    @Test
    void aliasExists() {
        AliasMappings aliasMappings = new AliasMappings();
        try {
            aliasMappings.addAlias(ALIAS_A_TO_B);
        } catch (RecursiveAliasException e) {
            throw new AssertionError("RecursiveAliasException should not be thrown");
        }
        aliasMappings.aliasWithNameExists(ALIAS_A_TO_B.getAliasName());
    }

    @Test
    void aliasUsesReservedName_aliasUsesReservedName_returnsTrue() {
        AliasMappings aliasMappings = new AliasMappings();

        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_ADD));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_ALIAS));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_CLEAR));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_DELETE));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_EDIT));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_EXIT));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_FIND));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_HELP));
        assertTrue(aliasMappings.aliasUsesReservedName(ALIAS_NAME_LIST));
    }

    @Test
    void aliasUsesReservedName_aliasDoesNotUseReservedName_returnsFalse() {
        AliasMappings aliasMappings = new AliasMappings();
        assertFalse(aliasMappings.aliasUsesReservedName(new Alias("somethingelse", "ignored")));
        assertFalse(aliasMappings.aliasUsesReservedName(new Alias("anotherNotCommand", "ignored")));
    }

    @Test
    void aliasCommandWordIsAlias_aliasCommandWordIsNotAliasNameOfExistingAlias_returnsFalse() {
        // returns true after alias with that name is added, false before
        AliasMappings aliasMappings = new AliasMappings();
        assertFalse(aliasMappings.aliasCommandWordIsAlias(ALIAS_A_TO_B));
        assertFalse(aliasMappings.aliasCommandWordIsAlias(ALIAS_B_TO_C));
        assertFalse(aliasMappings.aliasCommandWordIsAlias(ALIAS_C_TO_A));
    }

    @Test
    void aliasCommandWordIsAlias_aliasCommandWordIsAliasNameOfExistingAlias_returnsTrue() {
        // returns true after alias with that name is added, false before
        try {
            AliasMappings aliasMappings = new AliasMappings();
            aliasMappings = aliasMappings.addAlias(ALIAS_A_TO_B);
            assertTrue(aliasMappings.aliasCommandWordIsAlias(ALIAS_C_TO_A));
        } catch (RecursiveAliasException e) {
            throw new AssertionError("RecursiveAliasException should not be thrown");
        }
        try {
            AliasMappings aliasMappings = new AliasMappings();
            aliasMappings = aliasMappings.addAlias(ALIAS_B_TO_C);
            assertTrue(aliasMappings.aliasCommandWordIsAlias(ALIAS_A_TO_B));
        } catch (RecursiveAliasException e) {
            throw new AssertionError("RecursiveAliasException should not be thrown");
        }
    }

    @Test
    void addAlias_aliasCommandWordIsAlias_throwRecursiveAliasException() {
        // right after
        assertThrows(RecursiveAliasException.class, () -> {
            new AliasMappings().addAlias(ALIAS_A_TO_B).addAlias(ALIAS_C_TO_A);
        });
        assertThrows(RecursiveAliasException.class, () -> {
            new AliasMappings().addAlias(ALIAS_C_TO_A).addAlias(ALIAS_B_TO_C);
        });
        assertThrows(RecursiveAliasException.class, () -> {
            new AliasMappings().addAlias(ALIAS_B_TO_C).addAlias(ALIAS_A_TO_B);
        });

        // with other aliases in between
        assertThrows(RecursiveAliasException.class, () -> {
            new AliasMappings().addAlias(ALIAS_A_TO_B).addAlias(ALIAS_B_TO_C).addAlias(ALIAS_C_TO_A);
        });
    }

    @Test
    void testEquals() {
        try {
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
        } catch (RecursiveAliasException e) {
            throw new AssertionError("RecursiveAliasException should not be thrown");
        }
    }
}
