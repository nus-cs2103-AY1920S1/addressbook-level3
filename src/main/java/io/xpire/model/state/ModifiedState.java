package io.xpire.model.state;

import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;

/**
 * State that stores the previous model's Xpire, UserPrefs and the FilteredList. ModifiedState denotes
 * a state in which all the lists have been modified.
 * @@author Kalsyc
 */
public class ModifiedState extends State {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    public ModifiedState(Model model) {
        try {
            build(model);
        } catch (ClassCastException e) {
            logger.warning("Predicate Type mismatch in ModifiedState");
        }
        this.stateType = StateType.MODIFIED;
        this.cloneModel = clone(model, this.stateType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ModifiedState)) {
            return false;
        } else {
            ModifiedState other = (ModifiedState) obj;
            return other.getCloneModel().equals(this.getCloneModel());
        }
    }
}
