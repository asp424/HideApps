buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
    repositories.repository
}

allprojects { repositories.repository }

clearProject(rootProject.buildDir)


