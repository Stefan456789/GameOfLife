import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class SimpleGraphicsLibrary extends JPanel {
    private final int width;
    private final int height;
    private final Color background;
    private int[] pixels;
    private static boolean clicked = false;

    private static SimpleGraphicsLibrary lib;
    private static MouseEvent lastEvent;

    private BiConsumer<Integer,Integer> mouseDraggedEvent;

    private Graphics g;

    public SimpleGraphicsLibrary(int width, int height, Color background, BiConsumer<Integer,Integer> mouseDraggedEvent) {
        lib = this;
        this.width = width;
        this.height = height;
        this.background = background;
        this.mouseDraggedEvent = mouseDraggedEvent;

        JFrame frame = new JFrame("Simple Graphics Library");
        Thread mouseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        this.wait();
                    } catch (InterruptedException ignored) {
                    }
                    while (SimpleGraphicsLibrary.clicked){
                        Point p = SimpleGraphicsLibrary.lastEvent.getPoint();
                        lib.mouseDraggedEvent.accept(p.x,p.y);
                    }
                }
            }
        });
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                clicked = true;
                lastEvent = e;
                mouseThread.notify();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                clicked = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        this.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
    }

    public void setPixel(int x, int y, Color color) {
        if (pixels == null)
            clear();
        int index = y * width + x;
        pixels[index] = color.getRGB();
        repaint();
    }


    //TODO Not working, using backup pixelarray methode instead.
    public void drawSquare(int x, int y, int size){
        g.setColor(Color.BLACK);
        g.fillRect(x,y,size,size);
        repaint();
    }

    public void clear() {
        this.pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                pixels[index] = background.getRGB();
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;

        if (pixels != null){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    Color color = new Color(pixels[index]);
                    g.setColor(color);
                    g.drawLine(x, y, x, y);
                }
            }
        }
    }
}