plugins {
  id("skydoves.pokedex.android.library")
  id("skydoves.pokedex.android.hilt")
  id("skydoves.pokedex.spotless")
  alias(libs.plugins.protobuf.plugin)
}

android {
    namespace = "com.skydoves.pokedex.compose.core.datastore"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
  implementation(projects.core.common)
  implementation(projects.core.model)

  api(libs.androidx.dataStore)
  implementation(libs.protobuf.kotlin.lite)

  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
}

protobuf {

  protoc {
    artifact = libs.protobuf.protoc.get().toString()
  }

  generateProtoTasks {
    all().forEach { task ->
      task.builtins {
        register("java") {
          option("lite")
        }
        register("kotlin") {
          option("lite")
        }
      }
    }
  }
}