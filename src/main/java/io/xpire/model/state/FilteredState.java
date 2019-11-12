package io.xpire.model.state;

import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;


/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList. FilteredState denotes
 * a state in which the current view has been modified but not the items.
 * @@author Kalsyc
 */
public class FilteredState extends State {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    public FilteredState(Model model) {
        try {
            build(model);
        } catch (ClassCastException e) {
            logger.warning("Predicate Type mismatch in FilteredState");
        }
        this.stateType = StateType.FILTERED;
        this.cloneModel = clone(model, this.stateType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FilteredState)) {
            return false;
        } else {
            FilteredState other = (FilteredState) obj;
            return other.getCloneModel().equals(this.getCloneModel());
        }
    }
}
