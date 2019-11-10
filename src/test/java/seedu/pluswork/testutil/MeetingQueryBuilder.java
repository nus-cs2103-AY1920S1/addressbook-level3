package seedu.pluswork.testutil;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.parser.exceptions.ParseException;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.calendar.MeetingQuery;
import seedu.pluswork.model.member.MemberName;

/**
 * A utility class to help with building {@code Task} objects.
 */
public class MeetingQueryBuilder {

    public static final List<Meeting> DEFAULT_MEETING_LIST = new ArrayList();
    public static final LocalDateTime DEFAULT_START_DATE;
    public static final LocalDateTime DEFAULT_END_DATE;
    public static final Duration DEFAULT_DURATION;
    public static final List<MemberName> DEFAULT_MEMBER_NAME_LIST_1 = new ArrayList<>(
            Arrays.asList(new MemberName("Bob"),
                    new MemberName("Anne"),
                    new MemberName("Charlie"),
                    new MemberName("David")));
    public static final List<MemberName> DEFAULT_MEMBER_NAME_LIST_2 = new ArrayList<>(
            Arrays.asList(new MemberName("Bob"),
                    new MemberName("Elliot"),
                    new MemberName("Charlie"),
                    new MemberName("Francis")));

    static {
        LocalDateTime tmpStartDate = null;
        LocalDateTime tmpEndDate = null;
        Duration tmpDuration = null;
        try {
            tmpDuration = Duration.ofHours(2);
            DEFAULT_MEETING_LIST.add(new MeetingBuilder()
                    .withStartTime(DateTimeUtil.parseDateTime("22-12-2019 18:00"))
                    .withDuration(tmpDuration)
                    .withMembers(DEFAULT_MEMBER_NAME_LIST_1)
                    .build());
            DEFAULT_MEETING_LIST.add(new MeetingBuilder()
                    .withStartTime(DateTimeUtil.parseDateTime("23-12-2019 08:00"))
                    .withDuration(tmpDuration)
                    .withMembers(DEFAULT_MEMBER_NAME_LIST_2)
                    .build());
            DEFAULT_MEETING_LIST.add(new MeetingBuilder()
                    .withStartTime(DateTimeUtil.parseDateTime("24-12-2019 13:00"))
                    .withDuration(tmpDuration)
                    .withMembers(DEFAULT_MEMBER_NAME_LIST_1)
                    .build());
            DEFAULT_MEETING_LIST.add(new MeetingBuilder()
                    .withStartTime(DateTimeUtil.parseDateTime("25-12-2019 11:00"))
                    .withDuration(tmpDuration)
                    .withMembers(DEFAULT_MEMBER_NAME_LIST_2)
                    .build());
            DEFAULT_MEETING_LIST.add(new MeetingBuilder()
                    .withStartTime(DateTimeUtil.parseDateTime("26-12-2019 17:00"))
                    .withDuration(tmpDuration)
                    .withMembers(DEFAULT_MEMBER_NAME_LIST_1)
                    .build());
            tmpStartDate = DateTimeUtil.parseDateTime("22-12-2019 08:00");
            tmpEndDate = DateTimeUtil.parseDateTime("26-12-2019 23:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DEFAULT_START_DATE = tmpStartDate;
        DEFAULT_END_DATE = tmpEndDate;
        DEFAULT_DURATION = tmpDuration;
    }

    private List<Meeting> meetingList = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration duration;

    public MeetingQueryBuilder() {
        startDate = DEFAULT_START_DATE;
        endDate = DEFAULT_END_DATE;
        duration = DEFAULT_DURATION;
        meetingList = DEFAULT_MEETING_LIST;
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MeetingQueryBuilder(MeetingQuery meetingQueryToCopy) {
        requireNonNull(meetingQueryToCopy);
        startDate = meetingQueryToCopy.getStartDate();
        endDate = meetingQueryToCopy.getEndDate();
        duration = meetingQueryToCopy.getDuration();
        meetingList = meetingQueryToCopy.getMeetingList();
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public MeetingQueryBuilder withStartDate(LocalDateTime startDate) throws ParseException {
        this.startDate = DateTimeUtil.parseDateTime(DateTimeUtil.displayDateTime(startDate));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public MeetingQueryBuilder withEndDate(LocalDateTime endDate) throws ParseException {
        this.endDate = DateTimeUtil.parseDateTime(DateTimeUtil.displayDateTime(endDate));
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public MeetingQueryBuilder withDuration(Duration duration) {
        this.duration = Duration.parse(duration.toString());
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public MeetingQueryBuilder withMeetings(List<Meeting> meetingList) {
        this.meetingList = new ArrayList<>();
        meetingList.addAll(meetingList);
        return this;
    }


    public MeetingQuery build() {
        return new MeetingQuery(meetingList, startDate, endDate, duration);
    }

}
