package seedu.address.storage.borrowerrecords;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.borrower.Borrower;

/**
 * An Immutable BorrowerRecords that is serializable to JSON format.
 */
@JsonRootName(value = "BorrowerRecords")
class JsonSerializableBorrowerRecords {

    public static final String MESSAGE_DUPLICATE_BORROWER = "borrowers list contains duplicate borrower(s).";

    private final List<JsonAdaptedBorrower> borrowers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBorrowerRecords} with the given borrowers.
     */
    @JsonCreator
    public JsonSerializableBorrowerRecords(@JsonProperty("borrowers") List<JsonAdaptedBorrower> borrowers) {
        this.borrowers.addAll(borrowers);
    }

    /**
     * Converts a given {@code ReadOnlyBorrowerRecords} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBorrowerRecords}.
     */
    public JsonSerializableBorrowerRecords(ReadOnlyBorrowerRecords source) {
        borrowers.addAll(source.getBorrowerList().stream().map(JsonAdaptedBorrower::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address borrower into the model's {@code BorrowerRecords} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BorrowerRecords toModelType() throws IllegalValueException {
        BorrowerRecords BorrowerRecords = new BorrowerRecords();
        for (JsonAdaptedBorrower jsonAdaptedBorrower : borrowers) {
            Borrower borrower = jsonAdaptedBorrower.toModelType();
            if (BorrowerRecords.hasBorrower(borrower)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BORROWER);
            }
            BorrowerRecords.addBorrower(borrower);
        }
        return BorrowerRecords;
    }

}
