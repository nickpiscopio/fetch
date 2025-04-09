import com.nickpiscopio.fetch.domain.model.ListItem

class ListItemRepository {
    suspend fun getListItems(): Result<List<ListItem>> {
        return try {
            val todo = RetrofitClient.listItemService.getListItems()
            Result.success(todo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}