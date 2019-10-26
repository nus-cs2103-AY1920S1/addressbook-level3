package guitests.guihandles.cards;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.customer.Customer;

/**
 * Provides a handle to a customer card in the person list panel.
 */
public class CustomerCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String CONTACT_NUMBER_FIELD_ID = "#contactNumber";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label emailLabel;
    private final Label nameLabel;
    private final Label contactNumberLabel;
    private final List<Label> tagLabels;

    public CustomerCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        contactNumberLabel = getChildNode(CONTACT_NUMBER_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
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

    public String getName() {
        return nameLabel.getText();
    }


    public String getContactNumber() {
        return contactNumberLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
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
    public boolean equals(Customer customer) {
        return getName().equals(customer.getCustomerName().fullName)
                && getContactNumber().equals(customer.getContactNumber().value)
                && getEmail().equals(customer.getEmail().value)
                && getTags().equals(customer.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }

}
