package seedu.address.logic.commands.suggestions;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Abstract Suggester class that simplifies the handling and supplying of suggestions from NUSMods module data.
 */
abstract class NusModSuggester extends Suggester {
    protected static final ModuleSuggestionMapper MODULE_CODE_MAPPER = (module -> {
        return Stream.of(module.getModuleCode().toString());
    });
    protected static final ModuleSuggestionMapper ACAD_YEAR_MAPPER = (module -> {
        return Stream.of(module.getAcadYear().toString());
    });
    protected static final ModuleSuggestionMapper SEMESTER_MAPPER = (module -> {
        return module
                .getSemesterData()
                .stream()
                .map(Semester::getSemesterNo)
                .map(SemesterNo::toString);
    });

    /**
     * Finds a specific {@link Module} by its {@link seedu.address.model.module.ModuleCode}.
     *
     * @param model      The {@link Model} containing the {@link Module}s that will be searched through.
     * @param moduleCode The exact {@link String} representation of the {@link seedu.address.model.module.ModuleCode}.
     * @return Returns a {@link Module} if there is one that matches the {@code moduleCode}.
     */
    protected static Optional<Module> findModuleByCode(final Model model, final String moduleCode) {
        final ModuleCode moduleCodeObj;
        try {
            moduleCodeObj = ParserUtil.parseModuleCode(moduleCode);
        } catch (ParseException e) {
            return Optional.empty();
        }

        final AcadYear acadYear = model.getAcadYear();
        final ModuleId moduleId = new ModuleId(acadYear, moduleCodeObj);

        try {
            return Optional.of(model.getModuleList().findModule(moduleId));
        } catch (ModuleNotFoundException mnfe) {
            return Optional.empty();
        }
    }

    /**
     * Gets the specific {@link Module} that was specified by the user through the {@link CliSyntax#PREFIX_MODULE_CODE}.
     *
     * @param model     The {@link Model} containing the {@link Module}s that will be searched through.
     * @param arguments The {@link ArgumentList} possibly containing a {@link CliSyntax#PREFIX_MODULE_CODE} and some
     *                  user-defined value.
     * @return Returns a {@link Module} if the user specified a valid module code.
     */
    protected static Optional<Module> getSelectedModule(final Model model, final ArgumentList arguments) {
        return arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_MODULE_CODE).flatMap(moduleCode -> {
            return findModuleByCode(model, moduleCode);
        });
    }

    /**
     * Creates a {@link Predicate} that checks whether the {@link Module}'s module code contains the {@code searchTerm}.
     *
     * @param searchTerm The user-defined module code substring to search for.
     * @return A {@link Predicate} that checks whether the {@link Module}'s module code contains the {@code searchTerm}.
     */
    protected static Predicate<Module> createPredicateModuleCodeContains(final String searchTerm) {
        return (module) -> {
            return module.getModuleCode().toString().contains(searchTerm);
        };
    }

    /**
     * Creates a {@link Predicate} that checks whether the {@link Module}'s academic years contains the
     * {@code searchTerm}.
     *
     * @param searchTerm The user-defined academic year substring to search for.
     * @return A {@link Predicate} that checks whether the {@link Module}'s academic years contains the
     * {@code searchTerm}.
     */
    protected static Predicate<Module> createPredicateModuleAcadYearContains(final String searchTerm) {
        return (module) -> {
            return module.getAcadYear().toString().contains(searchTerm);
        };
    }

    /**
     * Creates a {@link Predicate} that checks whether the {@link Module}'s semester numbers contains the
     * {@code searchTerm}.
     *
     * @param searchTerm The user-defined semester number to search for.
     * @return A {@link Predicate} that checks whether the {@link Module}'s semester numbers contains the
     * {@code searchTerm}.
     */
    protected static Predicate<Module> createPredicateModuleSemesterNoContains(final String searchTerm) {
        return (module) -> {
            return module.getSemesterData().stream().anyMatch(semester -> {
                return semester.getSemesterNo().semesterNo().contains(searchTerm);
            });
        };
    }

    /**
     * A mapper function that takes in a {@link Module} and returns one or more {@link String} suggestions using a
     * {@link Stream}.
     */
    @FunctionalInterface
    protected interface ModuleSuggestionMapper extends Function<Module, Stream<String>> {
    }
}
