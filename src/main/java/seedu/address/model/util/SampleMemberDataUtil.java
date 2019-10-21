package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;

/**
 * provides a sample member data
 */
public class SampleMemberDataUtil {
    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new MemberName("Gabriel Seow"), new MemberId("GS"), getTagSet("Cheif Programmer")),
            new Member(new MemberName("Abhinav"), new MemberId("AB"), getTagSet("Programmer, UI Designer")),
            new Member(new MemberName("Arun"), new MemberId("AR"), getTagSet("Programmer")),
            new Member(new MemberName("Seah Lynn"), new MemberId("SL"), getTagSet("Programmer, Pitcher")),
            new Member(new MemberName("Elsa Koh"), new MemberId("EK"), getTagSet("Programmer, Pitcher")),
            new Member(new MemberName("John Doe"), new MemberId("JD"), getTagSet("Helps out when he cans"))
        };
    }

    public static ReadOnlyProjectDashboard getSampleProjectDashboard() {
        ProjectDashboard sampleAb = new ProjectDashboard();
        for (Member sampleMember : getSampleMembers()) {
            sampleAb.addMember(sampleMember);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
