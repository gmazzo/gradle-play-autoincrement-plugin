package com.github.gmazzo.gradle.plugins.api

import com.google.api.services.androidpublisher.AndroidPublisher

public class ChainedAPIAccessor extends APIAccessor {
    private final List<APIAccessor> accessors

    public static ChainedAPIAccessor of(APIAccessor... accessors) {
        return new ChainedAPIAccessor(accessors)
    }

    private ChainedAPIAccessor(APIAccessor... accessors) {
        this.accessors = accessors
    }

    @Override
    AndroidPublisher providePublisher() {
        for (Iterator<APIAccessor> it = accessors.iterator(); it.hasNext();) {
            try {
                def publisher = ++it.providePublisher()
                if (publisher) {
                    return publisher
                }

            } catch (Exception e) {
                if (!it.hasNext()) {
                    throw e
                }
            }
        }
        throw new IllegalStateException('No accessors found!')
    }

}
