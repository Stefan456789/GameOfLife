import java.util.Arrays;

public class Game {


    boolean[][] field;
    public Game(int width, int height) {
        field = new boolean[width][];
        for (int i = 0; i < width; i++) {
            field[i] = new boolean[height];
            for (int j = 0; j < height; j++) {
                field[i][j] = (int) (Math.random() * 4) == 0;
            }
        }
    }


    public int getWidth() {
        return field.length;
    }

    public int getHeight() {
        return field[0].length;
    }

    public boolean[][] frame() {
        final boolean[][] result = new boolean[field.length][];
        for (int i = 0; i < field.length; i++) {
            result[i] = new boolean[field[i].length];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = isAlive(field, i, j);
            }
        }

        var old = field;
        field = result;
        return old;
    }

    private boolean isAlive(boolean[][] field, int x, int y) {
        int neighbors = 0;

            for (int i = -1; i < 2; i++) {
                int xCheck = (x+i)%field.length;
                if (xCheck < 0)
                    xCheck = field.length+xCheck;
                for (int j = -1; j < 2; j++) {
                    int yCheck = (y+j)%field[1].length;
                    if (yCheck < 0)
                        yCheck = field[1].length+yCheck;

                    if (field[xCheck][yCheck] && !(i == 0 && j == 0)){
                        neighbors++;
                    }
                }
            }

        if (neighbors < 2){
            return false;
        } else if (neighbors > 3){
            return false;
        } else if (neighbors == 2 && !field[x][y]){
            return false;
        } else return true;
    }

    public void setPixel(int x, int y){
        field[x][y] = true;
    }
}
