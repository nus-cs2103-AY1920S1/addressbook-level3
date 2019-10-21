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
public class HintSupplier implements Supplier<FormattedHint> {

    private List<Hint> hintCharacters;
    private FormattedHint formattedHint;
    /**
     * Constructs a {@code HintSupplier}.
     *
     * @param text the text that the hints are based on
     */
    public HintSupplier(String text) {
        requireNonNull(text);
        hintCharacters = new LinkedList<>();
        for (int i = 0; i < text.length(); ++i) {
            hintCharacters.add(new Hint(text.charAt(i), Index.fromZeroBased(i)));
        }
        Collections.shuffle(hintCharacters);
        formattedHint = new FormattedHint(hintCharacters.size());
    }

    public int getRemainingHints() {
        return hintCharacters.size();
    }

    /**
     * Returns the next formatted hint. Returns same formatted hint of no more characters available.
     */
    @Override
    public FormattedHint get() {
        if (hintCharacters.isEmpty()) {
            return formattedHint;
        } else {
            formattedHint.updateHintArray(hintCharacters.remove(0));
            return formattedHint;
        }
    }
}
