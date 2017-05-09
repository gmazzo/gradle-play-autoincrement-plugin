# gradle-play-autoincrement-plugin
An Android-Gradle plugin to set the build version based on the last APK uploaded on PlayStore

## Import
On your `build.gradle` add:
```
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
Visit [Gradle plugins](https://plugins.gradle.org/plugin/com.github.gmazzo.play-autoincrement) for further details

## Configuration
On your `build.gradle` add:
```
autoincrement {
    jsonFile '<path-to-google-api-json-token>'
}
```

NOTE: this plugin will inherit its configuration from [gradle-play-publisher](https://github.com/Triple-T/gradle-play-publisher) if you are using it

### Target specific variants
By default, the plugin will only target any non-debuggable build variant.
You can customize this behaviour with the `targetVariants` closure.

For example, an hypothetical `flavor1Release` variant only do:
```
autoincrement {
    targetVariants { variant -> variant.name == 'flavor1Release' }
}
```

## Customization
### Custom logic for version code calculation:
```
autoincrement {
    codeFormatter { code, variant -> code + 1 }
}
```
Set it to `null` for leave the versionCode untouched

### Custom logic for version naming:
```
autoincrement {
    nameFormatter { code, variant -> "${variant.versionName}.$code" }
}
```
Set it to `null` for leave the versionName untouched

## Fail on errors
By default, the plugin will ignore any error and leave the version untouched. You can change this by setting:
```
autoincrement {
    failOnErrors true
}
```
