package seedu.address.logic.optimizer;

import java.util.Comparator;
import java.util.Optional;

import javafx.util.Pair;
import seedu.address.model.EventTime;
import seedu.address.model.person.Driver;

/**
 * A convenient representation for a Driver-EventTime pair.
 */
public class Candidate extends Pair<Driver, Optional<EventTime>> {

    /**
     * Creates a new pair.
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public Candidate(Driver key, Optional<EventTime> value) {
        super(key, value);
    }

    /**
     * Gets a comparator between the EventTimes in the pair.
     *
     * @return the comparator
     */
    public static Comparator<Candidate> comparator() {
        return (o1, o2) -> {
            // unpack
            Optional<EventTime> t1 = o1.getValue();
            Optional<EventTime> t2 = o2.getValue();

            if (t1.isPresent() && t2.isPresent()) {
                return t1.get().compareTo(t2.get());
            } else if (t1.isEmpty()) {
                return 1;
            } else {
                return -1;
            }
        };
    }
}

