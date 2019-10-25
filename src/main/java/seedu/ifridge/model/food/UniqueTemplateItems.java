package seedu.ifridge.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ifridge.model.food.exceptions.DuplicateFoodException;
import seedu.ifridge.model.food.exceptions.FoodNotFoundException;

/**
 * A list of template items that enforces uniqueness between its elements and does not allow nulls.
 * A template item is considered unique by comparing using {@code Food#isSameFood(Food)}. As such, adding and
 * updating of template items uses Food#isSameFood(Food) for equality so as to ensure that the template item being
 * added or updated is unique in terms of identity in the UniqueTemplateItems. However, the removal of a template item
 * uses Food#equals(Food) so as to ensure that the template item with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Food#isSameFood(Food)
 */
public class UniqueTemplateItems implements Iterable<TemplateItem>, Comparable<UniqueTemplateItems> {

    private final ObservableList<TemplateItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<TemplateItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final Name name;

    /**
     * Name field must be present and not null.
     */
    public UniqueTemplateItems(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(TemplateItem toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFood);
    }

    /**
     * Adds a template item to the template list.
     * The template item must not already exist in the list.
     */
    public void add(TemplateItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
        Collections.sort(internalList);
    }

    /**
     * Replaces the template item {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the list.
     * The template item identity of {@code editedFood} must not be the same as another existing template item
     * in the list.
     */
    public void setTemplateItem(TemplateItem target, TemplateItem editedFood) {
        requireAllNonNull(target, editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.isSameFood(editedFood) && contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
        Collections.sort(internalList);
    }

    /**
     * Removes the equivalent template item from the list.
     * The template item must exist in the list.
     */
    public void remove(TemplateItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    public void setTemplateItems(UniqueTemplateItems replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        Collections.sort(internalList);
    }

    /**
     * Replaces the contents of this list with {@code foods}.
     * {@code foods} must not contain duplicate food items.
     */
    public void setTemplateItems(List<TemplateItem> foods) {
        requireAllNonNull(foods);
        if (!foodItemsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
        Collections.sort(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<TemplateItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<TemplateItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTemplateItems // instanceof handles nulls
                        && internalList.equals(((UniqueTemplateItems) other).internalList));
    }

    /**
     * Returns true if both templates of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two templates.
     */
    public boolean isSameTemplate(UniqueTemplateItems otherTemplate) {
        if (otherTemplate == this) {
            return true;
        }

        return otherTemplate != null
                && otherTemplate.getName().equals(getName());
    }

    public Name getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code foods} contains only unique food items.
     */
    private boolean foodItemsAreUnique(List<TemplateItem> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).isSameFood(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getSize() {
        return internalList.size();
    }

    public TemplateItem get(int index) {
        return internalList.get(index);
    }

    public ObservableList<TemplateItem> getTemplate() {
        return this.internalUnmodifiableList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" with ");
        builder.append(getSize());
        builder.append(" items.");
        return builder.toString();
    }

    @Override
    public int compareTo(UniqueTemplateItems other) {
        String thisName = this.getName().toString();
        String otherName = other.getName().toString();

        return thisName.compareTo(otherName);
    }
}
