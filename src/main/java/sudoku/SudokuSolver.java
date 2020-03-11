package sudoku;

public class SudokuSolver {
    public static SudokuPuzzle solve(SudokuPuzzle sudokuPuzzle){
        SudokuPuzzle sp = new SudokuPuzzle(sudokuPuzzle);

        int rows = sp.getNumRows();
        int cols = sp.getNumColumns();
        int [][] bo = new int [rows][cols];
        String s = null;
        for(int i=0;i<rows;++i){
            for(int j=0;j<cols;++j){
                s= sp.getValue(i,j);
                if(s.isEmpty()){
                    bo[i][j]=0;
                }
                else
                    bo[i][j]=Integer.valueOf(s);
            }
        }

        if(solve(bo)) {
            for(int i=0;i<rows;++i){
                for(int j=0;j<cols;++j){
                    if(bo[i][j]==0)
                        s= "";
                    else
                        s= String.valueOf(bo[i][j]);
                    sp.setValue(i,j,s);
                }
            }
        }

        return sp;
    }

    /**
     * Solves a sudoku board using backtracking
     * @param bo 2d list of ints
     * @return solution
     */
    public static boolean solve(int[][] bo){
        int [] find = findEmpty(bo);
        int row, col;
        if(find!=null){
            row = find[0];
            col = find[1];
        }
        else
            return true;

        int [] pos=new int[2];
        for (int i=1;i<10;++i){
            pos[0]=row;
            pos[1]=col;
            if(valid(bo,pos,i)){
                bo[row][col]=i;
                if(solve(bo))
                    return true;
                bo[row][col]=0;
            }
        }

        return false;
    }

    /**
     * Returns if the attempted move is valid
     * @param bo 2d list of ints
     * @param pos (row, col)
     * @param num int
     * @return
     */
    private static boolean valid(int[][] bo, int [] pos, int num){
        int i=0;
        int j=0;
        //check row
        for(i=0;i<bo.length;++i){
            if(bo[pos[0]][i]==num && pos[1]!=i){
                return false;
            }
        }
        //check col
        for(i=0;i<bo.length;++i){
            if(bo[i][pos[1]]==num && pos[0]!=i){
                return false;
            }
        }
        //check box
        int box_x=pos[1]/3;
        int box_y=pos[0]/3;

        for (i=box_y*3;i<box_y*3 + 3;++i){
            for (j=box_x*3;j<box_x*3 + 3;++j){
                if (bo[i][j] == num && (pos[0]!=i && pos[1]!=j))
                    return false;
            }
        }

        return true;
    }

    /**
     * finds an empty space in the board
     * @param bo partially complete board
     * @return (int, int) row col
     */
    private static int[] findEmpty(int[][] bo){
        for(int i=0;i<bo.length;++i){
            for(int j=0;j<bo[i].length;++j){
                if(bo[i][j]==0) {
                    int [] pos = new int[2];
                    pos[0]=i;
                    pos[1]=j;
                    return pos;
                }
            }
        }
        return null;
    }

    /**
     *
     * @param bo 2d List of ints
     */
    public static void printBoard(int [][] bo){
        int i=0;
        int j=0;
        for(i=0;i<bo.length;++i){
            if (i % 3 == 0 && i != 0)
                System.out.print("- - - - - - - - - - - - - -");
            for(j=0;j<bo[i].length;++j){
                if(j%3==0){
                    System.out.print(" | ");
                }
                if (j == 8){
                    System.out.println(bo[i][j]);
                }
                else{
                    System.out.print(String.valueOf(bo[i][j]) + " ");
                }
            }
        }
    }
}
