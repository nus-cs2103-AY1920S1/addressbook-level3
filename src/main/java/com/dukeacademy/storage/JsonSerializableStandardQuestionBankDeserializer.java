package com.dukeacademy.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.storage.question.JsonAdaptedQuestion;
import com.dukeacademy.storage.question.JsonAdaptedTestCase;
import com.dukeacademy.storage.question.JsonAdaptedUserProgram;
import com.dukeacademy.storage.question.JsonSerializableStandardQuestionBank;
import com.dukeacademy.ui.QuestionListPanel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * The type Json serializable standard question bank deserializer.
 */
class JsonSerializableStandardQuestionBankDeserializer extends StdDeserializer<JsonSerializableStandardQuestionBank> {

    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    /**
     * Instantiates a new Json serializable standard question bank deserializer.
     */
    public JsonSerializableStandardQuestionBankDeserializer() {
        this(null);
    }

    private JsonSerializableStandardQuestionBankDeserializer(Class<?> vc) {
        super(vc);
    }

    // return JsonSerializableStandardQuestionBank
    @Override public JsonSerializableStandardQuestionBank deserialize(JsonParser p,
                                                     DeserializationContext ctxt)
        throws IOException {
        // zj - use SLAP to abstract out the deserialization process.
        JsonNode jsonQuestionBankNode = p.getCodec().readTree(p);
        ArrayNode rawJsonAdaptedQuestionList =
            (ArrayNode) jsonQuestionBankNode.get("questions");

        List<JsonAdaptedQuestion> jsonAdaptedQuestionList = new ArrayList<>();
        for (int i = 0; i < rawJsonAdaptedQuestionList.size(); i++) {
            JsonNode jsonAdaptedQuestionNode = rawJsonAdaptedQuestionList.get(i);
            String title = jsonAdaptedQuestionNode.get("title").textValue();
            String status = jsonAdaptedQuestionNode.get("status").textValue();
            String difficulty = jsonAdaptedQuestionNode.get("difficulty").textValue();
            Boolean isBookmarked =
                jsonAdaptedQuestionNode.get("isBookmarked").textValue().equals("true");
            ArrayNode rawTopicList = (ArrayNode) jsonAdaptedQuestionNode.get(
                "topic");
            List<String> topicList = new ArrayList<>();
            for (int j = 0; j < rawTopicList.size(); j++) {
                String topic = rawTopicList.get(j).textValue();
                topicList.add(topic);
            }
            String description = jsonAdaptedQuestionNode.get("description").textValue();
            ArrayNode rawTestCaseList = (ArrayNode) jsonAdaptedQuestionNode.get("testCases");
            ArrayNode rawUserProgram = (ArrayNode) jsonAdaptedQuestionNode.get(
                "userProgram");
            String className = rawUserProgram.get("canonicalName").textValue();
            logger.info("class name is:" + className);
            String sourceCode = rawUserProgram.get("sourceCode").textValue();
            JsonAdaptedUserProgram userProgram =
                new JsonAdaptedUserProgram(className,
                sourceCode);

            List<JsonAdaptedTestCase> testCaseList = new ArrayList<>();
            for (int k = 0; k < rawTestCaseList.size(); k++) {
                String input = rawTestCaseList.get(k).get("input").textValue();
                String expectedResult =
                    rawTestCaseList.get(k).get("expectedResult").textValue();
                JsonAdaptedTestCase testCase =
                    new JsonAdaptedTestCase(input, expectedResult);
                testCaseList.add(testCase);
            }
            JsonAdaptedQuestion jsonAdaptedQuestion =
                new JsonAdaptedQuestion(title, status, difficulty, isBookmarked, topicList,
                    testCaseList, userProgram, description);
            jsonAdaptedQuestionList.add(jsonAdaptedQuestion);
        }
        JsonSerializableStandardQuestionBank jsonSerializableStandardQuestionBank =
            new JsonSerializableStandardQuestionBank(jsonAdaptedQuestionList);
        return jsonSerializableStandardQuestionBank;
    }
}
