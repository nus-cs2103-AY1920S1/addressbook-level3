package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.visit.Remark;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class UpdateOngoingVisitDescriptor {
    private Remark remark;
    private List<Index> finishedVisitTaskIndexes;
    private List<Index> unfinishedVisitTaskIndexes;
    private List<Pair<Index, String>> updatedVisitTaskDetails;

    public UpdateOngoingVisitDescriptor() {
        finishedVisitTaskIndexes = new ArrayList<>();
        unfinishedVisitTaskIndexes = new ArrayList<>();
        updatedVisitTaskDetails = new ArrayList<>();
    }

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public UpdateOngoingVisitDescriptor(UpdateOngoingVisitDescriptor toCopy) {
        this();
        requireNonNull(toCopy);
        setRemark(toCopy.remark);
        setFinishedVisitTaskIndexes(toCopy.finishedVisitTaskIndexes);
        setUnfinishedVisitTaskIndexes(toCopy.unfinishedVisitTaskIndexes);
        setUpdatedVisitTaskDetails(toCopy.updatedVisitTaskDetails);
    }

    /**
     * Returns true if at least one field is updated.
     */
    public boolean isAnyFieldUpdated() {
        return remark != null
                || !finishedVisitTaskIndexes.isEmpty()
                || !unfinishedVisitTaskIndexes.isEmpty()
                || !updatedVisitTaskDetails.isEmpty();
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Optional<Remark> getRemark() {
        return Optional.ofNullable(remark);
    }

    public List<Index> getFinishedVisitTaskIndexes() {
        return finishedVisitTaskIndexes;
    }

    public void setFinishedVisitTaskIndexes(List<Index> finishedVisitTaskIndexes) {
        this.finishedVisitTaskIndexes.addAll(finishedVisitTaskIndexes);
    }

    public List<Index> getUnfinishedVisitTaskIndexes() {
        return unfinishedVisitTaskIndexes;
    }

    public void setUnfinishedVisitTaskIndexes(List<Index> unfinishedVisitTaskIndexes) {
        this.unfinishedVisitTaskIndexes.addAll(unfinishedVisitTaskIndexes);
    }

    public List<Pair<Index, String>> getUpdatedVisitTaskDetails() {
        return updatedVisitTaskDetails;
    }

    public void setUpdatedVisitTaskDetails(List<Pair<Index, String>> updatedVisitTaskDetails) {
        this.updatedVisitTaskDetails.addAll(updatedVisitTaskDetails);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateOngoingVisitDescriptor)) {
            return false;
        }

        // state check
        UpdateOngoingVisitDescriptor e = (UpdateOngoingVisitDescriptor) other;

        return getRemark().equals(e.getRemark())
                && CollectionUtil.checkEqual(getFinishedVisitTaskIndexes(), e.getFinishedVisitTaskIndexes())
                && CollectionUtil.checkEqual(getUnfinishedVisitTaskIndexes(), e.getUnfinishedVisitTaskIndexes())
                && CollectionUtil.checkEqual(getUpdatedVisitTaskDetails(), e.getUpdatedVisitTaskDetails());
    }
}
