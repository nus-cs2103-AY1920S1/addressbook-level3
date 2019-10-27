package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.jarvis.testutil.cca.TypicalCcas.CANOEING;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.Cca;

/**
 * Tests the behaviour of {@code JsonAdaptedCca}.
 */
public class JsonAdaptedCcaTest {
    private static final Cca VALID_CCA = CANOEING;

    @Test
    public void toModelType_returnsCca() throws Exception {
        JsonAdaptedCca jsonAdaptedCca = new JsonAdaptedCca(VALID_CCA);
        assertEquals(VALID_CCA, jsonAdaptedCca.toModelType());
    }
}
