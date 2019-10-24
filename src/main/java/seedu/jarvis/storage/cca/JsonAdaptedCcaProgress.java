package seedu.jarvis.storage.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;

/**
 * Jackson-friendly version of {@link CcaProgress}.
 */
public class JsonAdaptedCcaProgress {
    private final List<JsonAdaptedCcaMilestone> ccaProgressList = new ArrayList<>();
    private final JsonAdaptedCcaCurrentProgress ccaCurrentProgress;

    @JsonCreator
    public JsonAdaptedCcaProgress(@JsonProperty("ccaProgressList") List<JsonAdaptedCcaMilestone> ccaProgressList,
                                  @JsonProperty("ccaCurrentProgress") JsonAdaptedCcaCurrentProgress
                                          ccaCurrentProgress) {
        if (ccaProgressList != null) {
            this.ccaProgressList.addAll(ccaProgressList);
        }
        this.ccaCurrentProgress = ccaCurrentProgress;
    }

    public JsonAdaptedCcaProgress(CcaProgress ccaProgress) {
        ccaProgressList.addAll(ccaProgress.getCcaProgressList()
                .asUnmodifiableObservableList()
                .stream()
                .map(JsonAdaptedCcaMilestone::new)
                .collect(Collectors.toList()));
        ccaCurrentProgress = new JsonAdaptedCcaCurrentProgress(ccaProgress.getCcaCurrentProgress());
    }

    /**
     * Converts this Jackson-friendly adapted {@code CcaProgress} object into the model's {@code CcaProgress} object.
     *
     * @return {@code CcaProgress} object.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code CcaProgress}.
     */
    public CcaProgress toModelType() throws IllegalValueException {
        CcaProgress ccaProgress = new CcaProgress();
        List<CcaMilestone> milestones = new ArrayList<>();
        for (JsonAdaptedCcaMilestone jsonAdaptedCcaMilestone : ccaProgressList) {
            milestones.add(jsonAdaptedCcaMilestone.toModelType());
        }
        ccaProgress.setMilestones(milestones);
        ccaProgress.setCcaCurrentProgress(ccaCurrentProgress.toModelType());
        return ccaProgress;
    }
}
