//@@author e0031374
package tagline.testutil.group;

import tagline.model.group.Group;
import tagline.model.group.GroupBook;

/**
 * A utility class to help with building Groupbook objects.
 * Example usage: <br>
 * {@code GroupBook ab = new GroupBookBuilder().withGroup(ULTRON).build();}
 */
public class GroupBookBuilder {

    private GroupBook groupBook;

    public GroupBookBuilder() {
        groupBook = new GroupBook();
    }

    public GroupBookBuilder(GroupBook groupBook) {
        this.groupBook = groupBook;
    }

    /**
     * Adds a new {@code Group} to the {@code GroupBook} that we are building.
     */
    public GroupBookBuilder withGroup(Group note) {
        groupBook.addGroup(note);
        return this;
    }

    public GroupBook build() {
        return groupBook;
    }
}
