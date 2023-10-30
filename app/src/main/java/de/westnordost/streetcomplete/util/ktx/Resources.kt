package de.westnordost.streetcompletegpx.util.ktx

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.decodeFromStream
import de.westnordost.streetcompletegpx.view.DrawableImage
import de.westnordost.streetcompletegpx.view.Image
import de.westnordost.streetcompletegpx.view.ResImage

inline fun <reified T> Resources.getYamlObject(@RawRes id: Int): T =
    Yaml.default.decodeFromStream(openRawResource(id))

/** shortcut for [getYamlObject] with included type information */
fun Resources.getYamlStringMap(@RawRes id: Int): Map<String, String> = this.getYamlObject(id)

fun Resources.getBitmapDrawable(@DrawableRes id: Int): BitmapDrawable =
    getDrawable(id).asBitmapDrawable(this)

fun Resources.getBitmapDrawable(image: Image): BitmapDrawable =
    getDrawable(image).asBitmapDrawable(this)

fun Resources.getDrawable(image: Image): Drawable = when (image) {
    is ResImage -> getDrawable(image.resId)
    is DrawableImage -> image.drawable
}

fun Resources.updateConfiguration(block: Configuration.() -> Unit) =
    updateConfiguration(Configuration(configuration).apply(block), displayMetrics)
