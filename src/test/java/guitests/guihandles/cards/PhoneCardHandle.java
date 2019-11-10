package guitests.guihandles.cards;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.phone.Phone;

/**
 * Provide handle for {@Code Phone}
 */
public class PhoneCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String BRAND_FIELD_ID = "#brand";
    private static final String CAPACITY_FIELD_ID = "#capacity";
    private static final String COLOUR_FIELD_ID = "#colour";
    private static final String COST_FIELD_ID = "#cost";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String IDENTITY_FIELD_ID = "#identityNumber";
    private static final String SERIAL_FIELD_ID = "#serialNumber";


    private final Label nameLabel;
    private final Label colourLabel;
    private final Label costLabel;
    private final Label capacityLabel;
    private final Label brandLabel;
    private final Label idLabel;
    private final Label identityLabel;
    private final Label serialNumLabel;
    private final List<Label> tagLabels;


    public PhoneCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        colourLabel = getChildNode(COLOUR_FIELD_ID);
        costLabel = getChildNode(COST_FIELD_ID);
        capacityLabel = getChildNode(CAPACITY_FIELD_ID);
        brandLabel = getChildNode(BRAND_FIELD_ID);
        idLabel = getChildNode(ID_FIELD_ID);
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        identityLabel = getChildNode(IDENTITY_FIELD_ID);
        serialNumLabel = getChildNode(SERIAL_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getIdentityNumber() {
        return identityLabel.getText();
    }

    public String getSerialNumber() {
        return serialNumLabel.getText();
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getColour() {
        return colourLabel.getText();
    }

    public String getCost() {
        return costLabel.getText();
    }

    public String getCapacity() {
        return capacityLabel.getText();
    }

    public String getBrand() {
        return this.brandLabel.getText();
    }


    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code phone}.
     * @param phone
     */
    public boolean equals(Phone phone) {
        return getName().equals(phone.getPhoneName().fullName)
                && getColour().equals(phone.getColour().value)
                && getCost().equals(phone.getCost().value)
                && getBrand().equals(phone.getBrand().value)
                && getCapacity().equals(phone.getCapacity().value)
                && getTags().equals(phone.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
