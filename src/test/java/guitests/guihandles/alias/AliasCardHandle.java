package guitests.guihandles.alias;

import guitests.guihandles.CardHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.moolah.model.alias.Alias;

/**
 * Provides a handle for {@code AliasCard} wrapping an {@code Alias} in a {@code AliasListPanel}.
 */
public class AliasCardHandle extends CardHandle<Alias> {

    private static final String NAME_FX_ID = "#aliasName";
    private static final String INPUT_FX_ID = "#aliasInput";

    private final Label aliasName;
    private final Label aliasInput;

    public AliasCardHandle(Node cardNode) {
        super(cardNode);
        aliasName = getChildNode(NAME_FX_ID);
        aliasInput = getChildNode(INPUT_FX_ID);
    }

    public String getAliasName() {
        return aliasName.getText().trim();
    }

    public String getAliasInput() {
        return aliasInput.getText().trim();
    }

    @Override
    public boolean wraps(Alias alias) {
        return aliasName.getText().trim().equals(alias.getAliasName())
                && aliasInput.getText().trim().equals(alias.getInput());
    }
}
