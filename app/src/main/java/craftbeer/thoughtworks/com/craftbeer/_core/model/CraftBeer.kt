package craftbeer.thoughtworks.com.craftbeer._core.model

import com.google.gson.annotations.SerializedName
import java.time.format.DecimalStyle

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
data class CraftBeer(@SerializedName("abv") val abv: String, @SerializedName("ibu") val ibu: String, @SerializedName("id") val id: Long
                     , @SerializedName("name") val name: String, @SerializedName("style") val style: String, @SerializedName("ounces") val ounces: Double
) {
}