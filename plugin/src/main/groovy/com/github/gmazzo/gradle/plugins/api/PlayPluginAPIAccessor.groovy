package com.github.gmazzo.gradle.plugins.api

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import com.google.api.services.androidpublisher.AndroidPublisher
import org.gradle.api.Project

/**
 * Adds support for retrieving configuration from 'com.github.triplet.play' plugin
 */
public class PlayPluginAPIAccessor extends APIAccessor {
    private final Project project
    private final AppExtension android
    private final ApplicationVariant variant

    public PlayPluginAPIAccessor(Project project, AppExtension android, ApplicationVariant variant) {
        this.project = project
        this.android = android
        this.variant = variant
    }

    @Override
    protected AndroidPublisher providePublisher() {
        if (project.plugins.hasPlugin('com.github.triplet.play')) {
            def extension = project.extensions.getByType(Class.forName('de.triplet.gradle.play.PlayPublisherPluginExtension'))

            def flavorAccountConfig = variant.productFlavors.find {
                it.playAccountConfig
            }?.playAccountConfig
            def defaultAccountConfig = android.defaultConfig.playAccountConfig
            def playAccountConfig = flavorAccountConfig ?: defaultAccountConfig

            return Class.forName('de.triplet.gradle.play.AndroidPublisherHelper').init(extension, playAccountConfig)
        }
        throw new IllegalStateException('Plugin com.github.triplet.play is not applied!')
    }

}
