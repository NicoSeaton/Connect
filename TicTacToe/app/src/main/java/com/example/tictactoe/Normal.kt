package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TableLayout
import android.widget.TextView

class Normal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Code that runs on the creation of the game
        turnTextView = findViewById(R.id.turnTextView) as TextView
        TimerText = findViewById(R.id.Timertxt) as TextView
        NaughtWinstxt = findViewById(R.id.Owinstxt) as TextView
        CrossWinstxt = findViewById(R.id.Xwinstxt) as TextView
        tableLayout = findViewById(R.id.table_layout) as TableLayout
        resetButton = findViewById(R.id.resetButton) as android.widget.Button
        settingsButton = findViewById(R.id.settingsButton) as android.widget.Button
        settingsButton!!.setOnClickListener() { openSettings() }
        resetButton!!.setOnClickListener() {startNewGame(false)}
        startNewGame(true)
        TimerFunction()

    }

    // Variables for the game
    var gameBoard: Array<CharArray> = Array(4) { CharArray(4)}
    var turn = 'X'
    var TimeElapsed = 0
    var NaughtWins = 0
    var CrossWins = 0
    var tableLayout: android.widget.TableLayout? = null
    var turnTextView: android.widget.TextView? = null
    var resetButton: android.widget.Button? = null
    var settingsButton: android.widget.Button? = null
    var TimerText: android.widget.TextView? = null
    var NaughtWinstxt: android.widget.TextView? = null
    var CrossWinstxt: android.widget.TextView? = null


    // Start new game function that handles the setting up of the game
    // This also what is called whenever the reset button is clicked
    private fun startNewGame(setClickListener: Boolean) {
        turn = 'X'   //necessary variables for this function
        NaughtWins = 0
        CrossWins = 0
        TimeElapsed = 0

        TimerText?.text = String.format(resources.getString(R.string.timer), TimeElapsed) //Links text to a string in strings.xml
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)    //shows the current players turn
        CrossWinstxt?.text = String.format(resources.getString(R.string.Crosswins), CrossWins) //Links text to a string in strings.xml
        NaughtWinstxt?.text = String.format(resources.getString(R.string.Naughtwins), NaughtWins) //Links text to a string in strings.xml

        // Loop that sets all of the text views to empty so that the game can be played
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[i].size) {
                gameBoard[i][j] = ' '
                val cell = (tableLayout?.getChildAt(i) as
                        android.widget.TableRow).getChildAt(j) as android.widget.TextView
                cell.text = ""
                if (setClickListener) {
                    cell.setOnClickListener { cellClickListener(i, j)}
                }
            }

        }
    }

    private fun startNextRound(setClickListener: Boolean) {
        if (turn == 'O')
        {
            CrossWins +=1;
            CrossWinstxt?.text = String.format(resources.getString(R.string.Crosswins), CrossWins)
        } else
        {
            NaughtWins +=1;
            NaughtWinstxt?.text = String.format(resources.getString(R.string.Naughtwins), NaughtWins)
        }
        turn = 'X'
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)

        // Loop that sets all of the text views to empty so that the game can be played
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[i].size) {
                gameBoard[i][j] = ' '
                val cell = (tableLayout?.getChildAt(i) as
                        android.widget.TableRow).getChildAt(j) as android.widget.TextView
                cell.text = ""
                if (setClickListener) {
                    cell.setOnClickListener { cellClickListener(i, j)}
                }
            }

        }
    }

    // Function that handles the click event for each of the cells
    private fun cellClickListener(row: Int, column: Int) {
        // Places either an O or an X in the cell dependent on whose turn it is
        if (gameBoard[row][column] == ' ') {
            gameBoard[row][column] = turn

            ((tableLayout?.getChildAt(row) as android.widget.TableRow).getChildAt(column) as TextView).text = turn.toString()

            // If a cell is clicked changes the player
            turn = if ('X' == turn) 'O' else 'X'
            turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
            checkGameStatus()
        }
    }

    // Function that handles whether or not the board is full
    private fun isBoardFull(gameBoard:Array<CharArray>): Boolean {
        // loops through the cells to check if they are empty
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[i].size) {
                if (gameBoard[i][j] == ' ') {
                    return false
                }
            }
        }
        return true
    }

    // Function that handles win conditions
    private fun isWinner(gameBoard: Array<CharArray>, w: Char): Boolean {
        // loops through the array to check win conditions
        for (i in 0 until gameBoard.size) {
            if (gameBoard[i][0] == w && gameBoard[i][1] == w && gameBoard[i][2] == w) //Horizontal win conditions
            {
                return true
            }
            if (gameBoard[i][1] == w && gameBoard[i][2] == w && gameBoard[i][3] == w)
            {
                return true
            }
            if (gameBoard[0][i] == w && gameBoard[1][i] == w && gameBoard[2][i] == w) //Vertical win conditions
            {
                return true
            }
            if (gameBoard[1][i] == w && gameBoard[2][i] == w && gameBoard[3][i] == w)
            {
                return true
            }

        }


        if ((gameBoard[0][0] == w && gameBoard[1][1] == w && gameBoard[2][2] == w) || (gameBoard[0][3] == w && gameBoard[1][2] == w && gameBoard[2][1] == w )) {
            return true //Diagonal win conditions
        }
        if ((gameBoard[0][1] == w && gameBoard[1][2] == w && gameBoard[2][3] == w) || (gameBoard[0][2] == w && gameBoard[1][1] == w && gameBoard[2][0] == w )) {
            return true //Diagonal win conditions
        }
        if ((gameBoard[1][0] == w && gameBoard[2][1] == w && gameBoard[3][2] == w) || (gameBoard[1][3] == w && gameBoard[2][2] == w && gameBoard[3][1] == w )) {
            return true //Diagonal win conditions
        }
        if ((gameBoard[1][1] == w && gameBoard[2][2] == w && gameBoard[3][3] == w) || (gameBoard[1][2] == w && gameBoard[2][1] == w && gameBoard[3][0] == w )) {
            return true //Diagonal win conditions
        }
        return false
    }




    // Function that counts up the time
    fun TimerFunction() {
        // Creates a count up timer object
        object : CountDownTimer(1000000, 1000) { //increase by 1 per second
            override fun onTick(millisUntilFinished: Long) {
                if (NaughtWins == 3 || CrossWins == 3) {
                    TimeElapsed = 0
                    TimerText?.text = String.format(resources.getString(R.string.timer), TimeElapsed)
                } else {
                    // Every second add 1 to the gametimer variable and display in the timer textview
                    TimeElapsed++
                    TimerText?.text = String.format(resources.getString(R.string.timer), TimeElapsed)
                }
            }
            override fun onFinish() {

            }
        }.start()
    }

    // Function that handles the status of the game
    private fun checkGameStatus() {
        // Variable to store the state of the game message
        var message: String? = null

        if (CrossWins == 3) { //check if cross won three times.
            message = String.format(resources.getString(R.string.Crosswinner))
        }
        if (NaughtWins == 3) { //check if naughts won three times.
            message = String.format(resources.getString(R.string.Naughtwinner))
        }

        // Depending on who wins or if its a draw, creates a message and stores it in the state variable
        if (isWinner(gameBoard, 'X')) {
            message = String.format(resources.getString(R.string.winner), 'X')
        } else if (isWinner(gameBoard, 'O')) {
            message = String.format(resources.getString(R.string.winner), 'O')
        } else {
            if (isBoardFull(gameBoard)) {
                message = resources.getString(R.string.draw)
            }
        }

        // Creates an on screen dialog message to say who won or if there was a draw
        if (CrossWins == 3 || NaughtWins == 3) {
            if (message != null) {
                turnTextView?.text = message
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setMessage(message)
                builder.setPositiveButton("Restart", { dialog, id -> startNewGame(false) })
                val dialog = builder.create()
                dialog.show()
            }
        } else {
            if (message != null) {
                turnTextView?.text = message
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setMessage(message)
                builder.setPositiveButton("Restart", { dialog, id -> startNewGame(false) })
                builder.setNegativeButton("Next Turn", { dialog, id -> startNextRound(false) })
                val dialog = builder.create()
                dialog.show()
            }
        }
    }

    // Function to handle switching between activities
    fun openSettings() {
        val intent = Intent(this, Hard::class.java)
        startActivity(intent)
    }
}

