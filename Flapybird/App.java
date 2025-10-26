import javax.swing.*;
import java.awt.*;

public class App {
    JFrame frame;
    JPanel mainPanel; // Panel utama dengan CardLayout
    CardLayout cardLayout;

    MainMenu mainMenu; // Panel Menu
    JPanel gameContainer; // Panel yang berisi Game + Skor
    View gameView;
    Logic gameLogic;

    public App() {
        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        // 1. Setup CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 2. Buat Panel Main Menu
        // Beri "this" agar MainMenu bisa memanggil startGame()
        mainMenu = new MainMenu(this);
        mainPanel.add(mainMenu, "MENU");

        // 3. Buat Panel Game (Container)
        gameContainer = new JPanel(new BorderLayout());

        // Kita butuh referensi "this" (App) di Logic
        // agar Logic bisa memanggil showMenu()
        gameLogic = new Logic(this);
        gameView = new View(gameLogic);
        gameLogic.setView(gameView);

        JLabel scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setForeground(Color.WHITE);
        gameLogic.setScoreLabel(scoreLabel);

        gameContainer.add(scoreLabel, BorderLayout.NORTH);
        gameContainer.add(gameView, BorderLayout.CENTER);

        mainPanel.add(gameContainer, "GAME");

        // 4. Selesaikan Frame
        frame.add(mainPanel);
        frame.pack(); // pack() akan menyesuaikan ukuran
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Mulai dengan menampilkan Menu
        cardLayout.show(mainPanel, "MENU");
    }

    // Dipanggil oleh MainMenu untuk beralih ke panel game
    public void startGame() {
        cardLayout.show(mainPanel, "GAME");
        gameView.requestFocusInWindow(); // PENTING untuk KeyListener
        gameLogic.restart(); // Mulai/Reset game
    }

    // Dipanggil oleh Logic untuk beralih kembali ke menu
    public void showMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    public static void main(String[] args) {
        // Menjalankan GUI di Event Dispatch Thread (praktik terbaik)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}