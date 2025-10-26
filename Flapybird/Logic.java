import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;
    int playerStartXPos = frameWidth / 2;
    int playerStartYPos = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    View view;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -2;

    boolean gameOver = true; // Mulai sbg "game over" sblm game-nya start
    int score = 0;
    JLabel scoreLabel;

    // --- Atribut Baru ---
    private App app; // Referensi ke App

    // --- Constructor diubah ---
    public Logic(App app) { // Terima App sebagai parameter
        this.app = app; // Simpan referensi App

        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartXPos, playerStartYPos, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        // Timer dibuat tapi TIDAK di-start
        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });

        gameLoop = new Timer(1000 / 60, this);
    }

    public void setScoreLabel(JLabel label) {
        this.scoreLabel = label;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public void placePipes() {
        // (Tidak ada perubahan di sini)
        int randomPosY = (int) (pipeStartPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        // (Logika skor dan tabrakan sama seperti Bagian 1)
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        if (player.getPosY() + player.getHeight() >= frameHeight) {
            player.setPosY(frameHeight - player.getHeight());
            gameOver = true;
        }

        Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            if (i % 2 == 0) {
                Pipe upperPipe = pipe;
                if (!upperPipe.isPassed() && player.getPosX() > upperPipe.getPosX() + upperPipe.getWidth()) {
                    upperPipe.setPassed(true);
                    if (i + 1 < pipes.size()) {
                        pipes.get(i + 1).setPassed(true);
                    }
                    score++;
                    if (scoreLabel != null) {
                        scoreLabel.setText("Score: " + score);
                    }
                }
            }

            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());
            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
            }
        }

        if (gameOver) {
            // Hentikan KEDUA timer
            gameLoop.stop();
            pipesCooldown.stop();
        }
    }

    // --- Method Restart diubah ---
    public void restart() {
        // Hentikan timer (jika kebetulan masih jalan)
        gameLoop.stop();
        pipesCooldown.stop();

        // Reset posisi player
        player.setPosY(playerStartYPos);
        player.setVelocityY(0);

        // Kosongkan pipa
        pipes.clear();

        // Reset skor
        score = 0;
        if (scoreLabel != null) {
            scoreLabel.setText("Score: 0");
        }

        // Reset status game
        gameOver = false; // Game dimulai!

        // Mulai ulang SEMUA timer
        gameLoop.start();
        pipesCooldown.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // actionPerformed HANYA dipanggil oleh gameLoop
        // Jika game over, gameLoop akan berhenti, jadi kita tidak perlu
        // cek `!gameOver` di sini lagi (karena sudah dicek di `move()`)
        move();

        if (view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // --- keyPressed diubah ---
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                player.setVelocityY(-10);
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                restart(); // Restart game
            }
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            if (gameOver) {
                // Hentikan timer (untuk memastikan) dan kembali ke menu
                gameLoop.stop();
                pipesCooldown.stop();
                app.showMenu(); // Panggil method di App
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}