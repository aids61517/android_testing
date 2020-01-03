package aids61517.unittest.data

import com.squareup.moshi.Json

data class Product(
    val name: String,
    val price: Int,
    @Json(name = "image_url") val imageUrl: String
)