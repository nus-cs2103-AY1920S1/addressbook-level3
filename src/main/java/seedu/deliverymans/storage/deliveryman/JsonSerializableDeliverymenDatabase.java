package seedu.deliverymans.storage.deliveryman;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.storage.customer.JsonAdaptedCustomer;

/**
 * An Immutable CustomerDatabase that is serializable to JSON format.
 */

@JsonRootName(value = "deliverymendatabase")
public class JsonSerializableDeliverymenDatabase {

    public static final String MESSAGE_DUPLICATE_DELIVERYMEN = "deliverymen list contains duplicate deliverymen.";

    private final List<JsonAdaptedDeliveryman> deliverymen = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDeliverymenDatabase} with the given deliverymen.
     */
    @JsonCreator
    public JsonSerializableDeliverymenDatabase(@JsonProperty("persons") List<JsonAdaptedDeliveryman> deliverymen) {
        this.deliverymen.addAll(deliverymen);
    }

    /**
     * Converts a given {@code ReadOnlyDeliverymenDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableDeliverymenDatabase}.
     */
    public JsonSerializableDeliverymenDatabase(ReadOnlyDeliverymenDatabase source) {
        deliverymen.addAll(source.getDeliverymenList().stream().map(JsonAdaptedDeliveryman::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code DeliverymenDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DeliverymenDatabase toModelType() throws IllegalValueException {
        DeliverymenDatabase deliverymenDatabase = new DeliverymenDatabase();
        for (JsonAdaptedDeliveryman jsonAdaptedDeliveryman : deliverymen) {
            Deliveryman deliveryman = jsonAdaptedDeliveryman.toModelType();
            if (deliverymenDatabase.hasDeliveryman(deliveryman)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DELIVERYMEN);
            }
            deliverymenDatabase.addDeliveryman(deliveryman);
        }
        return deliverymenDatabase;
    }

}