package seedu.ifridge.model.waste;

import static java.util.Objects.requireNonNull;

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
        return other == this // short circuit if same object
                || (other instanceof WasteReport // instanceof handles nulls
                && historicalData.equals(((WasteReport) other).historicalData));
    }
}
