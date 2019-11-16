package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.exceptions.DuplicateTemplateException;
import seedu.ifridge.model.food.exceptions.TemplateNotFoundException;

/**
 * A list of templates that enforces uniqueness between its elements and does not allow nulls.
 * A template is considered unique by comparing using {@code UniqueTemplateItems#isSameTemplate(UniqueTemplateItems)}.
 * As such, adding and updating of templates uses UniqueTemplateItems#isSameTemplate(UniqueTemplateItems) for equality
 * so as to ensure that the template being added or updated is unique in terms of identity in the UniqueTemplateList.
 * However, the removal of a template uses TemplateList#equals(Object) so as to ensure that the template with
 * exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see UniqueTemplateItems#isSameTemplate(UniqueTemplateItems)
 */
public class UniqueTemplateList implements Iterable<UniqueTemplateItems> {

    private final ObservableList<UniqueTemplateItems> internalList = FXCollections.observableArrayList();
    private final ObservableList<UniqueTemplateItems> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent template as the given argument.
     */
    public boolean contains(UniqueTemplateItems toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTemplate);
    }

    /**
     * Returns true if the food item exists in any of the templates in the template list
     */
    public boolean containsTemplateItemWithName(Food foodItem) {
        requireNonNull(foodItem);
        for (int i = 0; i < internalList.size(); i++) {
            UniqueTemplateItems listN = internalList.get(i);
            if (listN.contains(foodItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a template to the template list.
     * The template must not already exist in the list.
     */
    public void add(UniqueTemplateItems toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTemplateException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList);
    }

    /**
     * Replaces the template {@code target} in the list with {@code editedTemplate}.
     * {@code target} must exist in the list.
     * The template identity of {@code editedTemplate} must not be the same as another existing template in the list.
     */
    public void setTemplate(UniqueTemplateItems target, UniqueTemplateItems editedTemplate) {
        requireAllNonNull(target, editedTemplate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TemplateNotFoundException();
        }

        if (!target.isSameTemplate(editedTemplate) && contains(editedTemplate)) {
            throw new DuplicateTemplateException();
        }

        internalList.set(index, editedTemplate);
        Collections.sort(internalList);
    }

    public void setTemplates(UniqueTemplateList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        Collections.sort(internalList);
    }

    /**
     * Replaces the contents of this list with {@code templates}.
     * {@code templates} must not contain duplicate food templates.
     */
    public void setTemplates(List<UniqueTemplateItems> templates) {
        requireAllNonNull(templates);
        if (!templatesAreUnique(templates)) {
            throw new DuplicateTemplateException();
        }
        internalList.setAll(templates);
        Collections.sort(internalList);
    }

    /**
     * Removes the equivalent template from the list.
     * The template must exist in the list.
     */
    public void remove(UniqueTemplateItems toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TemplateNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<UniqueTemplateItems> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<UniqueTemplateItems> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof UniqueTemplateList) {
            if (internalList.size() > ((UniqueTemplateList) other).internalList.size()
                    || internalList.size() < ((UniqueTemplateList) other).internalList.size()) {
                return false;
            }
            if (internalList.size() == 0 && ((UniqueTemplateList) other).internalList.size() == 0) {
                return true;
            }
            for (int i = 0; i < internalList.size(); i++) {
                if (!internalList.get(i).equals(((UniqueTemplateList) other).internalList.get(i))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code templates} contains only unique templates.
     */
    private boolean templatesAreUnique(List<UniqueTemplateItems> templates) {
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
