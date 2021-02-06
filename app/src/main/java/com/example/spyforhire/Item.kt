package layout

import android.media.Image
import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class Item(@SerializedName("weapon_id") val idItem:Int , @SerializedName("player_id") val idPlayer:Int, @SerializedName("image")var image: Int, @SerializedName("name")var name: String, @SerializedName("level")var level: Int, @SerializedName("price")var coin: Int, @SerializedName("complete")var complete: Boolean ) {

}
