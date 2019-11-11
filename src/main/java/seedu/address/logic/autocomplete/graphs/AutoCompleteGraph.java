package seedu.address.logic.autocomplete.graphs;

import seedu.address.logic.autocomplete.AutoCompleteResultProvider;
import seedu.address.logic.autocomplete.nodes.AutoCompleteNode;

/**
 * Represents a {@code Graph} that implements {@code AutoCompleteResultProvider}.
 */
public abstract class AutoCompleteGraph extends Graph<AutoCompleteNode<?>> implements AutoCompleteResultProvider {}
