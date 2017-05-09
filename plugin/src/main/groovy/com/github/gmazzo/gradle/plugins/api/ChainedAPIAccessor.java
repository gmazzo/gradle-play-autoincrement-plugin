package com.github.gmazzo.gradle.plugins.api;

import com.google.api.services.androidpublisher.AndroidPublisher;

import java.io.IOException;

public class ChainedAPIAccessor extends APIAccessor {
    private final APIAccessor accessors[];

    public static ChainedAPIAccessor of(APIAccessor... accessors) {
        return new ChainedAPIAccessor(accessors);
    }

    private ChainedAPIAccessor(APIAccessor... accessors) {
        this.accessors = accessors;
    }

    @Override
    protected AndroidPublisher providePublisher() throws IOException {
        for (int i = 0; i < accessors.length; i++) {
            try {
                AndroidPublisher publisher = accessors[i].providePublisher();
                if (publisher != null) {
                    return publisher;
                }

            } catch (Exception e) {
                if (i >= accessors.length - 1) {
                    throw e;
                }
            }
        }
        throw new IllegalStateException("No accessors found!");
    }

}
