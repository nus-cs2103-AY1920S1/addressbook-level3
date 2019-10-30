package dukecooks.logic.ui;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import dukecooks.logic.parser.DateParser;
import dukecooks.logic.parser.health.TimestampComparator;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.util.TypeUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is a class for the sole purpose to manipulate the record list result for Health Record's UI.
 * Supports functions to tweak the record list to show only information/records deemed useful.
 * Helps in filtering information/ records that does not help to provide useful inputs to the user.
 * It does NOT directly affect/ manipulate the original record list.
 */
public class CustomRecordList {

    /**
     * Filters records to show only the most recent record for each health data type.
     */
    public static ObservableList<Record> filterSummary(ObservableList<Record> recordList) {
        List<Record> result = TypeUtil.TYPE_LIST.entrySet().stream()
                .map(x -> recordList.stream()
                        .filter(c -> c.getType().type.equals(x.getKey()))
                        .max(new TimestampComparator())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    /**
     * Filters records to show only the past 30 days records.
     * If there is more than 1 record made in a day, it will take the most recent record made from that day.
     */
    public static ObservableList<Record> filterRecordsByLatest(ObservableList<Record> recordList) {
        List<Record> result = recordList.stream()
                .filter(x -> DateParser.getCurrentDayDiff(x.getTimestamp().getDate()) < 31)
                .collect(Collectors.groupingBy(r -> r.getTimestamp().getDate(),
                        Collectors.maxBy(new TimestampComparator())))
                .values().stream()
                .map(Optional::get)
                .sorted(new TimestampComparator())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }

    /**
     * Filters records to show only the past 30 days records.
     */
    public static ObservableList<Record> filterRecordsBySum(ObservableList<Record> recordList) {
        List<Record> result = recordList.stream()
                .filter(x -> DateParser.getCurrentDayDiff(x.getTimestamp().getDate()) < 31)
                .sorted(new TimestampComparator())
                .collect(Collectors.toList());
        return FXCollections.observableArrayList(result);
    }
}
