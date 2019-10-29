package guitests.guihandles.cards;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;

/**
 * provides a handle for the order card
 */
public class OrderCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";
    private static final String CUSTOMER_NAME_FIELD_ID = "#customerName";
    private static final String CUSTOMER_CONTACT_NUMBER_FIELD_ID = "#customerContactNumber";
    private static final String CUSTOMER_EMAIL_FIELD_ID = "#customerEmail";

    private static final String PHONE_NAME_FIELD_ID = "#phoneName";
    private static final String PHONE_COLOUR_FIELD_ID = "#phoneColour";
    private static final String PHONE_CAPACITY_FIELD_ID = "#phoneCapacity";
    private static final String PHONE_COST_FIELD_ID = "#phoneCost";
    private static final String PHONE_IDENTITY_NUM_FIELD_ID = "#phoneIdentityNumber";
    private static final String PHONE_SERIAL_NUM_FIELD_ID = "#phoneSerialNumber";


    private static final String ORDER_ID_FIELD_ID = "#orderId";
    private static final String ORDER_PRICE_FIELD_ID = "#orderPrice";
    private static final String ORDER_STATUS_FIELD_ID = "#orderStatus";

    private static final String TAGS_FIELD_ID = "#tags";




    private final Label idLabel;
    private final List<Label> tagLabels;

    private final Label customerContactNumberLabel;

    private final Label customerNameLabel;

    private final Label customerEmailLabel;

    private final Label phoneIdentityNumberLabel;

    private final Label phoneSerialNumberLabel;

    private final Label phoneCostLabel;

    private final Label phoneNameLabel;

    private final Label phoneColourLabel;

    private final Label phoneCapacityLabel;

    private final Label orderIdLabel;

    private final Label orderPriceLabel;

    private final Label orderStatusLabel;


    public OrderCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        customerNameLabel = getChildNode(CUSTOMER_NAME_FIELD_ID);
        customerContactNumberLabel = getChildNode(CUSTOMER_CONTACT_NUMBER_FIELD_ID);
        customerEmailLabel = getChildNode(CUSTOMER_EMAIL_FIELD_ID);
        phoneIdentityNumberLabel = getChildNode(PHONE_IDENTITY_NUM_FIELD_ID);
        phoneSerialNumberLabel = getChildNode(PHONE_SERIAL_NUM_FIELD_ID);
        phoneCostLabel = getChildNode(PHONE_COST_FIELD_ID);
        phoneNameLabel = getChildNode(PHONE_NAME_FIELD_ID);
        phoneColourLabel = getChildNode(PHONE_COLOUR_FIELD_ID);
        phoneCapacityLabel = getChildNode(PHONE_CAPACITY_FIELD_ID);
        orderIdLabel = getChildNode(ORDER_ID_FIELD_ID);
        orderPriceLabel = getChildNode(ORDER_PRICE_FIELD_ID);
        orderStatusLabel = getChildNode(ORDER_STATUS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getCustomerContactNumberLabel() {
        return customerContactNumberLabel.getText();
    }

    public String getCustomerNameLabel() {
        return customerNameLabel.getText();
    }

    public String getCustomerEmailLabel() {
        return customerEmailLabel.getText();
    }

    public String getPhoneIdentityNumberLabel() {
        return phoneIdentityNumberLabel.getText();
    }

    public String getPhoneSerialNumberLabel() {
        return phoneSerialNumberLabel.getText();
    }

    public String getPhoneCostLabel() {
        return phoneCostLabel.getText();
    }

    public String getPhoneNameLabel() {
        return phoneNameLabel.getText();
    }

    public String getPhoneColourLabel() {
        return phoneColourLabel.getText();
    }

    public String getPhoneCapacityLabel() {
        return phoneCapacityLabel.getText();
    }

    public String getOrderIdLabel() {
        return orderIdLabel.getText();
    }

    public String getOrderPriceLabel() {
        return orderPriceLabel.getText();
    }

    public String getOrderStatusLabel() {
        return orderStatusLabel.getText();
    }

    public String getId() {
        return idLabel.getText();
    }


    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Order order) {
        Customer customer = order.getCustomer();
        return getCustomerNameLabel().equals(customer.getCustomerName().fullName)
               && getCustomerContactNumberLabel().equals(customer.getContactNumber().value)
                && getCustomerEmailLabel().equals(customer.getEmail().value)
                && getOrderIdLabel().equals(order.getId().toString())
                && getOrderPriceLabel().equals(order.getPrice().value)
                && getOrderStatusLabel().split(" ")[0].equals(order.getStatus().toString())
                && getPhoneCapacityLabel().equals(order.getPhone().getCapacity().value)
                && getPhoneColourLabel().equals(order.getPhone().getColour().value)
                && getPhoneCostLabel().equals(order.getPhone().getCost().value)
                && getPhoneCapacityLabel().equals(order.getPhone().getCapacity().value)
                && getPhoneIdentityNumberLabel().equals(order.getPhone().getIdentityNumber().value)
                && getPhoneNameLabel().equals(order.getPhone().getPhoneName().fullName)
                && getTags().equals(order.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
