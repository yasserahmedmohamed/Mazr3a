package com.yasser.mazr3a_task.model

import com.yasser.mazr3a_task.utils.Format
import java.io.Serializable

class Product(
    val description: String?,
    val id: Int,
    val name: String,
    private val thumbnailUrl: String?,
    private val smallThumbnailUrl: String?,
    private val hdThumbnailUrl: String?,
    private val imageUrl: String?,
    private val originalImageUrl: String?,
    val price: String,
    val weight: Double?
) : Serializable {

    val fromatedDescription: String?
        get() {


          val remP =   description?.replace( "<p>", "")?.replace("</p>","")
            val remL = remP?.replace("[lang=","")?.replace("\"", "")?.replace("en]","")?.replace("ar]","")?.replace("[/lang]"," ")
return remL
        }

    val GetCodeID: String
        get() {
            return "  ${id} : الكود"
        }

    val mageUrl: String?
        get() {
            if (!thumbnailUrl.isNullOrEmpty())
                return thumbnailUrl
            else if (!smallThumbnailUrl.isNullOrEmpty())
                return smallThumbnailUrl
            else if (!hdThumbnailUrl.isNullOrEmpty())
                return hdThumbnailUrl
            else if (!imageUrl.isNullOrEmpty())
                return imageUrl
            else
                return originalImageUrl
        }

    fun getWeight(): String {
        if (weight != null) {
            if (weight < 1) {
                return "${(weight * 1000)} g"
            } else if (weight >= 1) {
                return "${weight} KG"
            }
        }
        return ".."
    }

}