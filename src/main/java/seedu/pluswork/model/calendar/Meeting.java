package seedu.pluswork.model.calendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.model.member.MemberName;

public class Meeting {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Duration duration;
    private final ObservableList<MemberName> internalList = FXCollections.observableArrayList();
    private final ObservableList<MemberName> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public Meeting(LocalDateTime startTime, Duration duration, List<MemberName> memberNameList) {
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
        internalList.addAll(memberNameList);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public ObservableList<MemberName> getMemberNameList() {
        return internalUnmodifiableList;
    }

    public Meeting updateMemberRemoved(MemberName memberToRemove) {
        List<MemberName> updatedNameList = internalList.stream()
                .filter(memberName -> !memberName.equals(memberToRemove))
                .collect(Collectors.toList());
        return new Meeting(startTime, duration, updatedNameList);
    }

    public boolean isSameMeeting(Meeting other) {
        return getStartTime().equals(other.getStartTime()) &&
                getEndTime().equals(other.getEndTime()) &&
                getDuration().equals(other.getDuration()) &&
                getMemberNameList().equals(other.getMemberNameList());
    }

    @Override
    public String toString() {
        return DateTimeUtil.displayDateTime(startTime) + " - " +
                DateTimeUtil.displayDateTime(endTime);
    }
}
