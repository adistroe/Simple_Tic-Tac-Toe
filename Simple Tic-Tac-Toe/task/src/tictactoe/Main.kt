package tictactoe

const val X_SYMBOL = 'X'
const val O_SYMBOL = 'O'
const val FREE_CELL_SYMBOL = ' '
//const val FREE_CELL_SYMBOL = '_'

const val EMPTY_TABLE =
    "$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL" +
            "$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL" +
            "$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL$FREE_CELL_SYMBOL"  // 9 characters total

const val THREE_X_IN_A_ROW = "$X_SYMBOL$X_SYMBOL$X_SYMBOL"
const val THREE_O_IN_A_ROW = "$O_SYMBOL$O_SYMBOL$O_SYMBOL"

const val PLAYER_X = X_SYMBOL
const val PLAYER_O = O_SYMBOL

const val TOP_GRID = "---------"
const val LEFT_GRID = "| "
const val RIGHT_GRID = " |"
const val BOTTOM_GRID = "---------"
const val STRING_DELIMITER = " "

const val MSG_NOT_FINISHED = "Game not finished"
const val MSG_DRAW = "Draw"
const val MSG_X_WINS = "X wins"
const val MSG_O_WINS = "O wins"
//const val MSG_IMPOSSIBLE = "Impossible"

const val ERR_ENTER_NUMBERS = "You should enter numbers!"
const val ERR_CELL_OCCUPIED = "This cell is occupied! Choose another one!"
const val ERR_COORDINATES_OUT_OF_RANGE = "Coordinates should be from 1 to 3!"

fun main() {
    var gameTable = EMPTY_TABLE
    println(getTicTacToeTable(gameTable))

    var whichPlayer = PLAYER_O
    var gameState: String

    do {
        // players take turns
        whichPlayer = if (whichPlayer == PLAYER_O) PLAYER_X else PLAYER_O

        // get player input, check for validity and update game table
        gameTable = getUserInput(gameTable, whichPlayer)

        // print updated game table
        println(getTicTacToeTable(gameTable))

        // check game state and exit if either player wins, or there is a draw
        gameState = getGameState(gameTable)
    } while (gameState == MSG_NOT_FINISHED)

    // print game state message: Win or Draw
    println(gameState)
}

/*
    ---------
    | 0 1 2 |
    | 3 4 5 |
    | 6 7 8 |
    ---------
    Creates 3x3 game table from a 9-character "_XXOO_OX_" type String
*/
fun getTicTacToeTable(ticTacToeTable: String): String {
    val rowOne = ticTacToeTable.toCharArray(0, 3).joinToString(STRING_DELIMITER, LEFT_GRID, RIGHT_GRID)
    val rowTwo = ticTacToeTable.toCharArray(3, 6).joinToString(STRING_DELIMITER, LEFT_GRID, RIGHT_GRID)
    val rowThree = ticTacToeTable.toCharArray(6, 9).joinToString(STRING_DELIMITER, LEFT_GRID, RIGHT_GRID)
    return "$TOP_GRID\n" +
            "$rowOne\n" +
            "$rowTwo\n" +
            "$rowThree\n" +
            BOTTOM_GRID
}

/*
    Gets player input coordinates, checks for validity and returns the updated game table
*/
fun getUserInput(ticTacToeTable: String, whichPlayer: Char): String {
    var isValidInput = false
    var tableWithUserInput = ""

    do {
        // get user input
        val (r1, c1) = readln().split(STRING_DELIMITER)
        val r2 = r1[0]
        val c2 = c1[0]

        // check if coordinates are numbers (digits)
        if (!r2.isDigit() || !c2.isDigit()) {
            println(ERR_ENTER_NUMBERS)
        } else {
            val row = r2.digitToInt()
            val column = c2.digitToInt()

            // check if coordinates are outside game grid
            if (row !in 1..3 || column !in 1..3) {
                println(ERR_COORDINATES_OUT_OF_RANGE)
            } else {
                /* We translate the coordinates into an index position,
                   so we can place the "X" or "O" in the "_XXOO_OX_" string input
                   i.e.: coordinates (2,2) translate into index position 4
                    ---------
                    | 0 1 2 |
                    | 3 4 5 |
                    | 6 7 8 |
                    ---------
                */
                // Depending on what row the coordinates are, we use different formulas
                // to translate the coordinates into an index
                var index: Int = -1
                when (row) {
                    1 -> index = column - row
                    2 -> index = row + column
                    3 -> index = 2 * row + column - 1
                }

                // check if cell is empty
                if (ticTacToeTable[index] != FREE_CELL_SYMBOL) {
                    println(ERR_CELL_OCCUPIED)
                } else {
                    // insert player's "X" or "O" into the "_XXOO_OX_" string
                    tableWithUserInput =
                        ticTacToeTable.substring(0, index) + whichPlayer + ticTacToeTable.substring(index + 1)
                    isValidInput = true
                }
            }
        }
    } while (!isValidInput)
    return tableWithUserInput
}

/*
    ---------
    | 0 1 2 |
    | 3 4 5 |
    | 6 7 8 |
    ---------
    Checks game state for Win or Draw conditions.
    Lines that are commented-out were used to check for other conditions,
    that are not part of the final project anymore.
*/
fun getGameState(ticTacToeTable: String): String {
//    val numberOfSymbolsX = ticTacToeTable.count { it == X_SYMBOL }
//    val numberOfSymbolsO = ticTacToeTable.count { it == O_SYMBOL }
    val freeCells = ticTacToeTable.count { it == FREE_CELL_SYMBOL }

    // Win conditions
    val topRow = ticTacToeTable.substring(0..2)
    val midRow = ticTacToeTable.substring(3..5)
    val bottomRow = ticTacToeTable.substring(6..8)
    val leftColumn = "${ticTacToeTable[0]}${ticTacToeTable[3]}${ticTacToeTable[6]}"
    val midColumn = "${ticTacToeTable[1]}${ticTacToeTable[4]}${ticTacToeTable[7]}"
    val rightColumn = "${ticTacToeTable[2]}${ticTacToeTable[5]}${ticTacToeTable[8]}"
    val leftDiagonal = "${ticTacToeTable[0]}${ticTacToeTable[4]}${ticTacToeTable[8]}"
    val rightDiagonal = "${ticTacToeTable[2]}${ticTacToeTable[4]}${ticTacToeTable[6]}"

    val winConditions =
        listOf(
            topRow, midRow, bottomRow,
            leftColumn, midColumn, rightColumn,
            leftDiagonal, rightDiagonal
        )

    val isFullTable = freeCells == 0
    val isWinnerX = winConditions.contains(THREE_X_IN_A_ROW)
    val isWinnerO = winConditions.contains(THREE_O_IN_A_ROW)
//    val isTooManyX = numberOfSymbolsX > numberOfSymbolsO + 1
//    val isTooManyO = numberOfSymbolsO > numberOfSymbolsX + 1

    return when {
//        (isWinnerX && isWinnerO) || (isTooManyX || isTooManyO) -> MSG_IMPOSSIBLE
//        !isWinnerX && !isWinnerO && !isFullTable -> MSG_NOT_FINISHED
//        !isWinnerX && !isWinnerO && isFullTable -> MSG_DRAW
        isWinnerX -> MSG_X_WINS
        isWinnerO -> MSG_O_WINS
        isFullTable -> MSG_DRAW
        else -> MSG_NOT_FINISHED
    }
}