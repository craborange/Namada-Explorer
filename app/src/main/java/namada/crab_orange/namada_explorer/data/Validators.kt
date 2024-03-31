package namada.crab_orange.namada_explorer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Validators(
    val currentValidatorsList: List<Validator>
) : Parcelable

@Parcelize
data class Validator(
    val address: String,

    @SerializedName("pub_key")
    val pubKey: PubKey,

    @SerializedName("voting_power")
    val votingPower: Long,

    @SerializedName("proposer_priority")
    val proposerPriority: String,

    @SerializedName("voting_percentage")
    val votingPercentage: Double,

    val moniker: String,

    @SerializedName("operator_address")
    val operatorAddress: String
) : Parcelable {
    @Parcelize
    data class PubKey(
        val type: String,
        val value: String
    ) : Parcelable
}
