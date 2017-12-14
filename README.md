# gradle-play-autoincrement-plugin
An Android-Gradle plugin to set the build version based on the last APK uploaded on PlayStore

## Usage
On your `build.gradle` add:
```groovy
buildscript {
    repositories {
        maven { url 'https://plugins.gradle.org/m2/' }
    }
    dependencies {
        classpath 'gradle.plugin.gmazzo:play-autoincrement-plugin:+'
    }
}

apply plugin: 'com.github.gmazzo.play-autoincrement'
```
Visit [Gradle plugins](https://plugins.gradle.org/plugin/com.github.gmazzo.play-autoincrement) for further details on how to apply it

The plugin creates one task per `build-variant. In a simple Android project you will have:
* <code>computeNext<i>Debug</i>Version</code>
* <code>computeNext<i>Release</i>Version</code>

Be aware of those tasks will run in a **very early** step on your Gradle build (between <code>pre<i>Release</i>Build</code> and <code>check<i>Release</i>Manifest</code> tasks).
If your Gradle Sync fails on Android Studio (due an Internet unavailability for example), see the section [Fail on errors](#fail-on-errors)

## Configuration
On your `build.gradle` add:
```groovy
autoincrement {
    jsonFile '<path-to-google-api-json-token>'
}
```
### Working with Triple-T/gradle-play-publisher
If you have configured the [gradle-play-publisher](https://github.com/Triple-T/gradle-play-publisher#authentication) (or going to), then you **don't need to add the `autoincrement` closure** the Gradle script. Just apply the plugin that it will pick the configurtion from Triple-T's `play` closure.

### Advanced configuration
This plugin supports low-level GoogleCredentials API by providing an InputStream with the JSON API Key content:
```groovy
autoincrement {
    jsonStream 'http://myserver/google-api-key.json'.toURL().openStream()
}
```

### Target specific variants
By default, the plugin will only target any non-debuggable build variant.
You can customize this behaviour with the `targetVariants` closure.

For example, an hypothetical `flavor1Release` variant only do:
```groovy
autoincrement {
    targetVariants { variant -> variant.name == 'flavor1Release' }
}
```

## Customization
### Custom logic for version code calculation:
```groovy
autoincrement {
    codeFormatter { code, variant -> code + 1 }
}
```
Set it to `null` for leave the versionCode untouched

### Custom logic for version naming:
```groovy
autoincrement {
    nameFormatter { code, variant -> "${variant.versionName}.$code-${new Date().format('yyyyMMdd-HHmmss')}" }
}
```
Set it to `null` for leave the versionName untouched

## Fail on errors
By default, the plugin will fail on any error and leave the version untouched. You can change this by setting:
```groovy
autoincrement {
    failOnErrors false
}
```
