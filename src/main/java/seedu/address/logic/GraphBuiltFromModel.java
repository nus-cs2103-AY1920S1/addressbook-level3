package seedu.address.logic;

import seedu.address.model.Model;

public abstract class GraphBuiltFromModel<T> extends AutoCompleteGraph<T> {

    public GraphBuiltFromModel(Model model) {
        super();
        build(model);
    }

    /**
     * Populates this graph with data from {@code model}.
     * @param model A database.
     */
    protected abstract void build(Model model);

}
