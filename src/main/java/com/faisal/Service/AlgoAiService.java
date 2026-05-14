package com.faisal.Service;

import com.faisal.model.AlgoHistory;
import com.faisal.model.AlgoReviewResponse;
import com.faisal.Repository.AlgoHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// --- CRITICAL SPRING AI IMPORTS ---
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse; // <-- MISSING: Resolves ChatResponse symbol error
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
// ----------------------------------

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;


@Service
public class AlgoAiService {

    private static final Logger log = LoggerFactory.getLogger(AlgoAiService.class);
    private final ChatModel chatModel;
    private final AlgoHistoryRepository repository;

    public AlgoAiService(ChatModel chatModel, AlgoHistoryRepository repository) {
        this.chatModel = chatModel;
        this.repository = repository;
    }

    @Transactional
    public AlgoHistory processAndSaveAlgorithm(String code) {
        log.info("Processing computational assessment profiling via local AI gateway...");

        var converter = new BeanOutputConverter<>(AlgoReviewResponse.class);
        Prompt prompt = createAnalysisPrompt(code, converter.getFormat()); // Fixed long method code smell

        try {
            ChatResponse response = chatModel.call(prompt);

            // Fixed: Removed the redundant inner chain null checks to satisfy the IDE constraint
            if (response == null) {
                throw new RuntimeException("Null response returned from local Ollama service.");
            }

            String rawOutput = response.getResult().getOutput().getContent();


            if (rawOutput == null || rawOutput.trim().isEmpty()) {
                throw new RuntimeException("Extracted AI raw content payload text is null or empty.");
            }

            AlgoReviewResponse parsedResponse = converter.convert(rawOutput);

            AlgoHistory entry = new AlgoHistory();
            entry.setSubmittedCode(code);
            entry.setTimeComplexity(parsedResponse.timeComplexity());
            entry.setSpaceComplexity(parsedResponse.spaceComplexity());
            entry.setOptimizationSuggestion(parsedResponse.optimizationSuggestion());
            entry.setEfficiencyRating(parsedResponse.efficiencyRating());

            return repository.save(entry);
        } catch (Exception ex) {
            log.error("AI pipeline processing failure occurred during structure deserialization", ex);
            throw new RuntimeException("Internal AI mapping pipeline standard constraint exception", ex);
        }
    }

    // Fixed code smell: Extracted prompt configuration logic to independent helper method
    private Prompt createAnalysisPrompt(String code, String formatInstructions) {
        String template = """
            Analyze the execution efficiency metrics for the following Java code snippet.
            Provide the required metrics matching the structural system formatting instructions perfectly.
            Categorize efficiencyRating strictly as one of the following elements: [Excellent, Good, Poor].
            
            Code Block:
            {code}
            
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        return promptTemplate.create(Map.of("code", code, "format", formatInstructions));
    }

    @Transactional(readOnly = true)
    public List<AlgoHistory> getHistory() {
        log.info("Retrieving aggregate algorithmic metric log logs...");
        return repository.findAll();
    }
}

