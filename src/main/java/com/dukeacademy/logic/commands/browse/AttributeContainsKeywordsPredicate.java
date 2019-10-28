package com.dukeacademy.logic.commands.browse;

import java.util.List;
import java.util.function.Predicate;

import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.model.question.Question;

/**
 * Tests that a {@code Question}'s {@code Attribute} matches any of the keywords
 * given.
 */
public class AttributeContainsKeywordsPredicate implements Predicate<Question> {
    private final List<String> keywords;

    /**
     * Instantiates a new Attribute contains keywords predicate.
     *
     * @param keywords the keywords
     */
    public AttributeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Question question) {
        return keywords.stream()
                       .anyMatch(keyword -> {
                           boolean matchTitle =
                               StringUtil.containsWordIgnoreCase(question.getTitle(), keyword);
                           boolean matchDescription =
                               StringUtil.containsWordIgnoreCase(question.getDescription(), keyword);
                           boolean matchStatus =
                               StringUtil.containsWordIgnoreCase(question.getStatus().toString(), keyword);
                           boolean matchDifficulty =
                               StringUtil.containsWordIgnoreCase(question.getDifficulty().toString(), keyword);
                           boolean matchTopic =
                               question.getTopics().stream().anyMatch(topic->
                                   StringUtil.containsWordIgnoreCase(topic.toString(), keyword));
                           return matchTitle || matchDescription || matchStatus || matchDifficulty || matchTopic;
                       });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AttributeContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((AttributeContainsKeywordsPredicate) other).keywords)); // state check
    }
}
