package seedu.address.model.record;


import seedu.address.commons.exceptions.DataConversionException;

import javax.xml.crypto.Data;

public abstract class Record {
    protected String description;
    protected boolean isDone;

    public Record() {
        isDone = false;
    }

    public Record(String description) {
        this.description = description;
    }

    /**
     * Constructor for Expense.
     * Throw error if there is no amount description or wrong format.
     *
     * @param desc  Description for the Record.
     * @param recordType Type for the record.
     * @throws DataConversionException  If details missing or in wrong format.
     */
    public Record(String desc, String recordType) throws DataConversionException {
        if (desc.isBlank()) {
//            throw new DataConversionException(recordType);
        } else {
            description = desc;
            isDone = false;
        }
    }

    public Record taskDone() {
        isDone = true;
        return this;
    }

    public abstract String getPrintableMsg();

    public String getDescription() {
        return description;
    }
}
