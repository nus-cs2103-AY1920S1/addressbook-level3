package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;

/**
 * Tests the behaviour of {@code JsonAdaptedCcaProgress}.
 */
public class JsonAdaptedCcaProgressTest {
    private static final CcaMilestone MILESTONE_1 = new CcaMilestone("1");
    private static final CcaMilestone MILESTONE_2 = new CcaMilestone("2");
    private static final CcaMilestone MILESTONE_3 = new CcaMilestone("3");

    @Test
    public void toModelType_returnsCcaProgress() throws Exception {
        CcaProgress ccaProgress = new CcaProgress();
        ccaProgress.setMilestones(Arrays.asList(MILESTONE_1, MILESTONE_2, MILESTONE_3));
        CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();
        ccaCurrentProgress.setMaxProgress(100);
        ccaCurrentProgress.setCurrentProgress(50);
        ccaProgress.setCcaCurrentProgress(ccaCurrentProgress);
        JsonAdaptedCcaProgress jsonAdaptedCcaProgress = new JsonAdaptedCcaProgress(ccaProgress);
        assertEquals(ccaProgress, jsonAdaptedCcaProgress.toModelType());
    }
}
