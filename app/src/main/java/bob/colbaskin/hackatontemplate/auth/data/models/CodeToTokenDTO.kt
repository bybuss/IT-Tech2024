package bob.colbaskin.hackatontemplate.auth.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeToTokenDTO(
    @SerialName("auth_code") val authCode: String,
    @SerialName("code_challenger") val codeChallenger: String,
    @SerialName("redirect_url") val redirectUrl: String,
    val scopes: List<String>
)