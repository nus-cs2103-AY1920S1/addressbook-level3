package seedu.address.logic.commands.suggestions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ArgumentList;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.CommandArgument;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Provides suggestions for the {@link Prefix}es of the {@link seedu.address.logic.commands.ShowNusModCommand}.
 */
public class ShowNusModCommandSuggester extends NusModSuggester {
    public static final List<Prefix> SUPPORTED_PREFIXES = List.of(
            CliSyntax.PREFIX_MODULE_CODE,
            CliSyntax.PREFIX_ACAD_YEAR,
            CliSyntax.PREFIX_SEMESTER
    );

    @Override
    protected List<String> provideSuggestions(
            final Model model, final ArgumentList arguments, final CommandArgument commandArgument) {
        final Prefix prefix = commandArgument.getPrefix();
        final String value = commandArgument.getValue();
        final List<Predicate<Module>> predicates = new ArrayList<>(3);
        ModuleSuggestionMapper mapper = null;

        if (prefix.equals(CliSyntax.PREFIX_MODULE_CODE)) {
            final String moduleCodeValue = value;

            if (!moduleCodeValue.isBlank()) {
                predicates.add(createPredicateModuleCodeContains(moduleCodeValue));
            }

            arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_ACAD_YEAR)
                    .map(NusModSuggester::createPredicateModuleAcadYearContains)
                    .ifPresent(predicates::add);

            arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_SEMESTER)
                    .map(NusModSuggester::createPredicateModuleSemesterNoContains)
                    .ifPresent(predicates::add);

            mapper = MODULE_CODE_MAPPER;
        } else if (prefix.equals(CliSyntax.PREFIX_ACAD_YEAR)) {
            getSelectedModule(model, arguments).ifPresent(matchedModule -> {
                predicates.add(matchedModule::equals);
            });

            arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_SEMESTER)
                    .map(NusModSuggester::createPredicateModuleSemesterNoContains)
                    .ifPresent(predicates::add);

            mapper = ACAD_YEAR_MAPPER;
        } else if (prefix.equals(CliSyntax.PREFIX_SEMESTER)) {
            getSelectedModule(model, arguments).ifPresent(matchedModule -> {
                predicates.add(matchedModule::equals);
            });

            arguments.getFirstValueOfPrefix(CliSyntax.PREFIX_ACAD_YEAR)
                    .map(NusModSuggester::createPredicateModuleAcadYearContains)
                    .ifPresent(predicates::add);

            mapper = SEMESTER_MAPPER;
        }

        if (mapper == null) {
            assert predicates.isEmpty() : "Some predicates exist but a mapper has not been set";
            return null;
        }

        final Predicate<Module> mergedPredicate = predicates.stream().reduce(module -> true, Predicate::and);

        return model
                .getModuleList()
                .asUnmodifiableObservableList()
                .stream()
                .filter(mergedPredicate)
                .flatMap(mapper)
                .distinct()
                .collect(Collectors.toUnmodifiableList());
    }

}
