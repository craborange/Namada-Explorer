package namada.crab_orange.namada_explorer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Signature(
    @SerializedName("block_number")
    val blockNumber: Long,

    @SerializedName("sign_status")
    val signStatus: Boolean
) : Parcelable