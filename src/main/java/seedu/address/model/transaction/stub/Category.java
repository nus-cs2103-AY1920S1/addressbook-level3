package seedu.address.model.transaction.stub;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Category {
    public static final String MESSAGE_CONSTRAINTS =
            "Category should not be null.";

    public String category;

    public Category(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.category = description;
    }

    boolean isValidDescription(String description){
        return description != null;
    }

    @Override
    public String toString(){
        return category;
    }

    @Override
    public boolean equals(Object other){
        return other == this
                || (other instanceof Category && category == ((Category) other).category);
    }

}
