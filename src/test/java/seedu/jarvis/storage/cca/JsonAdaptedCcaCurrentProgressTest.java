package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;

/**
 * Tests the behaviour of {@code JsonAdaptedCcaCurrentProgress}.
 */
public class JsonAdaptedCcaCurrentProgressTest {

    @Test
    public void toModelType_returnsCcaCurrentProgress() throws Exception {
        int maxProgress = 100;
        int currentProgress = 50;
        CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();
        ccaCurrentProgress.setMaxProgress(maxProgress);
        ccaCurrentProgress.setCurrentProgress(currentProgress);
        JsonAdaptedCcaCurrentProgress jsonAdaptedCcaCurrentProgress = new JsonAdaptedCcaCurrentProgress(
                ccaCurrentProgress);
        assertEquals(ccaCurrentProgress, jsonAdaptedCcaCurrentProgress.toModelType());
    }
}
