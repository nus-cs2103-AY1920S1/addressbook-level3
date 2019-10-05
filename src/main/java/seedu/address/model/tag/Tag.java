package seedu.address.model.tag;

import java.util.HashSet;

import seedu.address.model.module.Module;

/**
 * Represents a Tag.
 */
public interface Tag {

    public final HashSet<Module> ATTACHED_MODULES = new HashSet<Module>();

    /**
     * Adds the specified {@code Module} to this tag.
     * @param module The module that is to be added.
     * @return True if the module has been added.
     */
    public default boolean addModule(Module module) {
        return ATTACHED_MODULES.add(module);
    }

    /**
     * Deletes the specified {@code Module} from this tag.
     * @param module The module to be deleted.
     * @return True if the module has been deleted.
     */
    public default boolean deleteModule(Module module) {
        if (!containsModule(module)) {
            return false;
        }
        return ATTACHED_MODULES.remove(module);
    }

    /**
     * Returns all the modules attached to this tag.
     * @return A set of modules.
     */
    public default HashSet<Module> getModules() {
        return ATTACHED_MODULES;
    }

    /**
     * Checks if this tag contains the specified module.
     * @param module The module that is to be checked.
     * @return True if this tag contains the module.
     */
    public default boolean containsModule(Module module) {
        return ATTACHED_MODULES.contains(module);
    }

    public boolean isDefault();

    public boolean canBeRenamed();

}
