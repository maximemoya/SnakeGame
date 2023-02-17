package game

import java.awt.Color
import java.awt.Rectangle
import javax.swing.JFrame

class Game : JFrame() {
    init {
        this.title = "SnakeGame"
        this.bounds = Rectangle(10, 10, 905, 700)
        this.setLocationRelativeTo(null)
        this.isResizable = false
        this.isVisible = true
        this.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.background = Color.BLUE

        val gamePlay = GamePlay()
        this.add(gamePlay)
        this.validate()
    }
}