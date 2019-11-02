package seedu.address.model.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.member.MemberName;

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
}