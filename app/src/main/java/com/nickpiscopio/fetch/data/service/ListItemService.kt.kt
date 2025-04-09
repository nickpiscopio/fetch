import com.nickpiscopio.fetch.domain.model.ListItem
import retrofit2.http.GET

interface ListItemService {
    @GET("${ServerEnvironment.BASE_URL}hiring.json")
    suspend fun getListItems(): List<ListItem>
}