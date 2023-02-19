import java.net.URL

object MyResourceLoader {

    val inputStreamTitleImage = this::class.java.getResource("assets/snaketitle.jpg")
        ?: throw Error("URL from titleImage is NULL")
    val bubbleImagePath: URL = this::class.java.getResource("assets/enemy.png")
        ?: throw Error("URL from enemyImage is NULL")
    val pathRightMouth: URL = this::class.java.getResource("assets/rightmouth.png")
        ?: throw Error("URL from pathRightMouth is NULL")
    val pathLeftMouth: URL = this::class.java.getResource("assets/leftmouth.png")
        ?: throw Error("URL from pathLeftMouth is NULL")
    val pathUpMouth: URL = this::class.java.getResource("assets/upmouth.png")
        ?: throw Error("URL from pathUpMouth is NULL")
    val pathDownMouth: URL = this::class.java.getResource("assets/downmouth.png")
        ?: throw Error("URL from pathDownMouth is NULL")
    val pathSnakeImage: URL = this::class.java.getResource("assets/snakeimage.png")
        ?: throw Error("URL from pathSnakeImage is NULL")

}