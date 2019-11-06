package io.xpire.model.state;

import io.xpire.model.CloneModel;
import io.xpire.model.Model;
import io.xpire.model.item.sort.XpireMethodOfSorting;

/**
 * An interface State that defines the clone method for modified/filtered states.
 */
public interface State {

    CloneModel clone(Model model, XpireMethodOfSorting method);

    CloneModel getCloneModel();

    XpireMethodOfSorting getMethod();

}
