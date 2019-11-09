package seedu.ifridge.model.waste;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Creates a Waste Report
 */
public class WasteReport implements Iterable<Map.Entry<WasteMonth, WasteStatistic>> {

    private Map<WasteMonth, WasteStatistic> historicalData;

    public WasteReport(Map<WasteMonth, WasteStatistic> historicalData) {
        requireNonNull(historicalData);
        this.historicalData = historicalData;
    }

    public HashMap<WasteMonth, WasteStatistic> getData() {
        return new HashMap<>(historicalData);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Map.Entry<WasteMonth, WasteStatistic>> iterator() {
        return historicalData.entrySet().iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WasteReport)) {
            return false;
        }
        WasteReport otherReport = (WasteReport) other;
        return this.getData().equals(otherReport.getData());
    }

    @Override
    public String toString() {
        return historicalData.toString();
    }
}
