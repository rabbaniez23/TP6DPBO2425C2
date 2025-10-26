import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel ini adalah tampilan menu utama
public class MainMenu extends JPanel implements ActionListener {

    private JButton playButton;
    private JButton exitButton;
    private App app; // Referensi ke App utama untuk beralih panel

    public MainMenu(App app) {
        this.app = app;

        setPreferredSize(new Dimension(360, 640)); // Samakan ukuran
        setBackground(Color.CYAN); // Samakan tema
        setLayout(new GridBagLayout()); // Rata tengah

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        // Judul Game
        JLabel title = new JLabel("Flappy Bird");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        gbc.gridy = 0;
        add(title, gbc);

        // Tombol Play
        playButton = new JButton("   Play Game   ");
        playButton.setFont(new Font("Arial", Font.PLAIN, 24));
        playButton.addActionListener(this);
        gbc.gridy = 1;
        add(playButton, gbc);

        // Tombol Exit
        exitButton = new JButton("      Exit      ");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.addActionListener(this);
        gbc.gridy = 2;
        add(exitButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            app.startGame(); // Panggil method di App untuk mulai
        } else if (e.getSource() == exitButton) {
            System.exit(0); // Tutup program
        }
    }
}