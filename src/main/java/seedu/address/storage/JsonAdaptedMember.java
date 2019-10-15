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

public class JsonAdaptedMember {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Member's %s field is missing!";

    private final MemberName name;
    private final MemberId id;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedMember(@JsonProperty("name") MemberName name, @JsonProperty("id") MemberId id,
                           @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.id = id;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Mmeber} into this class for Jackson use.
     */
    public JsonAdaptedMember(Member source) {
        name = source.getName();
        id = source.getId();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Member} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task.
     */
    public Member toModelType() throws IllegalValueException {
        final List<Tag> memberTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            memberTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, MemberName.class.getSimpleName()));
        }
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MemberId.class.getSimpleName()));
        }
        if (!MemberName.isValidName(name.toString())) {
            throw new IllegalValueException(MemberName.MESSAGE_CONSTRAINTS);
        }
        final MemberName modelName = new MemberName(name.toString());
        final MemberId modelId = new MemberId(id.getDisplayName());
        final Set<Tag> modelTags = new HashSet<>(memberTags);
        return new Member(modelName, modelId, modelTags);
    }

}
