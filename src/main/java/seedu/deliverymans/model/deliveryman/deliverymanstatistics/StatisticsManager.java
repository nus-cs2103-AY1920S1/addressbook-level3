package seedu.deliverymans.model.deliveryman.deliverymanstatistics;

import seedu.deliverymans.model.Name;

/**
 * To be added.
 */
public class StatisticsManager {
    private final Analyzer analyzer;
    private RecordBackup records;

    {
        analyzer = Analyzer.getInstance();
        records = new RecordBackup();
    }

    /**
     * Creates a new record for a new deliveryman.
     * @return index of a new record
     */
    public RecordIndex createNewRecord(Name name) {
        return records.createNewRecord(name);
    }

    /**
     * Retrieves a delivery record given its record index.
     */
    public DeliveryRecord retrieveRecord(RecordIndex recordIndex) {
        return records.retrieveRecord(recordIndex);
    }

    /**
     * Passes a record to Analyzer to be analyzed.
     */
    public void analyzeRecord(RecordIndex recordIndex) {
        DeliveryRecord targetRecord = retrieveRecord(recordIndex);
        analyzer.analyze(targetRecord);
    }

}
