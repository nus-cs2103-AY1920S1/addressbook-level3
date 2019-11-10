package seedu.address.model.card;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import seedu.address.commons.core.index.Index;

/**
 * Supplier that supplies FormattedHints. This Supplier stores a List of Hints {@code hintCharacters} and
 * a {@code FormattedHint}. It updates the FormattedHint automatically every time {@code get()} is called
 * by polling from the List of Hints, which has been shuffled.
 */
public class FormattedHintSupplier implements Supplier<FormattedHint> {

    private List<Hint> hintCharacters;
    private FormattedHint formattedHint;
    /**
     * Constructs a {@code FormattedHintSupplier}.
     *
     * @param text Word's String that the Hints are based on, cannot be null.
     */
    FormattedHintSupplier(String text) {
        requireNonNull(text);

        hintCharacters = new LinkedList<>();
        for (int i = 0; i < text.length(); ++i) {
            hintCharacters.add(new Hint(text.charAt(i), Index.fromZeroBased(i)));
        }
        Collections.shuffle(hintCharacters);

        /* Initializing formattedHint, contains all null characters at this point. */
        formattedHint = new FormattedHint(hintCharacters.size());
    }

    /**
     * Returns the size of {@code hintCharacters}.
     */
    public int getRemainingNumOfHints() {
        return hintCharacters.size();
    }

    /**
     * Returns {@code FormattedHint} that is updated with the new Hint characters, if available.
     * Returns same formatted hint if no more characters available.
     */
    @Override
    public FormattedHint get() {
        if (hintCharacters.isEmpty()) {
            /* Do not update if no more hintCharacters are available. */
            return formattedHint;
        } else {
            /* Polls and removes the first element of hintCharacters.*/
            formattedHint.updateHintArray(hintCharacters.remove(0));
            return formattedHint;
        }
    }
}
