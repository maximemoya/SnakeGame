import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.net.URL
import java.util.Random
import javax.swing.ImageIcon
import javax.swing.JPanel
import javax.swing.Timer

class GamePlay : JPanel(), KeyListener, ActionListener {

    private val snakeXLength = Array(750) { _ -> -100 }
    private val snakeYLength = Array(750) { _ -> -100 }

    private var right = true
    private var left = false
    private var up = false
    private var down = false

    private val enemyXPosArray = Array(34) { i -> 25 * (i + 1) }
    private val enemyYPosArray = Array(23) { i -> 25 * i + 75 }

    private val random = Random()
    private var enemyXPos: Int = random.nextInt(34)
    private var enemyYPos: Int = random.nextInt(23)

    private val delay = 150
    private val timer: Timer = Timer(delay, this)

    private var lengthOfSnake = 1
    private var moves = 0

    init {
        println("init gameplay...")
        addKeyListener(this)
        isFocusable = true
        focusTraversalKeysEnabled = true
        timer.start()
    }

    // From JPanel:
    override fun paint(g: Graphics?) {

        if (moves == 0) {
            snakeXLength[0] = 100
            snakeYLength[0] = 100
            moves ++
        }

        // border of title page
        g?.color = Color.WHITE
        g?.drawRect(24, 10, 851, 55)

        val inputStreamTitleImage = this::class.java.getResource("assets/snaketitle.jpg")
        val titleImage = ImageIcon(inputStreamTitleImage, "the title image")
        titleImage.paintIcon(this, g, 25, 11)

        // border of gameplay
        g?.color = Color.WHITE
        g?.drawRect(24, 74, 851, 577)
        g?.color = Color.BLACK
        g?.fillRect(25, 75, 850, 575)

        // score
        g?.color = Color.WHITE
        g?.font = Font("arial", Font.PLAIN, 18)
        g?.drawString("score: $lengthOfSnake", 760, 45)

        // enemy bubbles
        val enemyImagePath: URL = this::class.java.getResource("assets/enemy.png")
            ?: throw Error("URL from enemyImage is NULL")
        val enemyImage = ImageIcon(enemyImagePath, "the enemy image")
        enemyImage.paintIcon(this, g, enemyXPosArray[enemyXPos], enemyYPosArray[enemyYPos])

        if (enemyXPosArray[enemyXPos] == snakeXLength[0]
            && enemyYPosArray[enemyYPos] == snakeYLength[0]
        ) {
            lengthOfSnake++
            enemyXPos = random.nextInt(34)
            enemyYPos = random.nextInt(23)
        }

        // snake
        val pathRightMouth: URL = this::class.java.getResource("assets/rightmouth.png")
            ?: throw Error("URL from pathRightMouth is NULL")
        val rightMouth = ImageIcon(pathRightMouth, "the right head direction image")
        val pathLeftMouth: URL = this::class.java.getResource("assets/leftmouth.png")
            ?: throw Error("URL from pathLeftMouth is NULL")
        val leftMouth = ImageIcon(pathLeftMouth, "the left head direction image")
        val pathUpMouth: URL = this::class.java.getResource("assets/upmouth.png")
            ?: throw Error("URL from pathUpMouth is NULL")
        val upMouth = ImageIcon(pathUpMouth, "the up head direction image")
        val pathDownMouth: URL = this::class.java.getResource("assets/downmouth.png")
            ?: throw Error("URL from pathDownMouth is NULL")
        val downMouth = ImageIcon(pathDownMouth, "the down head direction image")
        val pathSnakeImage: URL = this::class.java.getResource("assets/snakeimage.png")
            ?: throw Error("URL from pathSnakeImage is NULL")
        val snakeImage = ImageIcon(pathSnakeImage, "the snake body image")

        for (a in 0 until lengthOfSnake) {
            if (a == 0 && right) {
                rightMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a])
            } else if (a == 0 && left) {
                leftMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a])
            } else if (a == 0 && up) {
                upMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a])
            } else if (a == 0 && down) {
                downMouth.paintIcon(this, g, snakeXLength[a], snakeYLength[a])
            }

            if (a != 0) {
                snakeImage.paintIcon(this, g, snakeXLength[a], snakeYLength[a])
            }
        }

        for (b in 1 until lengthOfSnake) {
            if (snakeXLength[b] == snakeXLength[0] && snakeYLength[b] == snakeYLength[0]){
                lengthOfSnake = b
            }
        }

        g?.dispose()

    }

    // From KeyListener:
    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_D -> {
                moves++
                if (!left) {
                    right = true
                } else {
                    right = false
                    left = true
                }
                up = false
                down = false
            }
            KeyEvent.VK_A -> {
                moves++
                if (!right) {
                    left = true
                } else {
                    left = false
                    right = true
                }
                up = false
                down = false
            }
            KeyEvent.VK_W -> {
                moves++
                if (!down) {
                    up = true
                } else {
                    up = false
                    down = true
                }
                left = false
                right = false
            }
            KeyEvent.VK_S -> {
                moves++
                if (!up) {
                    down = true
                } else {
                    down = false
                    up = true
                }
                left = false
                right = false
            }
        }
    }

    override fun keyReleased(e: KeyEvent?) {
    }

    // From ActionListener:
    override fun actionPerformed(e: ActionEvent?) {

        if (right) {
            for (i in lengthOfSnake - 1 downTo 0) {
                snakeYLength[i + 1] = snakeYLength[i]
            }
            for (i in lengthOfSnake downTo 0) {
                if (i == 0) {
                    snakeXLength[i] = snakeXLength[i] + 25
                } else {
                    snakeXLength[i] = snakeXLength[i - 1]
                }
                if (snakeXLength[i] > 850) {
                    snakeXLength[i] = 25
                }
            }
            repaint()
        } else if (left) {
            for (i in lengthOfSnake - 1 downTo 0) {
                snakeYLength[i + 1] = snakeYLength[i]
            }
            for (i in lengthOfSnake downTo 0) {
                if (i == 0) {
                    snakeXLength[i] = snakeXLength[i] - 25
                } else {
                    snakeXLength[i] = snakeXLength[i - 1]
                }
                if (snakeXLength[i] < 25) {
                    snakeXLength[i] = 850
                }
            }
            repaint()
        } else if (up) {
            for (i in lengthOfSnake - 1 downTo 0) {
                snakeXLength[i + 1] = snakeXLength[i]
            }
            for (i in lengthOfSnake downTo 0) {
                if (i == 0) {
                    snakeYLength[i] = snakeYLength[i] - 25
                } else {
                    snakeYLength[i] = snakeYLength[i - 1]
                }
                if (snakeYLength[i] < 75) {
                    snakeYLength[i] = 625
                }
            }
            repaint()
        } else if (down) {
            for (i in lengthOfSnake - 1 downTo 0) {
                snakeXLength[i + 1] = snakeXLength[i]
            }
            for (i in lengthOfSnake downTo 0) {
                if (i == 0) {
                    snakeYLength[i] = snakeYLength[i] + 25
                } else {
                    snakeYLength[i] = snakeYLength[i - 1]
                }
                if (snakeYLength[i] > 625) {
                    snakeYLength[i] = 75
                }
            }
            repaint()
        }

    }

}