package guitests.guihandles;

import javafx.scene.Node;

/**
 * Provides a base for handles for card in a list panel to extend from.
 */
public abstract class CardHandle<E> extends NodeHandle<Node> {

    public CardHandle(Node cardNode) {
        super(cardNode);
    }

    /**
     * Returns true if this handle wraps the {@code E}.
     */
    public abstract boolean wraps(E element);
}
