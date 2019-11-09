package seedu.pluswork.model.calendar;

import static seedu.pluswork.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.pluswork.model.member.MemberName;

public class MeetingQuery {
    private final ObservableList<Meeting> meetingList = FXCollections.observableArrayList();
    private final ObservableList<Meeting> meetingUnmodifiableList =
            FXCollections.unmodifiableObservableList(meetingList);
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Duration duration;

    public MeetingQuery(List<Meeting> meetingList, LocalDateTime startDate,
                        LocalDateTime endDate, Duration duration) {
        requireAllNonNull(meetingList, startDate, endDate, duration);
        this.meetingList.addAll(meetingList);
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public ObservableList<Meeting> getMeetingList() {
        return meetingUnmodifiableList;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean hasMeetings() {
        return meetingList.size() > 0;
    }

    public void updateMemberRemoved(MemberName memberToRemove) {
        List<Meeting> tmpList = meetingList.stream()
                .map(meeting -> (Meeting) meeting.updateMemberRemoved(memberToRemove))
                .collect(Collectors.toList());
        meetingList.setAll(tmpList);
    }
}
