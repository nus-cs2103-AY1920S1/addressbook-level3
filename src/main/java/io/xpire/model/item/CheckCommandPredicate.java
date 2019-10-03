package io.xpire.model.item;

import java.util.function.Predicate;

/**
 * Predicate class for CheckCommand..
 */
public abstract class CheckCommandPredicate implements Predicate<Item> {

    @Override
    public abstract boolean test(Item item);
}
