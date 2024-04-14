package namada.crab_orange.namada_explorer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class Validators(
//    val currentValidatorsList: List<Validator>
//) : Parcelable
//
//@Parcelize
//data class Validator(
//    val address: String,
//
//    @SerializedName("pub_key")
//    val pubKey: PubKey,
//
//    @SerializedName("voting_power")
//    val votingPower: Long,
//
//    @SerializedName("proposer_priority")
//    val proposerPriority: String,
//
//    @SerializedName("voting_percentage")
//    val votingPercentage: Double,
//
//    val moniker: String,
//
//    @SerializedName("operator_address")
//    val operatorAddress: String
//) : Parcelable {
//    @Parcelize
//    data class PubKey(
//        val type: String,
//        val value: String
//    ) : Parcelable
//}


@Parcelize
data class Validators (
    val validators: List<Validator>
) : Parcelable

@Parcelize
data class Validator (
    @SerializedName("operator_address")
    val operatorAddress: String? = null,

    @SerializedName("hex_address")
    val hexAddress: String,

    val moniker: String? = null,
    val tokens: Long,

    @SerializedName("cumulative_share")
    val cumulativeShare: Double,

    @SerializedName("voting_power_percent")
    val votingPowerPercent: Double,

    val rank: Long
) : Parcelable