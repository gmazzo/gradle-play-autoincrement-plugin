package com.github.gmazzo.gradle.plugins.api

import com.github.gmazzo.gradle.plugins.PlayAutoincrementPluginExtension
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes

class ExtensionAPIAccessor extends APIAccessor {
    private final PlayAutoincrementPluginExtension extension

    public ExtensionAPIAccessor(PlayAutoincrementPluginExtension extension) {
        this.extension = extension
    }

    @Override
    AndroidPublisher providePublisher() {
        File jsonFile = extension.jsonFile

        if (jsonFile == null) {
            if (tryFromPlayPlugin()) {
                return publisher;
            }

            throw new IllegalArgumentException("Cannot connect with PlayStore API: no 'jsonFile' was provided")

        } else if (!jsonFile.exists()) {
            throw new IllegalArgumentException("Cannot connect with PlayStore API: '${jsonFile.absolutePath}' does not exists")
        }

        GoogleCredential credential = GoogleCredential.fromStream(jsonFile.newInputStream())
        Credential scoped = credential.createScoped([AndroidPublisherScopes.ANDROIDPUBLISHER])

        return new AndroidPublisher.Builder(scoped.transport, scoped.jsonFactory, scoped)
                .setApplicationName('play-autoincrement-plugin')
                .build()
    }

}
