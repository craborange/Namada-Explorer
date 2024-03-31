package namada.crab_orange.namada_explorer.apis

import namada.crab_orange.namada_explorer.data.Block
import namada.crab_orange.namada_explorer.data.Data
import namada.crab_orange.namada_explorer.data.Transaction
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IndexerApis {
    @GET("block")
    suspend fun getBlock(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Data<Block>

    @GET("block/hash/{id}")
    suspend fun getBlock(
        @Path("id") blockID: String
    ): Block

    @GET("tx")
    suspend fun getTransaction(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): Data<Transaction>

    @GET("tx/{hash}")
    suspend fun getTransaction(
        @Path("hash") txHash: String
    ): Transaction
}