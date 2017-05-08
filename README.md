# gradle-play-autoincrement-plugin
An Android-Gradle plugin to set the build version based on the last APK uploaded on PlayStore

## Import
On your `build.gradle` add:
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.gmazzo.gradle.plugins:play-autoincrement-plugin:0.1'
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

## Customization
Define a custom logic for version code increasing:
```
autoincrement {
    codeFormatter { int code, ApplicationVariant variant -> code + 1 }
}
```

Define a custom logic for version naming:
```
autoincrement {
    codeFormatter { int code, ApplicationVariant variant -> "${variant.versionName}.$code" }
}
```
