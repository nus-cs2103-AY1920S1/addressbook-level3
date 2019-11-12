package io.xpire.model.state;

import java.util.function.Predicate;

import io.xpire.model.CloneModel;
import io.xpire.model.ListType;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.item.sort.XpireMethodOfSorting;

/**
 * An abstract class State that cannot be instantiated.
 * @@author Kalsyc
 */
public abstract class State {

    /**
     * Enum that defines the type of State.
     */
    public enum StateType { FILTERED, MODIFIED }

    protected XpireMethodOfSorting method;
    protected Predicate<? extends Item> predicate;
    protected CloneModel cloneModel;
    protected ListType listType;
    protected StateType stateType;

    protected CloneModel clone(Model model, StateType stateType) {
        return new CloneModel(model.getXpire(), model.getReplenishList(), model.getUserPrefs(), stateType);
    }

    @SuppressWarnings("unchecked")
    protected void build(Model model) {
        this.method = model.getXpire().getMethodOfSorting();
        this.predicate = (Predicate<? extends Item>) model.getCurrentList().getPredicate();
        this.listType = model.getCurrentView();
    }

    public CloneModel getCloneModel() {
        return this.cloneModel;
    }

    public XpireMethodOfSorting getMethod() {
        return this.method;
    }

    public ListType getListType() {
        return listType;
    }

    public Predicate<? extends Item> getPredicate() {
        return predicate;
    }

}
