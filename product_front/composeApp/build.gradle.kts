import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.konfig)
}

fun <T: Any> propOfDef(propertyName: String, defaultValue: T): T {
    val props = Properties()
    try {
        FileInputStream("local.properties").use { props.load(it) }
    } catch (e: Exception) {
        println("Error reading local.properties: ${e.message}")
    }

    @Suppress("UNCHECKED_CAST")
    val propertyValue = props[propertyName] as? T
    return propertyValue ?: defaultValue
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.navigator)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.multiplatformSettings)
            implementation(libs.koin.core)
            implementation(libs.date.time)
            implementation(libs.components.resources)
            implementation("io.github.jan-tennert.supabase:postgrest-kt:1.4.7")
            implementation("io.github.jan-tennert.supabase:realtime-kt:1.4.7")
            implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.7")
            implementation("io.github.jan-tennert.supabase:storage-kt:1.4.7")
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            api(libs.androidx.core)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.camera.camera2)
            implementation(libs.camera.lifecycle)
            implementation(libs.camera.view)
            implementation(libs.accompanist.permissions)
            implementation(libs.gms.maps)
            implementation(libs.gms.location)

        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}

buildkonfig {
    packageName = "org.kakazuto.receiper"
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "SUPABASE_URL", propOfDef("SUPABASE_URL", "${System.getenv("SUPABASE_URL")}"))
        buildConfigField(FieldSpec.Type.STRING, "SUPABASE_KEY", propOfDef("SUPABASE_KEY", "${System.getenv("SUPABASE_KEY")}"))
    }


}

android {
    namespace = "org.kakazuto.receiper"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "org.kakazuto.receiper.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}
dependencies {
    implementation(libs.androidx.material3)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.kakazuto.receiper.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
