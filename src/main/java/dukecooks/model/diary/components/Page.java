package dukecooks.model.diary.components;

import java.util.Objects;

import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.Image;

/**
 * Represents a Page in Duke Cooks.
 * Guarantees: details are present and not null, field values validated and immutable.
 */
public class Page {

    // Identity fields
    private final Title title;
    private final PageDescription description;

    // Optional fields
    private final Image image;

    /**
     * Every field must be present and not null.
     */
    public Page(Title title, PageDescription description, Image image) {
        CollectionUtil.requireAllNonNull(title, description, image);
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Title getTitle() {
        return title;
    }

    public PageDescription getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    /**
     * Returns true if both persons of the same diaryName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePage(Page otherPage) {
        if (otherPage == this) {
            return true;
        }

        return otherPage != null
                && otherPage.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both Pages have the same identity fields.
     * This defines a stronger notion of equality between two pages.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Page)) {
            return false;
        }

        Page otherPage = (Page) other;
        return otherPage.getTitle().equals(getTitle());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Description: ")
                .append(getDescription().fullPageDescription);
        return builder.toString();
    }
}
