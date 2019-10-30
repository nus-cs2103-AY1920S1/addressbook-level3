//@@author e0031374
package tagline.logic.parser.group;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import tagline.commons.core.Messages;
import tagline.commons.core.index.Index;
import tagline.commons.util.StringUtil;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.group.MemberId;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class GroupParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses a {@code String name} into a {@code GroupName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static GroupName parseGroupName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!GroupName.isValidGroupName(trimmedName)) {
            throw new ParseException(GroupName.MESSAGE_CONSTRAINTS);
        }
        return new GroupName(trimmedName);
    }

    /**
     * Parses {@code Collection<String> nameSet} into a {@code Set<GroupName>}.
     */
    public static Set<GroupName> parseGroupNames(Collection<String> names) throws ParseException {
        requireNonNull(names);
        final Set<GroupName> nameSet = new HashSet<>();
        for (String id : names) {
            nameSet.add(parseGroupName(id));
        }
        return nameSet;
    }

    /**
     * Parses {@code Collection<String> groupNames} into a {@code Set<GroupName>} if {@code groupNames} is non-empty.
     * If {@code groupNames} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<GroupName>} containing zero groupNames.
     */
    public static Optional<Set<GroupName>> parseGroupNamesForSearch(Collection<String> groupNames)
        throws ParseException {
        assert groupNames != null;

        if (groupNames.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> nameSet = groupNames.size() == 1 && groupNames.contains("")
                ? Collections.emptySet() : groupNames;
        return Optional.of(GroupParserUtil.parseGroupNames(nameSet));
    }

    /**
     * Parses {@code targetGroupName} into a {@code GroupNameEqualsKeywordPredicate} if {@code targetGroupName}
     * is tested to be a valid {@code GroupName}, predicate contains only one element to check for
     */
    public static GroupNameEqualsKeywordPredicate stringsToGroupNamePredicate(String targetGroupName)
        throws ParseException {

        Optional<Set<GroupName>> optNameSet = GroupParserUtil.parseGroupNamesForSearch(Arrays.asList(targetGroupName));
        if (optNameSet.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_INVALID_GROUP_NAME + ": " + targetGroupName);
        }
        return new GroupNameEqualsKeywordPredicate(optNameSet.get());
    }

    /**
     * Parses a {@code String description} into a {@code GroupDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static GroupDescription parseGroupDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!GroupDescription.isValidGroupDescription(trimmedDescription)) {
            throw new ParseException(GroupDescription.MESSAGE_CONSTRAINTS);
        }
        return new GroupDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static MemberId parseMemberId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!MemberId.isValidMemberId(trimmedId)) {
            throw new ParseException(MemberId.MESSAGE_CONSTRAINTS);
        }
        return new MemberId(trimmedId);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<MemberId> parseMemberIds(Collection<String> ids) throws ParseException {
        requireNonNull(ids);
        final Set<MemberId> memberSet = new HashSet<>();
        for (String id : ids) {
            memberSet.add(parseMemberId(id));
        }
        return memberSet;
    }

    /**
     * Parses {@code Collection<String> memberIds} into a {@code Set<MemberId>} if {@code memberIds} is non-empty.
     * If {@code memberIds} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<MemberId>} containing zero memberIds.
     */
    public static Optional<Set<MemberId>> parseMemberIdsForEdit(Collection<String> memberIds) throws ParseException {
        assert memberIds != null;

        if (memberIds.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = memberIds.size() == 1 && memberIds.contains("")
                ? Collections.emptySet() : memberIds;
        return Optional.of(GroupParserUtil.parseMemberIds(tagSet));
    }

}
