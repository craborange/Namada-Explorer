package namada.crab_orange.namada_explorer.apis

import namada.crab_orange.namada_explorer.data.Signature
import namada.crab_orange.namada_explorer.data.Validators
import retrofit2.http.GET
import retrofit2.http.Path

interface StakePoolApis {
    @GET("node/validators/list")
    suspend fun getValidator(): Validators

    @GET("node/validators/validator/{address}/latestSignatures")
    suspend fun getSignature(@Path("address") address: String): List<Signature>
}