package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import java.util.HashMap;
import java.util.Map;

import seedu.deliverymans.model.Name;

/**
 * A data structure containing the record of every deliveryman.
 */
public class RecordBackup {
    private Map<RecordIndex, DeliveryRecord> records;
    private int nextIndex = 0;

    {
        records = new HashMap<RecordIndex, DeliveryRecord>();
    }

    /**
     * Initialises a new index and a new delivery record.
     */
    public RecordIndex createNewRecord(Name name) {
        RecordIndex newIndex = new RecordIndex(nextIndex++);
        DeliveryRecord newRecord = new DeliveryRecord(name);
        records.put(newIndex, newRecord);

        return newIndex;
    }

    public DeliveryRecord retrieveRecord(RecordIndex recordIndex) {
        return records.get(recordIndex);
    }

    /**
     * Initializes the records when app is restarted.
     */
    public void initRecords() {
        nextIndex = records.size();
    }
}
