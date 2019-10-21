package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Contains utility methods for populating {@code ProjectDashboard} with sample data.
 */
public class SampleTaskDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Review Budget"), TaskStatus.UNBEGUN, getTagSet("Finance")),
            new Task(new Name("Increase Funding"), TaskStatus.DOING, getTagSet("Finance", "Urgent")),
            new Task(new Name("Settle Claims"), TaskStatus.DOING, getTagSet("Finance")),
            new Task(new Name("Update Website"), TaskStatus.DONE, getTagSet("Branding")),
            new Task(new Name("Shirts for Freshman Open Day"), TaskStatus.DOING, getTagSet("Logistics")),
            new Task(new Name("Design Poster"), TaskStatus.UNBEGUN, getTagSet("Branding"))
        };
    }

    public static Member[] getSampleMembers() {
        return new Member[] {
                new Member(new MemberName("Gabriel Seow"), new MemberId("GS"), getTagSet("Programmer")),
                new Member(new MemberName("Abhinav"), new MemberId("AB"), getTagSet("UIDesigner")),
                new Member(new MemberName("Arun"), new MemberId("AR"), getTagSet("Programmer")),
                new Member(new MemberName("Seah Lynn"), new MemberId("SL"), getTagSet("Pitcher")),
                new Member(new MemberName("Elsa Koh"), new MemberId("EK"), getTagSet("Pitcher")),
                new Member(new MemberName("John Doe"), new MemberId("JD"), getTagSet("Helper"))
        };
    }

    public static Inventory[] getSampleInventory() {
        return new Inventory[] {
                new Inventory(new InvName("Toy"), new Price(8.90)),
                new Inventory(new InvName("Bench"), new Price(59.90)),
                new Inventory(new InvName("Chairs")),
                new Inventory(new InvName("Bag"), new Price(50.0)),
                new Inventory(new InvName("Condoms"), new Price(17.90)),
                new Inventory(new InvName("Laptop"), new Price(1111.11)),
        };
    }

    public static ReadOnlyProjectDashboard getSampleProjectDashboard() {
        ProjectDashboard sampleAb = new ProjectDashboard();
        for (Task sampleTask : getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        for (Member sampleMember : getSampleMembers()) {
            sampleAb.addMember(sampleMember);
        }
        for (Inventory sampleInventory : getSampleInventory()) {
            sampleAb.addInventory(sampleInventory);
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
