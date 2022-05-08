plugins { pluginsList.forEach { id(it) } }
dependencies { implementations() }

android {
    compileSdk = 31
    defaultConfig {
        applicationId = appId
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = appVersion
        testInstrumentationRunner = testRunner
        vectorDrawables { useSupportLibrary = true }
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile(proGName), proGRules)
            }
        }
        composeOptions { kotlinCompilerExtensionVersion = composeVersion }
        compileOptions { sourceCompatibility = javaVersion; targetCompatibility = javaVersion }
        kotlinOptions {
            jvmTarget = jvm; freeCompilerArgs = argsList
        }
        buildFeatures { compose = true; viewBinding = true }
        packagingOptions { resources { excludes += res } }
    }
}






