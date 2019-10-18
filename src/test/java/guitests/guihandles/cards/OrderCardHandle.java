package guitests.guihandles.cards;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.order.Order;

/**
 * Provide handle for {@Code Order}
 */
public class OrderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String CUSTOMER_NAME_FIELD_ID = "#customerName";
    private static final String CONTACT_NUMBER_FIELD_ID = "#contactNumber";
    private static final String PHONE_ID_FIELD_ID = "#phoneId";
    private static final String PHONE_NAME_FIELD_ID = "#phoneName";
    private static final String PHONE_COLOUR_FIELD_ID = "#phoneColour";
    private static final String PHONE_CAPACITY_FIELD_ID = "#phoneCapacity";
    private static final String ORDER_PRICE_FIELD_ID = "#orderPrice";
    private static final String ORDER_STATUS_FIELD_ID = "#orderStatus";
    private static final String ORDER_ID_FIELD_ID = "#orderId";
    private static final String TAGS_FIELD_ID = "#tags";



    private final Label idLabel;

    private final Label contactNumberLabel;

    private final Label customerNameLabel;

    private final Label phoneIdLabel;

    private final Label phoneNameLabel;

    private final Label phoneColourLabel;

    private final Label phoneCapacityLabel;

    private final Label orderIdLabel;

    private final Label orderPriceLabel;

    private final Label orderStatusLabel;

    private final List<Label> tagLabels;

    protected OrderCardHandle(Node rootNode) {
        super(rootNode);

        idLabel = getChildNode(ID_FIELD_ID);
        customerNameLabel = getChildNode(CUSTOMER_NAME_FIELD_ID);
        contactNumberLabel = getChildNode(CONTACT_NUMBER_FIELD_ID);
        phoneIdLabel = getChildNode(PHONE_ID_FIELD_ID);
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

    public String getId() {
        return idLabel.getText();
    }
    public String getContactNumber() {
        return contactNumberLabel.getText();
    }
    public String getCustomerName() {
        return customerNameLabel.getText();
    }
    public String getPhoneId() {
        return phoneNameLabel.getText();
    }

    public String getPhoneName() {
        return phoneNameLabel.getText();
    }

    public String getPhoneColour() {
        return phoneColourLabel.getText();
    }

    public String getPhoneCapacity() {
        return phoneCapacityLabel.getText();
    }

    public String getOrderId() {
        return orderIdLabel.getText();
    }

    public String getOrderPrice() {
        return orderPriceLabel.getText();
    }

    public String getOrderStatus() {
        return orderStatusLabel.getText();
    }


    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code order}.
     */
    public boolean equals(Order order) {
        return getId().equals(order.getId());
    }

}
