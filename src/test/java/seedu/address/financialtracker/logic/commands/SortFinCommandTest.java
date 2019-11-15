package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.financialtracker.model.Model;

class SortFinCommandTest {

    private ModelStub model = new ModelStub();

    @Test
    public void sort_failure() {
        // set fail
        model.setComparator("random_text");
        assertTrue(model.comparator == null);
    }

    @Test
    public void sort_success() {
        // set success with small capitals
        model.setComparator("time");
        assertTrue(model.comparator.equals("TIME"));

        // set success
        model.setComparator("DATE");
        assertTrue(model.comparator.equals("DATE"));
    }

    @Test
    public void equals() {
        SortFinCommand sortFin1 = new SortFinCommand("TIME");
        SortFinCommand sortFin2 = new SortFinCommand("DATE");

        // same object -> returns true
        assertTrue(sortFin1.equals(sortFin1));

        // same values -> returns true
        SortFinCommand sortFin1Copy = new SortFinCommand("TIME");
        assertTrue(sortFin1.equals(sortFin1Copy));

        // different value -> returns false
        assertFalse(sortFin1.equals(sortFin2));
    }

    /**
     * Stub for unit testing.
     */
    private class ModelStub extends Model {

        private String comparator;

        ModelStub() {
            this.comparator = null;
        }

        @Override
        public void setComparator(String args) {
            requireNonNull(args);
            String comparingType = args.toUpperCase().trim();
            if (comparingType.equals("TIME") || comparingType.equals("DATE") || comparingType.equals("AMOUNT")
                    || comparingType.equals("DEFAULT")) {
                this.comparator = comparingType;
            } else {
                this.comparator = null;
            }
        }
    }

}
