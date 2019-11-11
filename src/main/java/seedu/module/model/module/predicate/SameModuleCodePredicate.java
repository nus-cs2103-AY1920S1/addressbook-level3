package seedu.module.model.module.predicate;

import java.util.function.Predicate;

import seedu.module.model.module.Module;

/**
 * Tests that a {@code Module}'s moduleCode matches the moduleCode given.
 */
public class SameModuleCodePredicate implements Predicate<Module> {
    private final String moduleCode;

    public SameModuleCodePredicate(String moduleCode) {
        assert !moduleCode.equals("") : "SameModuleCodePredicate received an empty String.";
        this.moduleCode = moduleCode.toLowerCase();
    }

    @Override
    public boolean test(Module module) {
        return module.getModuleCode().toLowerCase().equals(moduleCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameModuleCodePredicate // instanceof handles nulls
                && moduleCode.equals(((SameModuleCodePredicate) other).moduleCode)); // state check
    }

}
