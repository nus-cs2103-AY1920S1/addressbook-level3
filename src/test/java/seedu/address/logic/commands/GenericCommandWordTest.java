package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class GenericCommandWordTest {

    @Test
    void isGeneric_genericCommandWord_true() {
        assertTrue(GenericCommandWord.isGeneric(GenericCommandWord.ADD));
        assertTrue(GenericCommandWord.isGeneric(GenericCommandWord.CLEAR));
        assertTrue(GenericCommandWord.isGeneric(GenericCommandWord.DELETE));
        assertTrue(GenericCommandWord.isGeneric(GenericCommandWord.EDIT));
        assertTrue(GenericCommandWord.isGeneric(GenericCommandWord.LIST));
    }

    @Test
    void isGeneric_nonGenericCommandWord_true() {
        Stream.of(CommandGroup.EXPENSE, CommandGroup.PRIMARY_BUDGET, CommandGroup.BUDGET,
                CommandGroup.EVENT, CommandGroup.STATISTIC, CommandGroup.ALIAS)
                .forEach( x -> {
                    assertFalse(GenericCommandWord.isGeneric(GenericCommandWord.ADD + x));
                    assertFalse(GenericCommandWord.isGeneric(GenericCommandWord.CLEAR + x));
                    assertFalse(GenericCommandWord.isGeneric(GenericCommandWord.DELETE + x));
                    assertFalse(GenericCommandWord.isGeneric(GenericCommandWord.EDIT + x));
                    assertFalse(GenericCommandWord.isGeneric(GenericCommandWord.LIST + x));
                });
    }
}