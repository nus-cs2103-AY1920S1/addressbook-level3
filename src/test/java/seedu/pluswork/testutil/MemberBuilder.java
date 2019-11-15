package seedu.pluswork.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.util.SampleMemberDataUtil;

/**
 * A utility class to help with building {@code <Member>} objects.
 */
public class MemberBuilder {
    public static final String DEFAULT_NAME = "Sample Member Name";
    public static final String DEFAULT_ID = "Sample Member ID";

    private MemberName name;
    private MemberId id;
    private Set<Tag> tags;

    public MemberBuilder() {
        name = new MemberName(DEFAULT_NAME);
        id = new MemberId(DEFAULT_ID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MemberBuilder(Member memberToCopy) {
        name = memberToCopy.getName();
        id = memberToCopy.getId();
        tags = new HashSet<>(memberToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public MemberBuilder withName(String name) {
        this.name = new MemberName(name);
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public MemberBuilder withId(MemberId memberId) {
        this.id = memberId;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public MemberBuilder withTags(String... tags) {
        this.tags = SampleMemberDataUtil.getTagSet(tags);
        return this;
    }

    public Member build() {
        return new Member(name, id, tags);
    }
}
