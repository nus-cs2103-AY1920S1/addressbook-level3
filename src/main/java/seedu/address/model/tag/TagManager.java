package seedu.address.model.tag;

import java.util.ArrayList;

public class TagManager {
    private ArrayList<Tag> allTags;

    public TagManager(ArrayList<Tag> _allTags) {
        this.allTags = _allTags;
    }

    public ArrayList<Tag> findTag(String keyword) {
        ArrayList<Tag> resultList = new ArrayList<>();
        for (Tag item : allTags) {
            if (item.getName().contains(keyword)) {
                resultList.add(item);
            }
        }
        return resultList;
    }

    public ArrayList<Tag> getTags(String keyword) {
        ArrayList<Tag> resultList = new ArrayList<>();
        resultList.addAll(allTags);
        return resultList;
    }

    public void setTagName(String tagName, String newName) {
        Tag cur = null;
        for (Tag item : allTags) {
            if (item.getName().equals(tagName)) {
                cur = item;
            }
        }
        cur.setName(newName);
    }

    public void addTag(String name) {
        Tag t = new Tag(name);
        ArrayList<Tag> newTags = new ArrayList<>();
        newTags.addAll(allTags);
        newTags.add(t);
        this.allTags = newTags;
    }

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
