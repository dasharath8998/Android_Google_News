package gurukul.com.googlenews

data class Articles(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)