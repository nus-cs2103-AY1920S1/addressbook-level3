package seedu.ifridge.model;

import java.util.ArrayList;
import java.util.List;

import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Facilitates undo and redo mechanism in template list.
 */
public class VersionedTemplateList extends TemplateList {
    private List<ReadOnlyTemplateList> templateListStateList;
    private List<UniqueTemplateItems> prevTemplateStateList;
    private List<UniqueTemplateItems> newTemplateStateList;
    private List<Integer> templateIndex;
    private int currentStatePointer = 0;

    public VersionedTemplateList() {
        templateListStateList = new ArrayList<>();
        prevTemplateStateList = new ArrayList<>();
        newTemplateStateList = new ArrayList<>();
        templateIndex = new ArrayList<>();
    }

    /**
     * Saves the current template list state in its history.
     */
    public void commit(ReadOnlyTemplateList templateList, UniqueTemplateItems prevTemplate,
                       UniqueTemplateItems newTemplate, int index) {
        currentStatePointer++;
        int listSize = templateListStateList.size();
        for (int i = currentStatePointer; i < listSize; i++) {
            templateListStateList.remove(i);
            prevTemplateStateList.remove(i);
            newTemplateStateList.remove(i);
            templateIndex.remove(i);
        }
        templateListStateList.add(currentStatePointer, templateList);
        prevTemplateStateList.add(currentStatePointer, prevTemplate);
        newTemplateStateList.add(currentStatePointer, newTemplate);
        templateIndex.add(currentStatePointer, index);
    }

    /**
     * Restores the previous template list state from its history.
     */
    public ReadOnlyTemplateList undo() {
        currentStatePointer--;
        return templateListStateList.get(currentStatePointer);
    }

    /**
     *  Restores a previously undone template list state from its history.
     */
    public ReadOnlyTemplateList redo() {
        currentStatePointer++;
        return templateListStateList.get(currentStatePointer);
    }

    public int getCurrentStatePointer() {
        return currentStatePointer;
    }

    public int getListSize() {
        return templateListStateList.size();
    }

    public UniqueTemplateItems getPrevTemplate() {
        return prevTemplateStateList.get(currentStatePointer);
    }

    public UniqueTemplateItems getNewTemplate() {
        return newTemplateStateList.get(currentStatePointer);
    }

    public Integer getIndex() {
        return templateIndex.get(currentStatePointer);
    }

    /**
     * Adds the first instance of templateList.
     */
    public void add(ReadOnlyTemplateList templateList) {
        templateListStateList.add(templateList);
        prevTemplateStateList.add(null);
        newTemplateStateList.add(null);
        templateIndex.add(-1);
    }
}
