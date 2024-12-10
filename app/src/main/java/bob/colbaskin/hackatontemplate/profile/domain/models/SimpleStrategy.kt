package bob.colbaskin.hackatontemplate.profile.domain.models

import java.util.UUID

data class SimpleStrategy(
    val id: UUID,
    val startDate: String,
    val endDate: String,
    val sum: Int
)
