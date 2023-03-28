package watermark

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.Transparency

fun main() {
    println("Input the image filename:")
    val fileName = readln()
    val imageFile = File(fileName)

    if (imageFile.exists()) {
        val im: BufferedImage = ImageIO.read(imageFile)
        println("Image file: $fileName")
        println("Width: ${im.width}")
        println("Height: ${im.height}")
        println("Number of components: ${im.colorModel.numComponents}")
        println("Number of color components: ${im.colorModel.numColorComponents}")
        println("Bits per pixel: ${im.colorModel.pixelSize}")
        val transp = if ( im.transparency == Transparency.OPAQUE) "OPAQUE"
                else if (im.transparency == Transparency.TRANSLUCENT) "TRANSLUCENT"
                else "BITMASK"
        println("Transparency: $transp")
    } else println("The file $fileName doesn't exist.")

}