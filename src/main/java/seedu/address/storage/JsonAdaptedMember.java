package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedMember {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Member's %s field is missing!";

    private final String name;
    private final MemberId memberId;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedMember(@JsonProperty("name") String name, @JsonProperty("id") MemberId memberId,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.memberId = memberId;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedMember(Member source) {
        name = source.getName().fullName;
        memberId = source.getId();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Member toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MemberName.class.getSimpleName()));
        }
        if (!MemberName.isValidName(name)) {
            throw new IllegalValueException(MemberName.MESSAGE_CONSTRAINTS);
        }
        final MemberName modelName = new MemberName(name);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Member(modelName, memberId, modelTags);
    }

}
