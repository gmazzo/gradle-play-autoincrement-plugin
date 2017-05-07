package com.github.gmazzo.gradle.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import org.gradle.api.Project

public class APIAccessor {
    private final Project project
    private final PlayAutoincrementPluginExtension extension
    private final AppExtension android
    private final ApplicationVariant variant
    private AndroidPublisher publisher

    public APIAccessor(Project project, PlayAutoincrementPluginExtension extension, AppExtension android, ApplicationVariant variant) {
        this.project = project
        this.extension = extension
        this.android = android
        this.variant = variant
    }

    public AndroidPublisher getPublisher() throws IOException {
        if (publisher == null) {
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

            publisher = new AndroidPublisher.Builder(scoped.transport, scoped.jsonFactory, scoped)
                    .setApplicationName('play-autoincrement-plugin')
                    .build()
        }
        return publisher;
    }

    /**
     * Adds support for retrieving configuration from 'com.github.triplet.play' plugin
     */
    private boolean tryFromPlayPlugin() {
        if (project.plugins.hasPlugin('com.github.triplet.play')) {
            def extension = project.extensions.getByType(Class.forName('de.triplet.gradle.play.PlayPublisherPluginExtension'))

            def flavorAccountConfig = variant.productFlavors.find {
                it.playAccountConfig
            }?.playAccountConfig
            def defaultAccountConfig = android.defaultConfig.playAccountConfig
            def playAccountConfig = flavorAccountConfig ?: defaultAccountConfig

            publisher = Class.forName('de.triplet.gradle.play.AndroidPublisherHelper').init(extension, playAccountConfig)
            return true
        }
        return false
    }

}
