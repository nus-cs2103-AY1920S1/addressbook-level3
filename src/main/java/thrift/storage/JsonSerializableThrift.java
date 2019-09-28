package thrift.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import thrift.commons.exceptions.IllegalValueException;
import thrift.model.ReadOnlyThrift;
import thrift.model.Thrift;
import thrift.model.transaction.Transaction;

/**
 * An Immutable THRIFT that is serializable to JSON format.
 */
@JsonRootName(value = "thrift")
class JsonSerializableThrift {

    private final List<JsonAdaptedTransaction> transactions = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableThrift} with the given transactions.
     */
    @JsonCreator
    public JsonSerializableThrift(@JsonProperty("transactions") List<JsonAdaptedTransaction> transactions) {
        this.transactions.addAll(transactions);
    }

    /**
     * Converts a given {@code ReadOnlyThrift} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableThrift}.
     */
    public JsonSerializableThrift(ReadOnlyThrift source) {
        transactions.addAll(source.getTransactionList().stream().map(JsonAdaptedTransaction::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this THRIFT into the model's {@code Thrift} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Thrift toModelType() throws IllegalValueException {
        Thrift thrift = new Thrift();
        for (JsonAdaptedTransaction jsonAdaptedTransaction : transactions) {
            Transaction transaction = jsonAdaptedTransaction.toModelType();
            thrift.addTransaction(transaction);
        }
        return thrift;
    }

}
