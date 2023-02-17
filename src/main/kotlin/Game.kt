import java.awt.Color
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

class Game : JFrame(), KeyListener {

    private val gamePlay = GamePlay()

    init {
        this.title = "SnakeGame"
        this.bounds = Rectangle(10, 10, 905, 700)
        this.setLocationRelativeTo(null)
        this.isResizable = false
        this.isVisible = true
        this.defaultCloseOperation = EXIT_ON_CLOSE
        this.background = Color.BLUE

        this.add(gamePlay)
        this.addKeyListener(this)
        this.validate()
    }

    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        gamePlay.keyPressed(e)
    }

    override fun keyReleased(e: KeyEvent?) {
    }
}