import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static final int SIZE = 10;
    private static final boolean USEPIXELS = true;

    public static void main(String[] args) {
        Game game = new Game(WIDTH, HEIGHT);
        SimpleGraphicsLibrary graphics = new SimpleGraphicsLibrary(game.getWidth() * SIZE, game.getHeight() * SIZE, Color.LIGHT_GRAY, game::setPixel);

        boolean[][] field = game.frame();
        boolean[][] overLastFrame;
        int cycle = 0;
        do {
            cycle++;

            if (USEPIXELS)
                graphics.clear();

            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    if (field[i][j]) {
                        if (USEPIXELS){
                            for (int k = 0; k < SIZE; k++) {
                                for (int l = 0; l < SIZE; l++) {
                                    graphics.setPixel(i * SIZE + k, j * SIZE + l, Color.BLACK);
                                }
                            }
                        } else {
                            graphics.drawSquare(j*SIZE,i*SIZE,SIZE);
                        }
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            overLastFrame = new boolean[field.length][];
            for (int i = 0; i < field.length; i++) {
                field[i] = Arrays.copyOf(field[i], field[i].length);
            }

            field = game.frame();
        } while (!Arrays.deepEquals(field, overLastFrame) || cycle > 1000);
    }
}
