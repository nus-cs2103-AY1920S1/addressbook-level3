package tagline.ui.util;

/**
 * Utility class for autocomplete feature.
 */
public class AutoCompleteUtil {
    /**
     * Returns a root node that matches all current command strings in TagLine.
     */
    public static AutoCompleteNode getAutoCompleteRootNode() {
        AutoCompleteNode contactRoot = new AutoCompleteNode("contact");
        contactRoot.addChildren(
            new AutoCompleteNode("list"),
            new AutoCompleteNode("create"),
            new AutoCompleteNode("delete"),
            new AutoCompleteNode("edit"),
            new AutoCompleteNode("find"),
            new AutoCompleteNode("clear")
        );

        AutoCompleteNode noteRoot = new AutoCompleteNode("note");
        noteRoot.addChildren(
            new AutoCompleteNode("list"),
            new AutoCompleteNode("create"),
            new AutoCompleteNode("edit"),
            new AutoCompleteNode("delete")
        );

        AutoCompleteNode groupRoot = new AutoCompleteNode("group");
        groupRoot.addChildren(
            new AutoCompleteNode("create"),
            new AutoCompleteNode("add"),
            new AutoCompleteNode("find")
        );

        AutoCompleteNode root = AutoCompleteNode.getRootNode();
        root.addChildren(contactRoot, noteRoot, groupRoot);
        return root;
    }
}
