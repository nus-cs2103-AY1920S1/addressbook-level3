package seedu.address.model;

/**
 * Deals with the description of the product to be delivered.
 */

public class Goods {

    private Description description;

    public Goods(Description description) {
        this.description = description;
    }

    /**
     * Edits the Name of the goods.
     *
     * @param newDescription The new name to be changed into.
     */

    public void setName(Description newDescription) {
        this.description = newDescription;
    }


    @Override
    public String toString() {
        return description.toString();
    }
}
