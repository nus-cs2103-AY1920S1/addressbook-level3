package seedu.pluswork.storage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pluswork.commons.exceptions.IllegalValueException;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.member.MemberName;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String startTime;
    private final String duration;
    private final List<String> memberNameList = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("startTime") String startTime,
                              @JsonProperty("duration") String duration,
                              @JsonProperty("memberNameList") List<String> memberNameList) {
        this.startTime = startTime;
        this.duration = duration;
        if (memberNameList != null) {
            this.memberNameList.addAll(memberNameList);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        startTime = source.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME);
        duration = source.getDuration().toString();
        memberNameList.addAll(source.getMemberNameList().stream()
                .map(memberName -> memberName.fullName)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Meeting toModelType() throws IllegalValueException {
        final List<MemberName> memberNameList = new ArrayList<>();
        for (String name : this.memberNameList) {
            if (name == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        MemberName.class.getSimpleName()));
            }
            if (!MemberName.isValidMemberName(name)) {
                throw new IllegalValueException(MemberName.MESSAGE_CONSTRAINTS);
            }
            memberNameList.add(new MemberName(name));
        }

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        final LocalDateTime modelStartTime = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_DATE_TIME);
        final Duration modelDuration = Duration.parse(duration);
        final Meeting modelMeeting = new Meeting(modelStartTime, modelDuration, memberNameList);
        return modelMeeting;
    }

}
