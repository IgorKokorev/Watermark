fun printColor(myImage: BufferedImage) {
    // Write your code here
    val inp = readln().split(" ")
    val x = inp[0].toInt()
    val y = inp[1].toInt()
    val c = Color(myImage.getRGB(x, y),true)
    println("${c.red} ${c.green} ${c.blue} ${c.alpha}")
}