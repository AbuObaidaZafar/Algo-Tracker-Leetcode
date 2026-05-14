package com.faisal.Controller;

import com.faisal.model.AlgoHistory;
import com.faisal.Service.AlgoAiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/algorithms")
public class AlgoRestController {

    private final AlgoAiService aiService;

    public AlgoRestController(AlgoAiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<AlgoHistory> analyzeAlgorithm(@RequestBody String codePayload) {
        if (codePayload == null || codePayload.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        AlgoHistory resultRecord = aiService.processAndSaveAlgorithm(codePayload);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultRecord);
    }

    @GetMapping("/history")
    public ResponseEntity<List<AlgoHistory>> getHistoryAuditLogs() {
        return ResponseEntity.ok(aiService.getHistory());
    }
}
