package seedu.pluswork.model.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.fortuna.ical4j.model.Calendar;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.parser.ParserUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.ReadOnlyProjectDashboard;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;
import seedu.pluswork.model.mapping.TasMemMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Name;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;

/**
 * Contains utility methods for populating {@code ProjectDashboard} with sample data.
 */
public class SampleTaskDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[]{
            new Task(new Name("Review Budget"), TaskStatus.UNBEGUN, getTagSet("Finance"),
                    LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Increase Funding"), TaskStatus.DOING,
                    getTagSet("Finance", "Urgent"), LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Settle Claims"), TaskStatus.DOING, getTagSet("Finance")),
            new Task(new Name("Update Website"), TaskStatus.DONE, getTagSet("Branding"),
                    LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Shirts for Freshman Open Day"), TaskStatus.DOING, getTagSet("Logistics")),
            new Task(new Name("Design Poster"), TaskStatus.UNBEGUN,
                    getTagSet("Branding"), LocalDateTime.now().plusWeeks(7))
        };
    }

    public static Member[] getSampleMembers() {
        return new Member[]{
            new Member(new MemberName("Gabriel Seow"), new MemberId("GS"), getTagSet("Programmer")),
            new Member(new MemberName("Abhinav"), new MemberId("AB"), getTagSet("UIDesigner")),
            new Member(new MemberName("Arun"), new MemberId("AR"), getTagSet("Programmer")),
            new Member(new MemberName("Seah Lynn"), new MemberId("SL"), getTagSet("Pitcher")),
            new Member(new MemberName("Elsa Koh"), new MemberId("EK"), getTagSet("Pitcher")),
            new Member(new MemberName("John Doe"), new MemberId("JD"), getTagSet("Helper"))
        };
    }

    public static Inventory[] getSampleInventory() {
        return new Inventory[]{
            new Inventory(new InvName("Toy"), new Price(8.90)),
            new Inventory(new InvName("Bench"), new Price(59.90)),
            new Inventory(new InvName("Chairs")),
            new Inventory(new InvName("Bag"), new Price(50.0)),
            new Inventory(new InvName("Canola Oil"), new Price(17.90)),
            new Inventory(new InvName("Laptop"), new Price(1111.11)),
        };
    }


    public static InvMemMapping[] getSampleInvMemMapping() {
        return new InvMemMapping[]{
                new InvMemMapping(2, 0),
                new InvMemMapping(2, 1),
                new InvMemMapping(0, 2),
                new InvMemMapping(1, 3),
                new InvMemMapping(2, 4),
                new InvMemMapping(0, 5),
        };
    }

    public static InvTasMapping[] getSampleInvTasMapping() {
        return new InvTasMapping[]{
                new InvTasMapping(5, 0),
                new InvTasMapping(2, 1),
                new InvTasMapping(5, 2),
                new InvTasMapping(5, 3),
                new InvTasMapping(2, 4),
                new InvTasMapping(4, 5),
        };
    }


    public static TasMemMapping[] getSampleTasMemMapping() {
        return new TasMemMapping[]{
                new TasMemMapping(1, 1),
                new TasMemMapping(1, 2),
                new TasMemMapping(2, 1),
                new TasMemMapping(3, 4),
                new TasMemMapping(4, 3),
                new TasMemMapping(5, 5),
        };
    }

    public static Meeting[] getSampleMeeting() {
        try {
            LocalDateTime sampleDateTime1 = DateTimeUtil.parseDateTime("29-11-2019 18:00");
            Duration sampleHours1 = ParserUtil.parseHours("4");
            List<MemberName> sampleMembers1 = Arrays.asList(new MemberName[]{
                    new MemberName("Gabriel"),
                    new MemberName("Abhinav"),
                    new MemberName("Lynn")
            });
            LocalDateTime sampleDateTime2 = DateTimeUtil.parseDateTime("30-11-2019 20:00");
            Duration sampleHours2 = ParserUtil.parseHours("2");
            List<MemberName> sampleMembers2 = Arrays.asList(new MemberName[]{
                    new MemberName("Gabriel"),
                    new MemberName("Abhinav"),
                    new MemberName("Lynn")
            });
            return new Meeting[]{
                    new Meeting(sampleDateTime1, sampleHours1, sampleMembers1),
                    new Meeting(sampleDateTime2, sampleHours2, sampleMembers2)
            };
        } catch (ParseException e) {
            e.printStackTrace();
            return new Meeting[0];
        }
    }

    public static CalendarWrapper[] getSampleCalendar() {
        try {
            Calendar sampleCalendar1 = ParserUtil.parseCalendar(SampleCalendarDataUtil.SAMPLE_CALENDAR_GABRIEL);
            Calendar sampleCalendar2 = ParserUtil.parseCalendar(SampleCalendarDataUtil.SAMPLE_CALENDAR_ABHINAV);
            Calendar sampleCalendar3 = ParserUtil.parseCalendar(SampleCalendarDataUtil.SAMPLE_CALENDAR_LYNN);
            return new CalendarWrapper[]{
                    new CalendarWrapper(new MemberName("Gabriel"), sampleCalendar1,
                            SampleCalendarDataUtil.SAMPLE_CALENDAR_GABRIEL),
                    new CalendarWrapper(new MemberName("Abhinav"), sampleCalendar2,
                            SampleCalendarDataUtil.SAMPLE_CALENDAR_ABHINAV),
                    new CalendarWrapper(new MemberName("Lynn"), sampleCalendar3,
                            SampleCalendarDataUtil.SAMPLE_CALENDAR_LYNN)
            };
        } catch (ParseException e) {
            e.printStackTrace();
            return new CalendarWrapper[0];
        }
    }

    public static ReadOnlyProjectDashboard getSampleProjectDashboard() {
        ProjectDashboard samplePd = new ProjectDashboard();
        for (Task sampleTask : getSampleTasks()) {
            samplePd.addTask(sampleTask);
        }
        for (Member sampleMember : getSampleMembers()) {
            samplePd.addMember(sampleMember);
        }
        for (Inventory sampleInventory : getSampleInventory()) {
            samplePd.addInventory(sampleInventory);
        }

        for (InvMemMapping sampleInvMem : getSampleInvMemMapping()) {
            samplePd.addMapping(sampleInvMem);
        }

        for (InvTasMapping sampleInvTas : getSampleInvTasMapping()) {
            samplePd.addMapping(sampleInvTas);
        }

        for (TasMemMapping sampleTasMem : getSampleTasMemMapping()) {
            samplePd.addMapping(sampleTasMem);
        }

        for (Meeting sampleMeeting : getSampleMeeting()) {
            samplePd.addMeeting(sampleMeeting);
        }

        for (CalendarWrapper sampleCalendar : getSampleCalendar()) {
            samplePd.addCalendar(sampleCalendar);
        }

        return samplePd;
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
