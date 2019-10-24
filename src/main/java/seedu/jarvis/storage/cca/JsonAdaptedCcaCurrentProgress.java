package seedu.jarvis.storage.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;

/**
 * Jackson-friendly version of {@link CcaCurrentProgress}.
 */
public class JsonAdaptedCcaCurrentProgress {
    private final int maxProgress;
    private final int currentProgress;

    /**
     * Constructs a {@code JsonAdaptedCcaCurrentProgress} with the given {@code CcaCurrentProgress}.
     */
    @JsonCreator
    public JsonAdaptedCcaCurrentProgress(@JsonProperty("maxProgress") int maxProgress,
                                         @JsonProperty("currentProgress") int currentProgress) {
        this.maxProgress = maxProgress;
        this.currentProgress = currentProgress;
    }

    public JsonAdaptedCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
        maxProgress = ccaCurrentProgress.getMaxProgress();
        currentProgress = ccaCurrentProgress.getCurrentProgress();
    }

    /**
     * Converts this Jackson-friendly adapted {@code CcaCurrentProgress} object into the model's
     * {@code CcaCurrentProgress} object.
     *
     * @return {@code CcaCurrentProgress} of the Jackson-friendly adapted {@code CcaCurrentProgress}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted
     * {@code CcaCurrentProgress}.
     */
    public CcaCurrentProgress toModelType() throws IllegalValueException {
        CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();
        ccaCurrentProgress.setMaxProgress(maxProgress);
        ccaCurrentProgress.setCurrentProgress(currentProgress);
        return ccaCurrentProgress;
    }
}
