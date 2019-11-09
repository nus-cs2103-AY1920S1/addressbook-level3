package seedu.guilttrip.ui.gui.guihandles;

import java.util.stream.Collectors;

import javafx.scene.Node;
import seedu.guilttrip.model.entry.Wish;

/**
 * Provides a handle to a wish card in the wish list panel.
 */
public class WishListCardHandle extends EntryCardHandle {

    public WishListCardHandle(Node cardNode) {
        super(cardNode);
    }

    /**
     * Returns true if this handle contains {@code wish}.
     */
    public boolean equals(Wish wish) {
        return getDesc().equals(wish.getDesc().fullDesc)
                && getAmount().equals(wish.getAmount().toString())
                && getDate().equals(wish.getDate().toString())
                && getCategory().equals(wish.getCategory().toString())
                && getTags().equals(wish.getTags().stream()
                .map(tag -> tag.tagName)
                .sorted()
                .collect(Collectors.toList()));
    }
}
