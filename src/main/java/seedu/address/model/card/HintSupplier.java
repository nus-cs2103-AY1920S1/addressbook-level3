package seedu.address.model.card;

import seedu.address.commons.core.index.Index;

import java.util.*;
import java.util.function.Supplier;

public class HintSupplier implements Supplier<Hint> {

    private List<Hint> hints;

    /**
     * Constructs a {@code HintSupplier}.
     *
     * @param text the text that the hints are based on
     */
    public HintSupplier(String text) {
        hints = new LinkedList<>();
        for (int i = 0; i < text.length(); ++i) {
            hints.add(new Hint(text.charAt(i), Index.fromZeroBased(i)));
        }
        Collections.shuffle(hints);
    }

    @Override
    public Hint get() {
        return hints.get(0);
    }
}
