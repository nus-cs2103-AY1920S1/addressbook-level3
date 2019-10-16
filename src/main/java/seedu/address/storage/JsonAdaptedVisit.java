package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.visit.EndDateTime;
import seedu.address.model.visit.Remark;
import seedu.address.model.visit.StartDateTime;
import seedu.address.model.visit.Visit;
import seedu.address.model.visittask.VisitTask;

/**
 * Jackson-friendly version of {@link Visit}.
 */
class JsonAdaptedVisit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Visit's %s field is missing!";
    public static final String END_DATE_EARLIER_THAN_START_DATE = "Visit's start date is earlier "
            + "than its end date";

    private final String remark;
    private final String startDateTime;
    private final String endDateTime;
    private final List<JsonAdaptedVisitTask> visitTasks = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedVisit} with the given visit details.
     */
    @JsonCreator
    public JsonAdaptedVisit(@JsonProperty("remark") String remark,
                            @JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime,
                            @JsonProperty("visitTasks") List<JsonAdaptedVisitTask> visitTasks) {
        this.remark = remark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        if (visitTasks != null) {
            this.visitTasks.addAll(visitTasks);
        }
    }

    /**
     * Converts a given {@code Visit} into this class for Jackson use.
     */
    public JsonAdaptedVisit(Visit source) {
        remark = source.getRemark().remark;
        startDateTime = source.getStartDateTime().toJacksonJsonString();
        Optional<EndDateTime> endDateTime = source.getEndDateTime();
        if (endDateTime.isPresent()) {
            this.endDateTime = endDateTime.get().toJacksonJsonString();
        } else {
            this.endDateTime = null;
        }
        visitTasks.addAll(source.getVisitTasks().stream()
                .map(JsonAdaptedVisitTask::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted visit object into the model's {@code Visit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted visit.
     */
    public Visit toModelType() throws IllegalValueException {
        final List<VisitTask> modelVisitTasks = new ArrayList<>();
        for (JsonAdaptedVisitTask visitTask : visitTasks) {
            modelVisitTasks.add(visitTask.toModelType());
        }

        final Remark modelRemark = new Remark(remark);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StartDateTime.class.getSimpleName()));
        }
        if (!StartDateTime.isValidStartDateTime(startDateTime)) {
            throw new IllegalValueException(StartDateTime.MESSAGE_CONSTRAINTS);
        }
        final StartDateTime modelStartDateTime = new StartDateTime(startDateTime);

        final EndDateTime modelEndDateTime;
        if (endDateTime == null) {
            modelEndDateTime = null;
        } else {
            if (!EndDateTime.isValidEndDateTime(endDateTime)) {
                throw new IllegalValueException(EndDateTime.MESSAGE_CONSTRAINTS);
            }
            modelEndDateTime = new EndDateTime(endDateTime);

            //Other constraints e.g. EndDateTime cannot be earlier than startDateTime
            if (modelEndDateTime.dateTime.before(modelStartDateTime.dateTime)) {
                throw new IllegalValueException(END_DATE_EARLIER_THAN_START_DATE);
            }
        }

        return new Visit(modelRemark, modelStartDateTime, modelEndDateTime, modelVisitTasks);
    }

}
