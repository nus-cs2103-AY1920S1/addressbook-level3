package seedu.address.flashcard;

import java.util.ArrayList;

/**
 *  A class for managing flashcard tagged sets.
 */
public class TagManager {
    private ArrayList<Tag> allTags;

    /**
     * Constructor for TagManager.
     *
     * @param _allTags a list of all the tagged sets.
     */
    public TagManager(ArrayList<Tag> _allTags) {
        this.allTags = _allTags;
    }

    /**
     * Identify all the relevant tags whose names contain the keyword.
     *
     * @param keyword the user input keyword for searching.
     * @return a list of all relevant tags.
     */
    public ArrayList<Tag> findTag(String keyword) {
        ArrayList<Tag> resultList = new ArrayList<>();
        for (Tag item : allTags) {
            if (item.getName().contains(keyword)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    /**
     * Give all the tags currently available.
     *
     * @return a list of all the tags.
     */
    public ArrayList<Tag> getTags() {
        ArrayList<Tag> resultList = new ArrayList<>();
        resultList.addAll(allTags);
        return resultList;
    }

    /**
     * Edit the name of a tag to a new name.
     *
     * @param tagName the original tag name.
     * @param newName the new tag name.
     */
    public void setTagName(String tagName, String newName) {
        Tag cur = null;
        for (Tag item : allTags) {
            if (item.getName().equals(tagName)) {
                cur = item;
            }
        }
        cur.setName(newName);
    }

    /**
     * Add a new tag to the current tag list.
     *
     * @param name name of the new tag.
     */
    public void addTag(String name) {
        Tag t = new Tag(name);
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.addAll(allTags);
        newTags.add(t);
        this.allTags = newTags;
    }

    /**
     * Remove a specific tag from the current tag list.
     *
     * @param name the name of tag that is to be deleted.
     */
    public void deleteTag(String name) {
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.addAll(allTags);
        for (Tag item : allTags) {
            if (item.getName().equals(name)) {
                newTags.remove(item);
            }
        }
        this.allTags = newTags;
    }

    /**
     * Check whether there exists a tag with the given name.
     *
     * @param name the name of the tag to be searched.
     * @return a boolean variable that informs either true or false
     */
    public boolean contains(String name) {
        boolean isContain = false;
        for (Tag item : allTags) {
            if (item.getName().equals(name)) {
                isContain = true;
            }
        }
        return isContain;
    }
}
