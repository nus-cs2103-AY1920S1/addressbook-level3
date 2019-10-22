package mams.model.module;

import java.util.function.Predicate;

/**
 * Tests if a {@code Module} matches another given {@code Module}
 */
public class SameModulePredicate implements Predicate<Module> {
    private final Module module;

    public SameModulePredicate(Module module) {
        this.module = module;
    }

    @Override
    public boolean test(Module module) {
        return this.module.equals(module);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameModulePredicate // instanceof handles nulls
                && module.equals(((SameModulePredicate) other).module)); // state check
    }
}
