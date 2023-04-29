package getphotos

import java.time.LocalDateTime

class Photo(
    val extention: String,
    val size: Size,
    val fileId: String,
    val time: LocalDateTime,
)

class Size(
    val width: Long,
    val height: Long,
)
