package seedu.jarvis.storage.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link CcaCurrentProgress}.
 */
public class JsonAdaptedCcaCurrentProgress implements JsonAdapter<CcaCurrentProgress> {
    private final int currentProgress;

    /**
     * Constructs a {@code JsonAdaptedCcaCurrentProgress} with the given cca current progress details.
     *
     * @param currentProgress Current progress.
     */
    @JsonCreator
    public JsonAdaptedCcaCurrentProgress(@JsonProperty("currentProgress") int currentProgress) {
        this.currentProgress = currentProgress;
    }

    /**
     * Converts a given {@code CcaCurrentProgress} into this class for Jackson use.
     *
     * @param ccaCurrentProgress {@code CcaCurrentProgress} to be used to construct the
     * {@code JsonAdaptedCcaCurrentProgress}.
     */
    public JsonAdaptedCcaCurrentProgress(CcaCurrentProgress ccaCurrentProgress) {
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
    @Override
    public CcaCurrentProgress toModelType() throws IllegalValueException {
        CcaCurrentProgress ccaCurrentProgress = new CcaCurrentProgress();
        ccaCurrentProgress.setCurrentProgress(currentProgress);
        return ccaCurrentProgress;
    }
}
