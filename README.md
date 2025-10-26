# 🐦 Flappy Bird Game

Ini adalah game Flappy Bird sederhana yang dibuat menggunakan Java Swing. Pemain mengontrol seekor burung, mencoba terbang melewati rintangan pipa tanpa menabrak.

## 🎨 Desain Program

Program ini dibangun menggunakan beberapa kelas yang masing-masing memiliki tanggung jawab spesifik, memisahkan antara logika, tampilan, dan data.

* **`App.java` (Kontroler Utama)**
    * Bertanggung jawab untuk membuat `JFrame` (jendela utama) program.
    * Menggunakan `CardLayout` untuk beralih antara dua panel utama: `MainMenu` dan panel Game (`gameContainer`).
    * Menginisialisasi `Logic`, `View`, dan `MainMenu`.
    * Menangani perpindahan antar panel (misalnya, dari menu ke game).

* **`MainMenu.java` (Tampilan Menu)**
    * Sebuah `JPanel` yang berfungsi sebagai layar menu utama.
    * Menampilkan judul game dan dua tombol: "Play Game" dan "Exit".
    * Menangani klik tombol untuk memberi tahu `App.java` agar memulai game atau menutup program.

* **`Logic.java` (Mesin Game / Logika)**
    * Kelas inti yang berisi semua logika permainan.
    * Mengimplementasikan `ActionListener` untuk `Timer` (game loop) dan `KeyListener` untuk input pemain.
    * Mengelola dua `Timer`:
        1.  `gameLoop`: Berdetak ~60 kali per detik untuk memperbarui fisika, menggerakkan objek, dan memeriksa tabrakan.
        2.  `pipesCooldown`: Berdetak setiap 1.5 detik untuk memunculkan pasangan pipa baru.
    * Mengelola status game (`gameOver`), skor (`score`), gravitasi, dan pergerakan.
    * Menyimpan *list* (`ArrayList`) dari semua objek `Pipe` yang ada di layar.
    * Menyediakan *getter* untuk `View` agar bisa mengambil data yang akan digambar.

* **`View.java` (Tampilan Game)**
    * Sebuah `JPanel` yang bertanggung jawab untuk *menggambar* semua elemen game.
    * Meng-override method `paintComponent(Graphics g)` untuk menggambar burung (`Player`), semua pipa (`Pipe`), dan pesan "Game Over" / "Restart".
    * Mendapatkan data yang akan digambar (seperti posisi player dan pipa) dari kelas `Logic`.

* **`Player.java` (Model Data)**
    * Kelas *data class* (model) yang menyimpan semua informasi tentang pemain.
    * Atribut meliputi posisi (`posX`, `posY`), ukuran (`width`, `height`), `Image`, dan kecepatan vertikal (`velocityY`).

* **`Pipe.java` (Model Data)**
    * Kelas *data class* (model) yang menyimpan semua informasi tentang satu pipa.
    * Atribut meliputi posisi (`posX`, `posY`), ukuran, `Image`, dan status `passed` (untuk menandai apakah sudah dilewati untuk penambahan skor).

## 🚀 Alur Program

1.  **Inisialisasi**: Pengguna menjalankan file `App.java`.
2.  **Tampil Menu**: `App` membuat `JFrame` dan `CardLayout`, lalu segera menampilkan panel `MainMenu`.
3.  **Opsi Menu**:
    * Jika pemain menekan **"Exit"**, program akan ditutup (`System.exit(0)`).
    * Jika pemain menekan **"Play Game"**:
        1.  `MainMenu` memanggil method `app.startGame()`.
        2.  `App` mengganti `CardLayout` untuk menampilkan panel game.
        3.  `App` meminta *fokus* pada `gameView` (agar `KeyListener` berfungsi).
        4.  `App` memanggil `gameLogic.restart()`.
4.  **Permainan Dimulai**:
    * `gameLogic.restart()` mengatur ulang posisi pemain, skor, mengosongkan daftar pipa, dan **memulai** `gameLoop` dan `pipesCooldown`.
5.  **Game Loop**:
    * `gameLoop` berjalan terus-menerus. Setiap detiknya:
        1.  Memanggil `logic.move()`:
        2.  Gravitasi ditambahkan ke `velocityY` pemain.
        3.  Posisi `posY` pemain diperbarui.
        4.  Semua pipa di `ArrayList` digerakkan ke kiri (`pipeVelocityX`).
        5.  Dilakukan **Deteksi Tabrakan** (antara `Player` dan setiap `Pipe`, serta antara `Player` dan lantai).
        6.  Dilakukan **Pengecekan Skor** (jika `Player` melewati `Pipe` yang belum ditandai `passed`).
        7.  Memanggil `view.repaint()` untuk menggambar ulang layar dengan posisi baru.
    * `pipesCooldown` berjalan terpisah. Setiap 1.5 detik, ia memanggil `logic.placePipes()` untuk menambah dua pipa baru (atas dan bawah) ke dalam `ArrayList`.
6.  **Input Pemain**:
    * Saat pemain menekan **Tombol Spasi**, `Logic.keyPressed()` mendeteksi dan mengatur `player.velocityY` menjadi nilai negatif (misal: -10), menyebabkan burung "melompat".
7.  **Game Over**:
    * Jika `logic.move()` mendeteksi tabrakan:
        1.  Variabel `gameOver` diatur menjadi `true`.
        2.  `gameLoop` dan `pipesCooldown` **dihentikan** (`.stop()`).
        3.  `view.repaint()` akan menggambar pesan "Game Over" karena `logic.isGameOver()` bernilai `true`.
8.  **Opsi Setelah Game Over**:
    * Jika pemain menekan **Tombol R**: `Logic.keyPressed()` memanggil `gameLogic.restart()` dan alur kembali ke Langkah 4.
    * Jika pemain menekan **Tombol M**: `Logic.keyPressed()` memanggil `app.showMenu()` dan alur kembali ke Langkah 2.

## 🎮 Dokumentasi Penggunaan

### Persyaratan
* Java Development Kit (JDK) terinstal.
* File gambar (`bird.png`, `lowerPipe.png`, `upperPipe.png`) harus berada di dalam folder bernama `assets`. Folder `assets` ini harus berada di *classpath* (biasanya, di direktori yang sama dengan file `.class` setelah kompilasi, atau di dalam folder `src` jika menggunakan IDE).

### Menjalankan Program

1.  Buka terminal atau command prompt.
2.  Navigasi ke direktori tempat semua file `.java` disimpan.
3.  Kompilasi semua file Java:
    ```bash
    javac *.java
    ```
4.  Jalankan file `App` (kelas yang berisi method `main`):
    ```bash
    java App
    ```
5.  Program akan berjalan dan menampilkan Main Menu.

### Kontrol

* **Main Menu**
    * **Tombol Play Game**: Memulai permainan.
    * **Tombol Exit**: Keluar dari aplikasi.

* **Dalam Game**
    * **Tombol Spasi**: Membuat burung melompat.

* **Layar Game Over**
    * **Tombol R**: Memulai ulang permainan (Restart).
    * **Tombol M**: Kembali ke Main Menu.
