package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

/**
 * Represents the index of a delivery record.
 */
public class RecordIndex {
    private int indexNum;

    public RecordIndex(int indexNum) {
        this.indexNum = indexNum;
    }

    public String toString() {
        return "#" + indexNum;
    }
}
