package lt.vitalijus.chirp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform