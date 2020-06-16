package difficulties;

import sudokulogic.SudokuBoard;

import java.util.Random;

// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------
// EXCERCISE 8 PARTS ----------------

public abstract class Difficulty {
    private static Random generator = new Random();
    private static final int max = 8;
    private static final int min = 0;
    int deleteCounter;

    public void prepareBoard(SudokuBoard board) {
        for (int i = 0; i < deleteCounter; i++) {
            int rand1 = generator.nextInt(max - min + 1) + min;
            int rand2 = generator.nextInt(max - min + 1) + min;

            board.getField(rand1, rand2).setValue(0);
        }
    }
}
