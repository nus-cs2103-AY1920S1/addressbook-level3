package seedu.address.model.calendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.member.MemberName;

public class Meeting {
    private final LocalDateTime dateTime;
    private final ObservableList<MemberName> internalList = FXCollections.observableArrayList();
    private final ObservableList<MemberName> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public Meeting(LocalDateTime dateTime, List<MemberName> memberNameList) {
        this.dateTime = dateTime;
        internalList.addAll(memberNameList);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString() + " " + internalList.size();
    }
}