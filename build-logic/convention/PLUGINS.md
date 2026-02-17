# Convention Plugins

This module contains all the Gradle convention plugins used across the Chirp project to standardize build configuration.

## Plugin Hierarchy

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     CONVENTION PLUGIN HIERARCHY                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────┐              ┌──────────────────────────────┐
│      ANDROID LAYER           │              │   KOTLIN MULTIPLATFORM       │
│  (Android-specific setup)    │              │        (KMP Base)            │
└──────────────┬───────────────┘              └───────────────┬──────────────┘
               │                                              │
               ▼                                              ▼
┌──────────────────────────────┐              ┌──────────────────────────────┐
│  AndroidApplicationConvention│              │   KmpLibraryConventionPlugin │
│           Plugin             │              │  ──────────────────────────  │
│  ──────────────────────────  │              │  • com.android.library       │
│  • com.android.application   │              │  • org.jetbrains.kotlin.mpp  │
│  • applicationId, versionCode│              │  • kotlinx-serialization     │
│  • targetSdk, minifyEnabled  │              │  • kotlin-test (commonTest)  │
│  • packaging resources       │              │  • enableAndroidResources    │
│  • namespace setup           │              │                              │
└──────────────┬───────────────┘              └───────────────┬──────────────┘
               │                                              │
               ▼                                              ▼
┌──────────────────────────────┐              ┌────────────────────────────────┐
│ AndroidApplicationCompose    │              │   CmpLibraryConventionPlugin   │
│      ConventionPlugin        │              │  ──────────────────────────    │
│  ──────────────────────────  │              │  ↳ extends KmpLibrary...       │
│  ↳ extends AndroidApplication│              │  • org.jetbrains.compose       │
│  • kotlin.plugin.compose     │              │  • jetbrains-compose-foundation│
│  • configureAndroidCompose() │              │  • jetbrains-compose-material3 │
│  • androidx-compose-bom      │              │  • jetbrains-compose-ui        │
│  • ui-tooling (debug)        │              │  • compose-material-icons      │
│  • ui-tooling-preview (debug)│              │  • ui-tooling (debug)          │
└──────────────┬───────────────┘              └───────────────┬────────────────┘
               │                                              │
               │                                              ▼
               │                              ┌─────────────────────────────────┐
               │                              │   CmpFeatureConventionPlugin    │
               │                              │  ──────────────────────────     │
               │                              │  ↳ extends CmpLibrary...        │
               │                              │  ↳ project(":core:presentation")│
               │                              │  ↳ project(":core:designsystem")│
               │                              │  • Koin: BOM + compose + vm     │
               │                              │  • Koin Android: android + nav  │
               │                              │  • JetBrains: navigation + vm   │
               │                              │  • Lifecycle: viewmodel + state │
               │                              └─────────────────────────────────┘
               ▼
┌──────────────────────────────────────┐
│  CmpApplicationConventionPlugin      │                                     
│  ─────────────────────────────────── │
│  ↳ extends AndroidApplicationCompose │                                         
│  • org.jetbrains.kotlin.mpp          │
│  • org.jetbrains.compose             │
│  • kotlin.plugin.compose             │
│  • configureAndroidTarget()          │
│  • configureIosTargets()             │
│  • ui-tooling (debug)                │
└──────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                    SPECIALIZED PLUGINS (Standalone)                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌───────────────────────────┐    ┌─────────────────────────────────────────┐
│  │    RoomConventionPlugin   │    │      BuildKonfigConventionPlugin        │
│  │  ───────────────────────  │    │     ─────────────────────────────       │
│  │  • com.google.devtools.ksp│    │     • com.codingfeline.buildkonfig      │
│  │  • androidx.room          │    │     • packageName from project path     │
│  │  • schemaDirectory        │    │     • API_KEY from local.properties     │ 
│  │  • sqlite-bundled         │    │                                         │
│  │  • kspAndroid, kspIos*    │    │     Used by: core:data                  │
│  │                           │    │     (for API secrets)                   │
│  │  Used by: core:data       │    │                                         │
│  └───────────────────────────┘    └─────────────────────────────────────────┘
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

═══════════════════════════════════════════════════════════════════════════════
                           USAGE IN YOUR PROJECT
═══════════════════════════════════════════════════════════════════════════════

┌─────────────────────┐         ┌─────────────────────┐         ┌─────────────────────┐
│    composeApp/      │         │  core:presentation/ │         │   core:data/        │
│  build.gradle.kts   │         │   build.gradle.kts  │         │  build.gradle.kts   │
├─────────────────────┤         ├─────────────────────┤         ├─────────────────────┤
│ plugins {           │         │ plugins {           │         │ plugins {           │
│   convention.       │◄────────│   convention.       │         │   convention.       │
│   cmp.application   │dependsOn│   cmp.library       │◄────────│   kmp.library       │
│ }                   │         │ }                   │dependsOn│   convention.room   │
│                     │         │                     │         │   convention.       │
│ • Android target    │         │ • Common UI         │         │   buildkonfig       │
│ • iOS target        │         │ • No target config  │         │ }                   │
│ • Runs on device    │         │ • Used by features  │         │                     │
└─────────────────────┘         └─────────────────────┘         │ • Ktor networking   │
         │                                                      │ • Room database     │
         │ dependsOn                                            │ • Secrets config    │
         ▼                                                      └─────────────────────┘
┌─────────────────────────────────────────────────────────────────────────────┐
│                           FEATURE MODULES                                   │
│                    (feature:auth, feature:feed, etc.)                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   plugins {                                                                 │
│     alias(libs.plugins.convention.cmp.feature)                              │
│   }                                                                         │
│                                                                             │
│   • Extends CmpLibrary (UI + Compose)                                       │
│   • + Koin Dependency Injection                                             │
│   • + Navigation (JetBrains Compose Navigation)                             │
│   • + ViewModel (JetBrains ViewModel)                                       │
│   • + Core modules (presentation, designsystem)                             │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Available Plugins

| Plugin ID | Implementation Class | Purpose |
|-----------|---------------------|---------|
| `lt.vitalijus.convention.android.application` | `AndroidApplicationConventionPlugin` | Base Android app configuration |
| `lt.vitalijus.convention.android.application.compose` | `AndroidApplicationComposeConventionPlugin` | Android app with Compose support |
| `lt.vitalijus.convention.cmp.application` | `CmpApplicationConventionPlugin` | Compose Multiplatform app (Android + iOS) |
| `lt.vitalijus.convention.kmp.library` | `KmpLibraryConventionPlugin` | Kotlin Multiplatform library base |
| `lt.vitalijus.convention.cmp.library` | `CmpLibraryConventionPlugin` | CMP library with Compose UI components |
| `lt.vitalijus.convention.cmp.feature` | `CmpFeatureConventionPlugin` | CMP feature module with Koin, Navigation, ViewModel |
| `lt.vitalijus.convention.room` | `RoomConventionPlugin` | Room database with KSP for all targets |
| `lt.vitalijus.convention.buildkonfig` | `BuildKonfigConventionPlugin` | Build-time configuration (API keys, etc.) |

## Usage

Apply a plugin in your module's `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.convention.cmp.application)  // For the main app
    // OR
    alias(libs.plugins.convention.cmp.feature)      // For feature modules
    // OR
    alias(libs.plugins.convention.cmp.library)      // For UI libraries
}
```

## Legend

| Symbol | Meaning |
|--------|---------|
| `↓ extends` | Inherits from / builds on top of |
| `← dependsOn` | Module dependency |
| `•` | Plugin or configuration item |
