/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Project3_6581204;

/**
 *
 * @author  Sukumarn Srimai 6581085
            Rueti Limpacharoenkul 6581166
            Chantapat Tivalai 6581193
            Thanarat Bunbangyang 6581195
            Banthawan Udomsap 6581204

 */
import static Project3_6581204.MyConstants.FRAMEWIDTH;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;


public class MainGame extends JPanel implements MyConstants {
    private MyImageIcon backgroundImg;
    private MyImageIcon characterGif;
    private MyImageIcon characterHitGif;
    private MyImageIcon characterJumpGif;
    private MyImageIcon oldCharacterGif; // ✅ Store the old character animation
    private int bgX;
    private int characterX, characterY;
    private boolean isHitting;
    private boolean isJumping;
    private MySoundEffect themeMusic;

    private final int JUMP_HEIGHT = 180; // How high the character jumps
    private final int JUMP_SPEED = 450;  // Duration of the jump (in ms)
    private final int FLOOR_Y = FLOOR_DOWN - CHARACTERHEIGHT; // Ground position
    private final int MOVE_SPEED = 15; // 🚀 Character moves 15 pixels per key press
    private final int MAX_RIGHT = FRAMEWIDTH - CHARACTERWIDTH - 50; // 🚧 Right boundary
    private final int MIN_LEFT = 20; // 🚧 Left boundary
    private int hearts = 3;  // 💔 Player starts with 3 hearts
    private String playerName; // ✅ Store Player's Name
    
    private MyImageIcon alienImg;  // 👽 Alien enemy image
    private int alienX, alienY;    // Alien's position
    private final int ALIEN_SPEED = 5;  // 👽 Speed of the alien moving left
    private boolean alienVisible = true; // 👽 Track if the alien is visible
    private Random random = new Random(); // ✅ Random generator
    
     private MyImageIcon studentPam, studentSea, studentRueti, studentBingo, studentTaro;
    private int studentPamX, studentPamY;
    private int studentSeaX, studentSeaY;
    private int studentRuetiX, studentRuetiY;
    private int studentBingoX, studentBingoY;
    private int studentTaroX, studentTaroY;
    private boolean studentPamVisible, studentSeaVisible, studentRuetiVisible, studentBingoVisible, studentTaroVisible;
    private final int STUDENT_SPEED = 3;  // 👨‍🎓 Speed of Student Movement
    private Timer studentTimer;  // Timer for Moving Student
    
    private MyImageIcon unicornGif = new MyImageIcon(FILE_UNICORN).resize(120, 120); // Adjust size as needed
    private int unicornX, unicornY;
    private boolean unicornDragging = false;
    private Timer returnUnicornTimer;


    private int score = 0;  // 🏆 Player score
    private int bgSpeed;
    boolean arrowKeyPressed = false; // ✅ Track manual movement

    

