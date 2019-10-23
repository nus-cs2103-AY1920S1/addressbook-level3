package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SEMESTER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.PrereqLeaf;
import seedu.address.model.PrereqNode;
import seedu.address.model.PrereqTree;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code semester} into an {@code SemesterName} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if specified semester is not valid.
     */
    public static SemesterName parseSemester(String semester) throws ParseException {
        String trimmedSemester = semester.trim().toUpperCase();
        if (!SemesterName.isValidSemesterName(trimmedSemester)) {
            throw new ParseException(MESSAGE_INVALID_SEMESTER);
        }
        int year = Character.getNumericValue(trimmedSemester.charAt(1));
        int sem = Character.getNumericValue(trimmedSemester.charAt(trimmedSemester.length() - 1));
        if (trimmedSemester.length() == 4) {
            return SemesterName.getEnum(year, sem);
        } else {
            return SemesterName.getSpecialTermEnum(year, sem);
        }
    }

    /**
     * Checks whether or not the module is valid.
     *
     * @throws ParseException if specified module is not valid.
     */
    public static String parseModule(String module) throws ParseException {
        // TODO: Check module against megaList to ensure that it is valid
        return module.toUpperCase().trim();
    }

    /**
     * Parses a raw {@code String tagName} into a formatted tagName.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tagName} is invalid.
     */
    public static String parseTag(String tagName) throws ParseException {
        requireNonNull(tagName);
        String trimmedTagName = tagName.trim();
        if (!Tag.isValidTagName(trimmedTagName)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return trimmedTagName;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<String> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<String> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses the prerequisite tree from a given string.
     * @param s Given string representing the prerequisite tree. Either the empty string, a module code, or (OP _ _).
     * @return Prerequisite tree
     */
    public static PrereqTree parsePrereqTree(String s) {
        if ("".equals(s)) {
            return null;
        } else if (s.charAt(0) != '(') {
            return new PrereqLeaf(s);
        }

        // Split the string into list of items
        String removeBrackets = s.substring(1, s.length() - 1);
        String[] operatorOperands = removeBrackets.split(" ", 2);
        String operator = operatorOperands[0];
        String operandsString = operatorOperands[1];
        List<String> operands = splitOperands(operandsString);
        List<PrereqTree> children = operands.stream()
                .map(operand -> parsePrereqTree(operand))
                .collect(Collectors.toList());
        return new PrereqNode(operator, children);
    }

    /**
     * Splits the String of operands into its logical groupings.
     * Example: "CS1 (AND (OR CS2 CS3) CS4) CS5" => ["CS1", "(AND (OR CS2 CS3) CS4)", "CS5"]
     *
     * @param operands String that represents the operands all together
     * @return List of Strings, where each represents a single operand to be further parsed
     */
    public static List<String> splitOperands(String operands) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int balance = 0;
        for (char c : operands.toCharArray()) {
            sb.append(c);
            if (c == ' ' && balance == 0) {
                String trimmed = sb.toString().trim();
                if (!"".equals(trimmed)) {
                    list.add(trimmed);
                }
                sb.setLength(0);
            } else if (c == '(') {
                balance++;
            } else if (c == ')' && --balance == 0) {
                list.add(sb.toString().trim());
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }
}
