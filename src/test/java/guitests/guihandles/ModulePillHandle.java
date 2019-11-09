package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.module.Module;

/**
 * Provides a handle to a module card in the module list panel.
 */
public class ModulePillHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";

    private final Label nameLabel;

    public ModulePillHandle(Node pillNode) {
        super(pillNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code module}.
     */
    public boolean equals(Module module) {
        return getName().equals(module.getName());
    }
}
