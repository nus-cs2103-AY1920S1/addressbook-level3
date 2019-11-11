package seedu.weme.model.template;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.weme.model.template.exceptions.DuplicateTemplateException;
import seedu.weme.model.template.exceptions.TemplateNotFoundException;

/**
 * A list of templates that enforces uniqueness between its elements and does not allow nulls.
 * A template is considered unique by comparing using {@link Template#isSameTemplate(Template)}. As such, adding and
 * updating of templates uses {@link Template#isSameTemplate(Template)} for equality so as to ensure that the template
 * being added or updated is unique in terms of identity in the UniqueTemplateList. However, the removal of a template
 * uses {@link Template#equals(Object)} so as to ensure that the template with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Template#isSameTemplate(Template)
 */
public class UniqueTemplateList implements Iterable<Template> {

    private final ObservableList<Template> internalList = FXCollections.observableArrayList();
    private final ObservableList<Template> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent template as the given argument.
     */
    public boolean contains(Template toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTemplate);
    }

    /**
     * Adds a template to the list.
     * The template must not already exist in the list.
     */
    public void add(Template toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTemplateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the template {@code target} in the list with {@code editedTemplate}.
     * {@code target} must exist in the list.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in the list.
     */
    public void setTemplate(Template target, Template editedTemplate) {
        requireAllNonNull(target, editedTemplate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TemplateNotFoundException();
        }

        if (!target.isSameTemplate(editedTemplate) && contains(editedTemplate)) {
            throw new DuplicateTemplateException();
        }

        internalList.set(index, editedTemplate);
    }

    /**
     * Removes the equivalent template from the list.
     * The template must exist in the list.
     */
    public void remove(Template toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TemplateNotFoundException();
        }
    }

    public void setTemplates(UniqueTemplateList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code templates}.
     * {@code templates} must not contain duplicate templates.
     */
    public void setTemplates(List<Template> templates) {
        requireAllNonNull(templates);
        if (!templatesAreUnique(templates)) {
            throw new DuplicateTemplateException();
        }

        internalList.setAll(templates);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Template> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Template> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTemplateList // instanceof handles nulls
            && internalList.equals(((UniqueTemplateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code templates} contains only unique templates.
     */
    private boolean templatesAreUnique(List<Template> templates) {
        for (int i = 0; i < templates.size() - 1; i++) {
            for (int j = i + 1; j < templates.size(); j++) {
                if (templates.get(i).isSameTemplate(templates.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
