package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;

/**
 * Provides a handle to a module card in the module list panel.
 */
public class ModuleCardHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String MCCOUNT_FIELD_ID = "#mcCount";
    private static final String PREREQS_FIELD_ID = "#prereqs";
    private static final String TAGS_FIELD_ID = "#tags";


    private final Label nameLabel;
    private final Label mcCountLabel;
    private final Label prereqsLabel;
    private final List<Label> tagLabels;

    public ModuleCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        mcCountLabel = getChildNode(MCCOUNT_FIELD_ID);
        prereqsLabel = getChildNode(PREREQS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }


    public String getName() {
        return nameLabel.getText();
    }

    public String getMcCount() {
        return mcCountLabel.getText();
    }

    public String getPrereqs() {
        return prereqsLabel.getText();
    }


    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return getName().equals(module.getName())
                && getMcCount().equals(module.getMcCount())
                && getPrereqs().equals(module.getPrereqString())
                && getTags().equals(module.getTags());
    }
}
