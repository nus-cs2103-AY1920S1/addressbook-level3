package seedu.deliverymans.model.deliveryman;

import java.time.LocalDateTime;

/**
 * Analyzes the delivery record of deliverymen and computes relevant data.
 */
public class Analyzer {

    private final LocalDateTime currentDateTime;

    public Analyzer() {
        currentDateTime = LocalDateTime.now();
    }

    /**
     * Analyzes the time in database, the delivery rate, order completion rate of a deliveryman record.
     */
    public void analyze(DeliveryRecord record) {}
}
