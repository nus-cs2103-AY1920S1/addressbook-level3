package dukecooks.logic.parser.health;

import java.util.Comparator;

import dukecooks.logic.parser.DateParser;
import dukecooks.model.health.components.Record;

/**
 * Determines the order of the records by its timestamp.
 */
public class TimestampComparator implements Comparator<Record> {
    @Override
    public int compare(Record a, Record b) {
        int isEarlierDate = DateParser.compareDate(a.getTimestamp().getDate(), b.getTimestamp().getDate());

        if (isEarlierDate != 0) {
            return isEarlierDate;
        } else { // same date => compare time instead
            int isEarlierTime = DateParser.compareTime(a.getTimestamp().getTime(), b.getTimestamp().getTime());
            return isEarlierTime;
        }
    }
}
