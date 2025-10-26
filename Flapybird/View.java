import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int height = 640;

    private Logic logic;

    // constructor
    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // (Gambar Player dan Pipa tetap sama)
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);
        }

        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // --- Pesan Game Over Diperbarui ---
        if (logic.isGameOver()) {
            g.setFont(new Font("Arial", Font.BOLD, 40));

            // Bayangan
            g.setColor(Color.BLACK);
            g.drawString("Game Over!", 82, height / 2 - 48);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press 'R' to Restart", 92, height / 2 + 12);
            g.drawString("Press 'M' for Menu", 92, height / 2 + 42);

            // Teks Utama
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 80, height / 2 - 50);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press 'R' to Restart", 90, height / 2 + 10);
            g.drawString("Press 'M' for Menu", 90, height / 2 + 40);
        }
    }
}