    public MainGame(String playerName, int selectedDifficulty, String selectedBackground) {
        this.playerName = playerName; // ✅ Store player's name
        
        
         // ✅ Set Background Speed Based on Difficulty
        switch (selectedDifficulty) {
            case 0 -> bgSpeed = 1;  // Easy
            case 1 -> bgSpeed = 3;  // Normal
            case 2 -> bgSpeed = 5;  // Hard
            case 3 -> bgSpeed = 8;  // Extreme
            case 4 -> bgSpeed = 12; // Impossible
            default -> bgSpeed = 3; // Default to Normal
        }
        
        setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        setLayout(null); // Allow absolute positioning for button
         
        backgroundImg = new MyImageIcon(selectedBackground).resize(FRAMEWIDTH, FRAMEHEIGHT);
        
        // Load character GIF clearly
        characterGif = new MyImageIcon(FILE_CHARACTER_MOVE).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        characterHitGif = new MyImageIcon(FILE_CHARACTER_HIT).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        characterJumpGif = new MyImageIcon(FILE_CHARACTER_JUMP).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        
       // ✅ Set initial Unicorn position above the sky
        unicornX = FRAMEWIDTH / 2 - 60;  // Centered horizontally
        unicornY = 50;  // Above the sky

       
        // ✅ Load Student Images
        studentPam = new MyImageIcon(FILE_PAM).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        studentSea = new MyImageIcon(FILE_SEA).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        studentRueti = new MyImageIcon(FILE_RUETI).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        studentBingo = new MyImageIcon(FILE_BINGO).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        studentTaro = new MyImageIcon(FILE_TARO).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        
        // ✅ Hardcoded Student Positions
        resetStudentPam();
        resetStudentSea();
        resetStudentRueti();
        resetStudentBingo();
        resetStudentTaro();
        
        // ✅ Hardcoded Positions
        studentPamX = FRAMEWIDTH;
        studentPamY = FLOOR_Y;
        studentPamVisible = true;

        studentSeaX = FRAMEWIDTH + 200;
        studentSeaY = FLOOR_Y - 20;
        studentSeaVisible = true;

        studentRuetiX = FRAMEWIDTH + 400;
        studentRuetiY = FLOOR_Y + 10;
        studentRuetiVisible = true;

        studentBingoX = FRAMEWIDTH + 600;
        studentBingoY = FLOOR_Y - 15;
        studentBingoVisible = true;

        studentTaroX = FRAMEWIDTH + 800;
        studentTaroY = FLOOR_Y + 5;
        studentTaroVisible = true;

        
        // ✅ Timer for Moving Students
        Timer studentTimer = new Timer(10, e -> {
            if (studentPamVisible) {
                studentPamX -= STUDENT_SPEED;
                if (studentPamX < -CHARACTERWIDTH) {
                    studentPamX = FRAMEWIDTH + random.nextInt(500);
                }
            }
            if (studentSeaVisible) {
                studentSeaX -= STUDENT_SPEED;
                if (studentSeaX < -CHARACTERWIDTH) {
                    studentSeaX = FRAMEWIDTH + random.nextInt(500);
                }
            }
            if (studentRuetiVisible) {
                studentRuetiX -= STUDENT_SPEED;
                if (studentRuetiX < -CHARACTERWIDTH) {
                    studentRuetiX = FRAMEWIDTH + random.nextInt(500);
                }
            }
            if (studentBingoVisible) {
                studentBingoX -= STUDENT_SPEED;
                if (studentBingoX < -CHARACTERWIDTH) {
                    studentBingoX = FRAMEWIDTH + random.nextInt(500);
                }
            }
            if (studentTaroVisible) {
                studentTaroX -= STUDENT_SPEED;
                if (studentTaroX < -CHARACTERWIDTH) {
                    studentTaroX = FRAMEWIDTH + random.nextInt(500);
                }
            }
            repaint();
        });
        studentTimer.start();

        // adjust scale of character
        isHitting = false;
        isJumping = false;
        bgX = 0;
        characterX = 20;
        characterY = FLOOR_Y; // position character clearly on ground
        
        // ✅ Load Alien Image
        alienImg = new MyImageIcon(FILE_ENEMY).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        alienX = FRAMEWIDTH - 100; // 👽 Start from the right side
        alienY = FLOOR_DOWN - CHARACTERHEIGHT; // 👽 Same height as the character
                
        // Play background music
        themeMusic = new MySoundEffect(FILE_THEME);
        themeMusic.playLoop();
        themeMusic.setVolume(0.5f);
        
       

        // ✅ Start Background Timer with Updated Speed
        Timer bgTimer = new Timer(20, e -> {
            bgX -= bgSpeed; // ✅ Use difficulty-adjusted speed
            if (bgX <= -FRAMEWIDTH) {
                bgX = 0;
            }
            repaint();
        });
        bgTimer.start();
        
        
        // ✅ Timer for Moving Alien Left
        Timer alienTimer = new Timer(50, e -> {
            alienX -= ALIEN_SPEED; // 👽 Move left
            if (alienX < -CHARACTERWIDTH) {
                alienX = FRAMEWIDTH; // 👽 Reset alien to right side when off-screen
            }
            repaint();
        });
        alienTimer.start();
        
       
         // ✅ KeyEvent Handler: Spacebar to jump
        setFocusable(true);
        requestFocusInWindow(); // ✅ Ensure keyboard focus is on the panel
        
        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                arrowKeyPressed = true; // ✅ Disable unicorn follow
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    triggerJump();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    unicornX = Math.max(unicornX - MOVE_SPEED, MIN_LEFT); // 🚀 Move left (stop at left boundary)
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    unicornX = Math.min(unicornX + MOVE_SPEED, MAX_RIGHT); // 🚀 Move right (stop at right boundary)
                    repaint();
                }
            }

            @Override public void keyReleased(KeyEvent e) {}
        });
        
        // ✅ MouseEvent Handler: Mouse Click on Button
        JButton attackButton = new JButton("Attack!");
        attackButton.setFont(new Font("Arial", Font.BOLD, 30)); // Bigger text
        attackButton.setBounds(FRAMEWIDTH - 170, FRAMEHEIGHT - 80, 150, 50); // Position & size
        
        // ✅ Use ActionListener instead of MouseListener (Fixes Double Attack Issue)
        attackButton.addActionListener(e -> {
            triggerHitAnimation();
            requestFocusInWindow(); // ✅ Keep focus on game panel after clicking
        });
        add(attackButton);
        

        // ✅ MouseEvent Handler: Drag Unicorn to Aliens
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                unicornDragging = true;
                // ✅ Update Unicorn position while dragging
                unicornX += (e.getX() - unicornX - 60) * 0.2; // Center Unicorn on mouse
                unicornY += (e.getY() - unicornY - 60) * 0.2;

                // ✅ Check for collision with Alien
                if (unicornX + 120 >= alienX && unicornX <= alienX + CHARACTERWIDTH &&
                    unicornY + 120 >= alienY && unicornY <= alienY + CHARACTERHEIGHT) {

                    // ✅ Remove the Alien when hit by Unicorn
                    alienVisible = false; 
                    Timer respawnTimer = new Timer(2000, evt -> resetAlien()); // Respawn after 2 sec
                    respawnTimer.setRepeats(false);
                    respawnTimer.start();
                }

                repaint();
            }

            @Override public void mouseMoved(MouseEvent e) {}
            });
        // ✅ MouseListener to Reset Unicorn Position When Mouse Released
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    unicornDragging = false;
                    if (returnUnicornTimer != null && returnUnicornTimer.isRunning()) {
                        returnUnicornTimer.stop();
                    }

                    // ✅ Smoothly Move Unicorn Back Above the Character
                returnUnicornTimer = new Timer(10, evt -> {
                unicornX += (characterX - unicornX) * 0.2;
                unicornY += ((characterY - 150) - unicornY) * 0.2;

                // ✅ Stop the animation when close enough
                if (Math.abs(unicornX - characterX) < 1 && Math.abs(unicornY - (characterY - 150)) < 1) {
                    unicornX = characterX;
                    unicornY = characterY - 150;
                    returnUnicornTimer.stop();
                    }
                    repaint();
            });

           returnUnicornTimer.start();
            }
        });
             // ✅ Timer to Keep Unicorn Above the Character When Not Dragging
            Timer unicornFollowTimer = new Timer(10, e -> {
                if (!unicornDragging && !arrowKeyPressed) {
                    unicornX = characterX;
                    unicornY = characterY - 150;
                    repaint();
                }
            });
            unicornFollowTimer.start();
        }

        // Trigger hit animation (used for spacebar and attack button)
        private void triggerHitAnimation() {
            isHitting = true;
            repaint();
            
            // ✅ Check for Collision & decrease Score
            if (characterX + CHARACTERWIDTH >= alienX && characterX <= alienX + CHARACTERWIDTH) {
                hearts -= 1; // 💔 Lose 1 heart
                //soundLose.play(); // 🔊 Play losing sound
                alienVisible = false; // ❌ Hide the alien
                Timer respawnTimer = new Timer(2000, evt -> resetAlien()); // ⏳ Respawn after 2 sec
                respawnTimer.setRepeats(false);
                respawnTimer.start();
                
                // ✅ Check for Game Over (If 3 hearts lost)
            if (hearts <= 0) {
                //soundGameOver.playOnce(); // 🔊 Play Game Over sound
                gameOver();
                System.exit(0); // ❌ Exit Game
                }
            }
            
                // ✅ Check for Collision with Students
            if (studentPamVisible && characterX + CHARACTERWIDTH >= studentPamX && characterX <= studentPamX + CHARACTERWIDTH) {
                score++;
                studentPamVisible = false;
                Timer respawn = new Timer(2000, e -> studentPamVisible = true);
                respawn.setRepeats(false);
                respawn.start();
            }
            if (studentSeaVisible && characterX + CHARACTERWIDTH >= studentSeaX && characterX <= studentSeaX + CHARACTERWIDTH) {
                score++;
                studentSeaVisible = false;
                Timer respawn = new Timer(2000, e -> studentSeaVisible = true);
                respawn.setRepeats(false);
                respawn.start();
            }
            if (studentRuetiVisible && characterX + CHARACTERWIDTH >= studentRuetiX && characterX <= studentRuetiX + CHARACTERWIDTH) {
                score++;
                studentRuetiVisible = false;
                Timer respawn = new Timer(2000, e -> studentRuetiVisible = true);
                respawn.setRepeats(false);
                respawn.start();
            }
            if (studentBingoVisible && characterX + CHARACTERWIDTH >= studentBingoX && characterX <= studentBingoX + CHARACTERWIDTH) {
                score++;
                studentBingoVisible = false;
                Timer respawn = new Timer(2000, e -> studentBingoVisible = true);
                respawn.setRepeats(false);
                respawn.start();
            }
            if (studentTaroVisible && characterX + CHARACTERWIDTH >= studentTaroX && characterX <= studentTaroX + CHARACTERWIDTH) {
                score++;
                studentTaroVisible = false;
                Timer respawn = new Timer(2000, e -> studentTaroVisible = true);
                respawn.setRepeats(false);
                respawn.start();
            }


                Timer hitTimer = new Timer(1000, evt -> {
                    isHitting = false;
                    repaint();
                });
                hitTimer.setRepeats(false);
                hitTimer.start();
            }
        
        private void resetAlien() {
                alienX = FRAMEWIDTH + random.nextInt(300); // 👽 Start from right, with random delay
                alienY = FLOOR_DOWN - CHARACTERHEIGHT - random.nextInt(50); // 👽 Vary height slightly
                alienVisible = true; // ✅ Show alien again
            }
             // ✅ Reset Each Student
        private void resetStudentPam() {
            studentPamX = FRAMEWIDTH + random.nextInt(500);
            studentPamY = FLOOR_Y + random.nextInt(20) - 10;
            studentPamVisible = true;
        }

        private void resetStudentSea() {
            studentSeaX = FRAMEWIDTH + random.nextInt(500);
            studentSeaY = FLOOR_Y + random.nextInt(20) - 10;
            studentSeaVisible = true;
        }

        private void resetStudentRueti() {
            studentRuetiX = FRAMEWIDTH + random.nextInt(500);
            studentRuetiY = FLOOR_Y + random.nextInt(20) - 10;
            studentRuetiVisible = true;
        }

        private void resetStudentBingo() {
            studentBingoX = FRAMEWIDTH + random.nextInt(500);
            studentBingoY = FLOOR_Y + random.nextInt(20) - 10;
            studentBingoVisible = true;
        }

        private void resetStudentTaro() {
            studentTaroX = FRAMEWIDTH + random.nextInt(500);
            studentTaroY = FLOOR_Y + random.nextInt(20) - 10;
            studentTaroVisible = true;
        }

     
    // ✅ Jump Mechanic: Character jumps when pressing Spacebar
    private void triggerJump() {
        if (!isJumping) { // Prevent multiple jumps
            isJumping = true;
            
            // ✅ Store current animation before switching to jump animation
            oldCharacterGif = characterGif; 
            characterGif = characterJumpGif;
        
            characterY -= JUMP_HEIGHT; // Move up
            repaint();

            // Return character to ground after a delay
            Timer jumpTimer = new Timer(JUMP_SPEED, evt -> {
                characterY = FLOOR_Y; // Reset to ground
                isJumping = false;
                characterGif = oldCharacterGif; // ✅ Restore original animation
                repaint();
            });
            jumpTimer.setRepeats(false);
            jumpTimer.start();
        }
    }

    private void gameOver() {
        themeMusic.stop(); // ✅ Stop Background Music
        //soundGameOver.play(); // 🔊 Play Game Over Sound

        // ✅ Show Game Over Message with Player Name
        JOptionPane.showMessageDialog(null, 
            "Game Over, " + playerName + "! \nYour Final Score: " + score, 
            "Game Over", 
            JOptionPane.INFORMATION_MESSAGE);
        
        System.exit(0); // ❌ Exit Game
    }

    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundImg.paintIcon(this, g, bgX, 0);
        backgroundImg.paintIcon(this, g, bgX + FRAMEWIDTH, 0);
       
        
        // ✅ Draw Unicorn Above the Sky
        unicornGif.paintIcon(this, g, unicornX, unicornY);
        
        if (isHitting) {
            characterHitGif.paintIcon(this, g, characterX, characterY);
        } else {
            characterGif.paintIcon(this, g, characterX, characterY);
        }
        // ✅ Draw Alien Only If Visible
        if (alienVisible) {
            alienImg.paintIcon(this, g, alienX, alienY);
        }
        
         // ✅ Draw Students Only If Visible
        if (studentPamVisible) studentPam.paintIcon(this, g, studentPamX, studentPamY);
        if (studentSeaVisible) studentSea.paintIcon(this, g, studentSeaX, studentSeaY);
        if (studentRuetiVisible) studentRueti.paintIcon(this, g, studentRuetiX, studentRuetiY);
        if (studentBingoVisible) studentBingo.paintIcon(this, g, studentBingoX, studentBingoY);
        if (studentTaroVisible) studentTaro.paintIcon(this, g, studentTaroX, studentTaroY);

        // ✅ Draw Score Bar at Top-Left
        g.setColor(Color.BLACK);
        g.fillRect(20, 20, 120, 30); // 📊 Background box for score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 30, 40); // 🏆 Display score
        
        // ✅ Draw Hearts (Top-Right Corner)
        g.setColor(Color.RED);
        for (int i = 0; i < hearts; i++) {
            g.fillOval(FRAMEWIDTH - 120 + (i * 30), 20, 20, 20); // ❤️ Draw heart symbols
        }
    }

    // Main method to run your application clearly
    public static void main(String[] args) {
        // ✅ Prompt user for their name
        String playerName = JOptionPane.showInputDialog(null, "Enter Your Name:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player"; // ✅ Default name if empty
        }

        // ✅ Create game window
        JFrame frame = new JFrame("Angry Ajarn Game - " + playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ✅ Pass player name to MainGame
        int difficulty = NameFrame.getSelectedDifficulty();
        String selectedBackground = BackgroundSelectionFrame.getSelectedBackground();
        MainGame app = new MainGame(playerName, difficulty, selectedBackground);
        frame.setContentPane(app);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // ✅ Window Event Handler: Print message when closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Game is closing...");
                System.exit(0);
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }
}
