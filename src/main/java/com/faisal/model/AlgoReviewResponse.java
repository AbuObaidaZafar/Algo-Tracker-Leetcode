package com.faisal.model;

public record AlgoReviewResponse(
        String timeComplexity,
        String spaceComplexity,
        String optimizationSuggestion,
        String efficiencyRating
) {}
