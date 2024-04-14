package namada.crab_orange.namada_explorer.apis

import namada.crab_orange.namada_explorer.data.Validators
import retrofit2.http.GET
import retrofit2.http.Path

interface StakePoolApis {
    @GET("api/validators")
    suspend fun getValidator(): Validators
}