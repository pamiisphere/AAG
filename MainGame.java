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
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    private int bgX;
    private int characterFrameIndex;
    private int characterX, characterY;
    private boolean isHitting;
    private boolean isJumping;
    private MySoundEffect themeMusic;
    private MySoundEffect soundLose;
    private MySoundEffect soundPoint;
    private MySoundEffect soundGameOver;
    private final int JUMP_HEIGHT = 180; // How high the character jumps
    private final int JUMP_SPEED = 450;  // Duration of the jump (in ms)
    private final int FLOOR_Y = FLOOR_DOWN - CHARACTERHEIGHT; // Ground position
    private final int MOVE_SPEED = 15; // 🚀 Character moves 15 pixels per key press
    private final int MAX_RIGHT = FRAMEWIDTH - CHARACTERWIDTH - 50; // 🚧 Right boundary
    private final int MIN_LEFT = 20; // 🚧 Left boundary
    private int hearts = 3;  // 💔 Player starts with 3 hearts
    
    private MyImageIcon alienImg;  // 👽 Alien enemy image
    private int alienX, alienY;    // Alien's position
    private final int ALIEN_SPEED = 5;  // 👽 Speed of the alien moving left
    private boolean alienVisible = true; // 👽 Track if the alien is visible
    private Random random = new Random(); // ✅ Random generator
    
    private MyImageIcon studentGif; 
    private int studentX, studentY;  // Student position
    private boolean studentVisible = true;  // Track if student is on-screen
    private final int STUDENT_SPEED = 3;  // 👨‍🎓 Speed of Student Movement
    private Timer studentTimer;  // Timer for Moving Student


    private int score = 0;  // 🏆 Player score

    

    public MainGame() {
        setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
        setLayout(null); // Allow absolute positioning for button
        backgroundImg = new MyImageIcon(FILE_BG_JUPITER).resize(FRAMEWIDTH, FRAMEHEIGHT);
        
        // Load character GIF clearly
        characterGif = new MyImageIcon(FILE_CHARACTER_MOVE).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        characterHitGif = new MyImageIcon(FILE_CHARACTER_HIT).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        
        // ✅ Load Student Image
        studentGif = new MyImageIcon(FILE_HEART).resize(CHARACTERWIDTH, CHARACTERHEIGHT);
        resetStudent(); // 🎓 Spawn student at a random position
        
        // ✅ Timer for Moving Student (Like Alien)
        studentTimer = new Timer(10, e -> {
            studentX -= STUDENT_SPEED; // 👨‍🎓 Move student left
            if (studentX < -CHARACTERWIDTH) {
                resetStudent(); // 🔄 Respawn student when off-screen
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
        
        characterFrameIndex = 0;
                
        // Play background music
        themeMusic = new MySoundEffect(FILE_THEME);
        themeMusic.playLoop();
        themeMusic.setVolume(0.5f);
        
        // ✅ Load Sound Effects
        soundLose = new MySoundEffect(FILE_SFX_BAD);
        soundPoint = new MySoundEffect(FILE_SFX_GOOD);
        soundGameOver = new MySoundEffect(FILE_GAMEOVER);


        // Fast background timer (20ms)
        Timer bgTimer = new Timer(20, e -> {
            updateBackground();
            repaint();
        });
        
        bgX = 0;
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
        addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    triggerJump();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    characterX = Math.max(characterX - MOVE_SPEED, MIN_LEFT); // 🚀 Move left (stop at left boundary)
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    characterX = Math.min(characterX + MOVE_SPEED, MAX_RIGHT); // 🚀 Move right (stop at right boundary)
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
        
        // ✅ MouseEvent Handler: Drag Character (MUST DELETE AND REPLACE WITH OTHERS, TO HAVE $ EVENT HANDLER)
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                characterX = e.getX();
                characterY = e.getY();
                repaint();
            }

            @Override public void mouseMoved(MouseEvent e) {}
        });
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
                //soundGameOver.play(); // 🔊 Play Game Over sound
                JOptionPane.showMessageDialog(null, "Game Over! Total Score: " + score, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // ❌ Exit Game
                }
            }
            
            // ✅ Check for Collision with Student (Increase Score)
            if (studentVisible && characterX + CHARACTERWIDTH >= studentX && characterX <= studentX + CHARACTERWIDTH) {
                score += 1; // 🏆 Increase score
                //soundPoint.play(); // 🔊 Play point sound
                studentVisible = false; // ❌ Hide student
                Timer studentRespawnTimer = new Timer(2000, evt -> resetStudent()); // ⏳ Respawn student
                studentRespawnTimer.setRepeats(false);
                studentRespawnTimer.start();
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
        private void resetStudent() {
            studentX = FRAMEWIDTH + random.nextInt(300); // 🎓 Start from right, with random delay
            studentY = FLOOR_DOWN - CHARACTERHEIGHT - random.nextInt(50); // 🎓 Vary height slightly
            studentVisible = true; // ✅ Show student again
        }
     
    // ✅ Jump Mechanic: Character jumps when pressing Spacebar
    private void triggerJump() {
        if (!isJumping) { // Prevent multiple jumps
            isJumping = true;
            characterY -= JUMP_HEIGHT; // Move up
            repaint();

            // Return character to ground after a delay
            Timer jumpTimer = new Timer(JUMP_SPEED, evt -> {
                characterY = FLOOR_Y; // Reset to ground
                isJumping = false;
                repaint();
            });
            jumpTimer.setRepeats(false);
            jumpTimer.start();
        }
    }

        

    private void updateBackground() {
        bgX -= BG_SCROLL_SPEED;
        if (bgX <= -FRAMEWIDTH) {
            bgX = 0;
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        backgroundImg.paintIcon(this, g, bgX, 0);
        backgroundImg.paintIcon(this, g, bgX + FRAMEWIDTH, 0);
        
        if (isHitting) {
            characterHitGif.paintIcon(this, g, characterX, characterY);
        } else {
            characterGif.paintIcon(this, g, characterX, characterY);
        }
        // ✅ Draw Alien Only If Visible
        if (alienVisible) {
            alienImg.paintIcon(this, g, alienX, alienY);
        }
        
        // ✅ Draw Student Only If Visible
        if (studentVisible) {
            studentGif.paintIcon(this, g, studentX, studentY);
        }

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
        JFrame frame = new JFrame("Angry Ajarn Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainGame app = new MainGame();
        frame.setContentPane(app);
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // ✅ WindowEvent Handler: Print message when closing
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
