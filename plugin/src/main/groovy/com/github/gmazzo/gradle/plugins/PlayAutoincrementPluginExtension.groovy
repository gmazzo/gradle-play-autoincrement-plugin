package com.github.gmazzo.gradle.plugins

import com.android.build.gradle.api.ApplicationVariant

class PlayAutoincrementPluginExtension {

    /**
     * The JSON credential file downloaded from Google Console API project
     */
    File jsonFile

    /**
     * A callback for providing an {@link java.io.InputStream} of the JSON PlayStore API key.
     */
    Closure<InputStream> jsonStream = {
        if (jsonFile == null) {
            throw new IllegalArgumentException("Cannot connect with PlayStore API: no 'jsonFile' was provided")

        } else if (!jsonFile.exists()) {
            throw new IllegalArgumentException("Cannot connect with PlayStore API: '${jsonFile.absolutePath}' does not exists")
        }

        return new FileInputStream(jsonFile)
    }

    /**
     * A callback for computing the next versionCode. Defaults to 'current + 1'.
     */
    Closure<Integer> codeFormatter = { int code, ApplicationVariant variant -> code + 1 }

    /**
     * A callback for computing the next versionName. By default appends '.version' to the current name. Set it to 'null' to disable this feature
     */
    Closure<String> nameFormatter = { int code, ApplicationVariant variant -> "${variant.versionName}.$code" }

    /**
     * A callback for determining which variants this target should apply. By default, only targets releases
     */
    Closure<Boolean> targetVariants = { ApplicationVariant variant -> !variant.buildType.debuggable }

    boolean failOnErrors = false

    void jsonFile(File file) {
        jsonFile = file
    }

    void jsonFile(String file) {
        jsonFile(new File(file))
    }

    void jsonStream(Closure<InputStream> jsonStream) {
        this.jsonStream = jsonStream
    }

    void codeFormatter(Closure<Integer> codeFormatter) {
        this.codeFormatter = codeFormatter
    }

    void nameFormatter(Closure<String> nameFormatter) {
        this.nameFormatter = nameFormatter
    }

    void targetVariants(Closure<Boolean> targetVariants) {
        this.targetVariants = targetVariants
    }

    void failOnErrors(boolean failOnErrors) {
        this.failOnErrors = failOnErrors
    }

}
