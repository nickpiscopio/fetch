import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val listItemService: ListItemService by lazy {
        Retrofit.Builder()
            .baseUrl(ServerEnvironment.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ListItemService::class.java)
    }
}