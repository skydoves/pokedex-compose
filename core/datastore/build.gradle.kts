plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
}

android {
    namespace = "com.skydoves.pokedex.compose.core.datastore"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {

}