import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class NQueen {
    final static int M = 8;
    static JLabel[][] jLabel = new JLabel[M][M];
    static int board[][] = new int[M][M];   
    static Image queenImage;

    static {
        try {
            // Load queen image
            queenImage = ImageIO.read(new File("queen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printSolution() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                System.out.printf("%d ", board[i][j]);
            }
            System.out.printf("\n");
        }
    }

    static boolean isSafe(int row, int col) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < col; ++i)
            if (board[row][i] == 1)
                return false;

        for (int i = row, j = col; i >= 0 && j >= 0; --i, --j) {
            if (board[i][j] == 1)
                return false;
        }

        for (int i = row, j = col; i < M && j >= 0; ++i, --j) {
            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    static boolean findSolution(int col) {
        if (col >= M)
            return true;

        for (int i = 0; i < M; ++i) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSafe(i, col)) {
                board[i][col] = 1;
                setQueenIcon(i, col, true);

                if (findSolution(col + 1))
                    return true;

                board[i][col] = 0;
                setQueenIcon(i, col, false);
            }
        }

        return false;
    }

    static void solveNQueen() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                board[i][j] = 0;
                jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                jLabel[i][j].setIcon(null);
            }
        }

        if (!findSolution(0))
            System.out.println("No Solution.\n");
        else
            printSolution();
    }

    static void setQueenIcon(int row, int col, boolean set) {
        if (set) {
            int cellSize = jLabel[row][col].getWidth();
            Image scaledImage = queenImage.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
            ImageIcon queenIcon = new ImageIcon(getScaledImage(queenImage, cellSize, cellSize));
            jLabel[row][col].setIcon(queenIcon);
        } else {
            jLabel[row][col].setIcon(null);
        }
    }

    static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    NQueen() {
        JFrame jFrame = new JFrame("NQueen Visualizer.");
        jFrame.setLayout(new GridLayout(M, M));
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < M; ++j) {
                jLabel[i][j] = new JLabel();
                jLabel[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i][j].setOpaque(true);
                jLabel[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                jFrame.add(jLabel[i][j]);
            }
        }

        jFrame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(NQueen::new);
        solveNQueen();
    }
}
