package seedu.address.model.card;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import seedu.address.commons.core.index.Index;

/**
 * Supplies a hint consisting of a letter and the index the letter occurs.
 * Each character in the string is output once in random order.
 */
public class HintSupplier implements Supplier<HintFormat> {

    private List<Hint> hints;
    private HintFormat hintFormat;
    /**
     * Constructs a {@code HintSupplier}.
     *
     * @param text the text that the hints are based on
     */
    public HintSupplier(String text) {
        requireNonNull(text);
        hints = new LinkedList<>();
        for (int i = 0; i < text.length(); ++i) {
            hints.add(new Hint(text.charAt(i), Index.fromZeroBased(i)));
        }
        Collections.shuffle(hints);
        hintFormat = new HintFormat(hints.size());
    }

    public int getRemainingHints() {
        return hints.size();
    }

    /**
     * Returns the next formatted hint. Returns same formatted hint of no more characters available.
     */
    @Override
    public HintFormat get() {
        if (hints.isEmpty()) {
            return hintFormat;
        } else {
            hintFormat.updateHintArray(hints.remove(0));
            return hintFormat;
        }
    }
}
