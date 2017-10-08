package com.example.chungwei.placetogo.services.apiai;

import android.content.Context;
import android.support.annotation.NonNull;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class APIAIService {

    private AIDataService aiDataService;
    private AIService aiService;

    public APIAIService(@NonNull Context context, @NonNull AIListener listener) {
        final AIConfiguration config = new AIConfiguration("PLEASE_REPLACE_WITH_ACCESS_TOKEN",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiDataService = new AIDataService(context, config);
        aiService = AIService.getService(context, config);
        aiService.setListener(listener);
    }

    // Voice Input
    public void startListening() {
        aiService.startListening();
    }

    public void stopListening() {
        aiService.stopListening();
    }

    public void cancel() {
        aiService.cancel();
    }

    // Text input
    public AIResponse textRequest(@NonNull String query) throws AIServiceException {
        AIRequest request = new AIRequest();
        request.setQuery(query);
        return aiDataService.request(request);
    }
}
