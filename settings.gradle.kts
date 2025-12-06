@file:Suppress("UnstableApiUsage")

include(":feature:settings")


include(":core:common")


include(":baselineprofile")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
  includeBuild("build-logic")
  repositories {


    // fetch plugins from google maven (https://maven.google.com)
    google() {
      content {
        includeGroupByRegex("androidx\\..*")
        includeGroupByRegex("com\\.android(\\..*|)")
        includeGroupByRegex("com\\.google\\.android\\..*")
        includeGroupByRegex("com\\.google\\.firebase(\\..*|)")
        includeGroupByRegex("com\\.google\\.gms(\\..*|)")
        includeGroupByRegex("com\\.google\\.mlkit")
        includeGroupByRegex("com\\.google\\.oboe")
        includeGroupByRegex("com\\.google\\.prefab")
        includeGroupByRegex("com\\.google\\.testing\\.platform")
      }
      mavenContent {
        releasesOnly()
      }
    }

    // fetch plugins
    mavenCentral() {
      content {
        includeGroup("com.google.dagger")
        includeGroup("com.google.dagger.hilt.android")
        includeGroup("com.github.skydoves.compose.stability.analyzer")
      }
      mavenContent {
        releasesOnly()
      }
    }

    // fetch plugins from gradle plugin portal (https://plugins.gradle.org)
    gradlePluginPortal()

    // fetch snapshot plugins from sonatype
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
      mavenContent {
        snapshotsOnly()
      }
    }
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    // fetch libraries from google maven (https://maven.google.com)
    google() {
      content {
        includeGroupByRegex("androidx\\..*")
        includeGroupByRegex("com\\.android(\\..*|)")
        includeGroupByRegex("com\\.google\\.android\\..*")
        includeGroupByRegex("com\\.google\\.firebase(\\..*|)")
        includeGroupByRegex("com\\.google\\.gms(\\..*|)")
        includeGroupByRegex("com\\.google\\.mlkit")
        includeGroupByRegex("com\\.google\\.oboe")
        includeGroupByRegex("com\\.google\\.prefab")
        includeGroupByRegex("com\\.google\\.testing\\.platform")
      }
      mavenContent {
        releasesOnly()
      }
    }

    // fetch libraries from maven central
    mavenCentral() {
      mavenContent {
        releasesOnly()
      }
    }

    // fetch snapshot libraries from sonatype
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
      mavenContent {
        snapshotsOnly()
      }
    }
  }
}
rootProject.name = "pokedex-compose"
include(":app")
include(":core:model")
include(":core:network")
include(":core:viewmodel")
include(":core:database")
include(":core:datastore")
include(":core:data")
include(":core:test")
include(":core:navigation")
include(":core:designsystem")
include(":core:preview")

include(":feature:home")
include(":feature:details")
