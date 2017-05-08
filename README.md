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

## Configuration
On your `build.gradle` add:
```
autoincrement {
    jsonFile '<path-to-google-api-json-token>'
}
```

NOTE: this plugin will inherit its configuration from [gradle-play-publisher](https://github.com/Triple-T/gradle-play-publisher) if you are using it
