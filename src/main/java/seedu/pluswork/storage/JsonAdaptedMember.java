package seedu.pluswork.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pluswork.commons.exceptions.IllegalValueException;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Member}.
 */
class JsonAdaptedMember {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Member's %s field is missing!";

    private final String name;
    //private final MemberId id;
    private final String id;

    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String memberImage;

    /**
     * Constructs a {@code JsonAdaptedMember} with the given member details.
     */
    @JsonCreator
    public JsonAdaptedMember(@JsonProperty("name") String name, @JsonProperty("id") String id,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("image") String memberImage) {
        this.name = name;
        this.id = id;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.memberImage = memberImage;
    }

    /**
     * Converts a given {@code Mmeber} into this class for Jackson use.
     */
    public JsonAdaptedMember(Member source) {
        name = source.getName().fullName;
        id = source.getId().getDisplayId();
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        memberImage = source.getImagePath();
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
        if (!MemberName.isValidMemberName(name.toString())) {
            throw new IllegalValueException(MemberName.MESSAGE_CONSTRAINTS);
        }

        final MemberName modelName = new MemberName(name);
        final MemberId modelId = new MemberId(id);
        final Set<Tag> modelTags = new HashSet<>(memberTags);
        final String modelImage = memberImage;
        return new Member(modelName, modelId, modelTags, modelImage);
    }
}
