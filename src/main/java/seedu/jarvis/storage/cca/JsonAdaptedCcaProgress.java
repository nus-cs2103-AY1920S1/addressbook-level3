package seedu.jarvis.storage.cca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.storage.JsonAdapter;

/**
 * Jackson-friendly version of {@link CcaProgress}.
 */
public class JsonAdaptedCcaProgress implements JsonAdapter<CcaProgress> {
    private final List<JsonAdaptedCcaMilestone> ccaProgressList = new ArrayList<>();
    private final JsonAdaptedCcaCurrentProgress ccaCurrentProgress;

    /**
     * Constructs a {@code JsonAdaptedCcaProgress} with the given cca progress details.
     *
     * @param ccaProgressList {@code List} of {@code JsonAdaptedCcaMilestone} objects.
     * @param ccaCurrentProgress {@code CcaCurrentProgress} in json format.
     */
    @JsonCreator
    public JsonAdaptedCcaProgress(@JsonProperty("ccaProgressList") List<JsonAdaptedCcaMilestone> ccaProgressList,
                                  @JsonProperty("ccaCurrentProgress") JsonAdaptedCcaCurrentProgress
                                          ccaCurrentProgress) {
        if (ccaProgressList != null) {
            this.ccaProgressList.addAll(ccaProgressList);
        }
        this.ccaCurrentProgress = ccaCurrentProgress;
    }

    /**
     * Converts a given {@code CcaProgress} into this class for Jackson use.
     *
     * @param ccaProgress {@code CcaProgress} to be used to construct the {@code JsonAdaptedCcaProgress}.
     */
    public JsonAdaptedCcaProgress(CcaProgress ccaProgress) {
        ccaProgressList.addAll(ccaProgress.getCcaMilestoneList()
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
    @Override
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
