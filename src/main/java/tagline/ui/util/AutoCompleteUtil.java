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
            new AutoCompleteNode("show"),
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
            new AutoCompleteNode("delete"),
            new AutoCompleteNode("tag"),
            new AutoCompleteNode("untag"),
            new AutoCompleteNode("clear")
        );

        AutoCompleteNode groupRoot = new AutoCompleteNode("group");
        groupRoot.addChildren(
            new AutoCompleteNode("list"),
            new AutoCompleteNode("create"),
            new AutoCompleteNode("add"),
            new AutoCompleteNode("remove"),
            new AutoCompleteNode("find")
        );

        AutoCompleteNode tagRoot = new AutoCompleteNode("tag");
        tagRoot.addChildren(
            new AutoCompleteNode("list")
        );

        AutoCompleteNode helpRoot = new AutoCompleteNode("help");

        AutoCompleteNode exitRoot = new AutoCompleteNode("exit");

        AutoCompleteNode root = AutoCompleteNode.getRootNode();
        root.addChildren(contactRoot, noteRoot, groupRoot, tagRoot, exitRoot, helpRoot);
        return root;
    }
}
