package watermark

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.lang.NumberFormatException

class ImgFileException( s: String ): Exception(s)

fun main() {

    val im: BufferedImage // Image
    val wm: BufferedImage // Watermark
    try {
        println("Input the image filename:")
        im = readFile("image")
        println("Input the watermark image filename:")
        wm = readFile("watermark")
    } catch (e: ImgFileException) { return }

    if (im.width != wm.width || im.height != wm.height) {
        println("The image and watermark dimensions are different.")
        return
    }

    val weight: Int
    println("Input the watermark transparency percentage (Integer 0-100):")
    try {
        weight = readln().toInt()
    } catch (e: NumberFormatException) {
        println("The transparency percentage isn't an integer number.")
        return
    }

    if (!(weight in 0..100)) {
        println("The transparency percentage is out of range.")
        return
    }

    println("Input the output image filename (jpg or png extension):")
    val outFileName = readln()
    if ( !( outFileName.endsWith(".jpg", true) || outFileName.endsWith(".png", true ) ) ) {
        println("The output file extension isn't \"jpg\" or \"png\".")
        return
    }

    val resImg = BufferedImage(im.width, im.height, BufferedImage.TYPE_INT_RGB)

    for (x in 0 until im.width) {
        for (y in 0 until im.height) {
            val i = Color(im.getRGB(x, y))
            val w = Color(wm.getRGB(x, y))
            val color = Color(
                (weight * w.red + (100 - weight) * i.red) / 100,
                (weight * w.green + (100 - weight) * i.green) / 100,
                (weight * w.blue + (100 - weight) * i.blue) / 100
            )
            resImg.setRGB(x, y, color.rgb)
        }
    }
    val outFile = File(outFileName)
    ImageIO.write(resImg, outFileName.substring(outFileName.length - 3), outFile)
    println("The watermarked image $outFileName has been created.")
}

fun readFile(str: String): BufferedImage {

    val fileName = readln()
    val file = File(fileName)
    if (!file.exists()) {
        println("The file $fileName doesn't exist.")
        throw ImgFileException("")
    }

    val im: BufferedImage = ImageIO.read(file)
    if (im.colorModel.numComponents != 3) {
        println("The number of $str color components isn't 3.")
        throw ImgFileException("")
    }
    if (im.colorModel.pixelSize != 24 && im.colorModel.pixelSize != 32) {
        println("The $str isn't 24 or 32-bit.")
        throw ImgFileException("")
    }
    return im
}