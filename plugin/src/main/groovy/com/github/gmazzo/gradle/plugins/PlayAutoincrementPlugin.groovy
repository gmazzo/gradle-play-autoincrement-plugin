package com.github.gmazzo.gradle.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.github.gmazzo.gradle.plugins.api.CombinedAPIAccessor
import com.github.gmazzo.gradle.plugins.api.ExtensionAPIAccessor
import com.github.gmazzo.gradle.plugins.api.PlayPluginAPIAccessor
import com.github.gmazzo.gradle.plugins.tasks.ComputeNextVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PlayAutoincrementPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (!project.plugins.any { p -> p instanceof AppPlugin }) {
            throw new IllegalStateException('The \'com.android.application\' plugin is required.')
        }

        def android = project.extensions.getByType(AppExtension)
        def extension = project.extensions.create('autoincrement', PlayAutoincrementPluginExtension)
        def extensionAccessor = new ExtensionAPIAccessor(extension)

        android.applicationVariants.all { ApplicationVariant variant ->
            if (extension.targetVariants.call(variant)) {
                def variantName = variant.name.capitalize()
                def taskName = "computeNext${variantName}Version"

                def task = project.tasks.create(taskName, ComputeNextVersionTask)
                task.extension = extension
                task.variant = variant
                task.accessor = CombinedAPIAccessor.of(
                        new PlayPluginAPIAccessor(project, android, variant),
                        extensionAccessor)
                task.dependsOn variant.preBuild

                variant.checkManifest.dependsOn task
            }
        }
    }

}
