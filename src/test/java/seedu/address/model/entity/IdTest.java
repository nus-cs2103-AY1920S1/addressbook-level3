package seedu.address.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IdTest {
    @Test
    public void equals_sameId_returnsTrue() {
        assertEquals(new Id(PrefixType.T, 1), new Id(PrefixType.T, 1));
    }
}
