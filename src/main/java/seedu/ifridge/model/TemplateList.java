package seedu.ifridge.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.food.UniqueTemplateList;

/**
 * Wraps all data at the template list level
 * Duplicates are not allowed (by .isSameFood comparison)
 */
public class TemplateList implements ReadOnlyTemplateList {

    private final UniqueTemplateList templates;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        templates = new UniqueTemplateList();
    }

    public TemplateList() {}

    /**
     * Creates an TemplateList using the UniqueTemplateItems in the {@code toBeCopied}
     */
    public TemplateList(ReadOnlyTemplateList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the template list with {@code foods}.
     * {@code foods} must not contain duplicate persons.
     */
    public void setTemplates(List<UniqueTemplateItems> templates) {
        this.templates.setTemplates(templates);
    }

    /**
     * Resets the existing data of this {@code TemplateList} with {@code newData}.
     */
    public void resetData(ReadOnlyTemplateList newData) {
        requireNonNull(newData);

        setTemplates(newData.getTemplateList());
    }

    //// uniqueTemplateItems-level operations

    /**
     * Returns true if a template with the same identity as {@code template} exists in the template list.
     */
    public boolean hasTemplate(UniqueTemplateItems template) {
        requireNonNull(template);
        return templates.contains(template);
    }

    /**
     * Adds a template to the address book.
     * The template must not already exist in the templateList.
     */
    public void addTemplate(UniqueTemplateItems t) {
        templates.add(t);
    }

    /**
     * Replaces the given template in {@code target} in the list with {@code editedTemplate}.
     * {@code target} must exist in the template list.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in the
     * template list.
     */
    public void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate) {
        requireNonNull(editedTemplate);

        templates.setTemplate(target, editedTemplate);
    }

    /**
     * Removes {@code key} from this {@code TemplateList}.
     * {@code key} must exist in the template list.
     */
    public void removeTemplate(UniqueTemplateItems key) {
        templates.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return templates.asUnmodifiableObservableList().size() + " templates";
        // TODO: refine later
    }

    @Override
    public ObservableList<UniqueTemplateItems> getTemplateList() {
        return templates.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TemplateList // instanceof handles nulls
                && templates.equals(((TemplateList) other).templates));
    }

    @Override
    public int hashCode() {
        return templates.hashCode();
    }
}
