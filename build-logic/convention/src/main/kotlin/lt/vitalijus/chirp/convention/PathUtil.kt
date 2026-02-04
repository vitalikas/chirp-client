package lt.vitalijus.chirp.convention

import org.gradle.api.Project
import java.util.Locale

fun Project.pathToPackageName(): String {
    val relativePackageName = path
        .replace(':', '.')
        .lowercase()

    return "lt.vitalijus$relativePackageName"
}

fun Project.pathToResourcePrefix(): String {
    return path
        .replace(':', '_')
        .lowercase()
        .drop(1) + "_"
}

// :core:data -> CoreData
fun Project.pathToFrameworkName(): String {
    // :core:data -> ["core", "data"]
    val parts = path.split(":", "-", "_", " ")
    // ["core", "data"] -> "CoreData"
    return parts.joinToString { part ->
        part.replaceFirstChar { char ->
            char.titlecase(Locale.ROOT)
        }
    }
}
