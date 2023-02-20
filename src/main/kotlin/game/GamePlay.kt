package game

import MyResourceLoader.bubbleImagePath
import MyResourceLoader.inputStreamTitleImage
import MyResourceLoader.pathDownMouth
import MyResourceLoader.pathLeftMouth
import MyResourceLoader.pathRightMouth
import MyResourceLoader.pathSnakeImage
import MyResourceLoader.pathUpMouth
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
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

    private val bubbleXPosArray = Array(34) { i -> 25 * (i + 1) }
    private val bubbleYPosArray = Array(23) { i -> 25 * i + 75 }

    private val random = Random()
    private var bubbleXPos: Int = random.nextInt(34)
    private var bubbleYPos: Int = random.nextInt(23)

    private val delayInit = 150
    private val delayAccumulator = 2
    private val delayLimit = 50
    private var timer: Timer = Timer(delayInit, this)

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

        // INITIAL POSITION
        if (moves == 0) {
            snakeXLength[0] = 100
            snakeYLength[0] = 100
            moves++
        }

        // BORDER TITLE
        val titleImage = ImageIcon(inputStreamTitleImage, "the title image")
        titleImage.paintIcon(this, g, 25, 11)
        g?.color = Color.WHITE
        g?.drawRect(24, 10, 851, 55)

        // BORDER GAME
        g?.color = Color.WHITE
        g?.drawRect(24, 74, 851, 577)
        g?.color = Color.BLACK
        g?.fillRect(25, 75, 850, 575)

        // SCORE
        g?.color = Color.WHITE
        g?.font = Font("arial", Font.PLAIN, 18)
        g?.drawString("score: $lengthOfSnake", 730, 35)
        g?.drawString("speed: ${((25.0 / timer.delay) * 1000).toInt()} px/s", 730, 55)

        // BUBBLES
        val bubbleImage = ImageIcon(bubbleImagePath, "the bubble image")
        bubbleImage.paintIcon(this, g, bubbleXPosArray[bubbleXPos], bubbleYPosArray[bubbleYPos])

        // SNAKE EAT BUBBLE
        if (bubbleXPosArray[bubbleXPos] == snakeXLength[0]
            && bubbleYPosArray[bubbleYPos] == snakeYLength[0]
        ) {
            lengthOfSnake++
            setTimerAfterEatingBubble()
            bubbleXPos = random.nextInt(34)
            bubbleYPos = random.nextInt(23)
        }

        // SNAKE
        val rightMouth = ImageIcon(pathRightMouth, "the right head direction image")
        val leftMouth = ImageIcon(pathLeftMouth, "the left head direction image")
        val upMouth = ImageIcon(pathUpMouth, "the up head direction image")
        val downMouth = ImageIcon(pathDownMouth, "the down head direction image")
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

            // SNAKE EAT HIMSELF
            if (snakeXLength[b] == snakeXLength[0] && snakeYLength[b] == snakeYLength[0]) {
                lengthOfSnake = b
                setTimerAfterEatingHimself()
            }
        }

        g?.dispose()

    }

    private fun setTimerAfterEatingBubble() {
        val newDelay = timer.delay - 2
        timer.delay = if (newDelay >= delayLimit) newDelay else delayLimit
    }

    private fun setTimerAfterEatingHimself() {
        val testDelay = delayInit - delayAccumulator * (lengthOfSnake + 1)
        val newDelay = if (testDelay >= delayLimit) testDelay else delayLimit
        timer.delay = newDelay
    }

    // From KeyListener:
    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_RIGHT -> {
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

            KeyEvent.VK_LEFT -> {
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

            KeyEvent.VK_UP -> {
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

            KeyEvent.VK_DOWN -> {
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