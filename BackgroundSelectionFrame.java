package Project3_6581204;

import static Project3_6581204.MyConstants.FRAMEHEIGHT;
import static Project3_6581204.MyConstants.FRAMEWIDTH;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BackgroundSelectionFrame extends JFrame implements MyConstants {
    private String playerName;
    private int selectedDifficulty;
    private static String selectedBackground; 
    private MySoundEffect clickSound;

    public BackgroundSelectionFrame(String playerName, int selectedDifficulty) {
        this.playerName = playerName;
        this.selectedDifficulty = selectedDifficulty;

        setTitle("Select Background");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // ✅ Added spacing
        
        // ✅ Load sound effect
        clickSound = new MySoundEffect(FILE_SFX_CLICK);


        // ✅ Title Label
        JLabel titleLabel = new JLabel("Choose a Background", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // ✅ Background Selection Panel (Radio Buttons)
        JPanel backgroundPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 rows, spacing
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Add padding

        JRadioButton bg1 = new JRadioButton("Venus");
        JRadioButton bg2 = new JRadioButton("Jupiter");
        JRadioButton bg3 = new JRadioButton("Uranus");
        JRadioButton bg4 = new JRadioButton("Mars");
        JRadioButton bg5 = new JRadioButton("Saturn");


        ButtonGroup group = new ButtonGroup();
        group.add(bg1);
        group.add(bg2);
        group.add(bg3);
        group.add(bg4);
        group.add(bg5);

        // ✅ Play click sound when a background is selected
        bg1.addActionListener(e -> {
            clickSound.playOnce();
            selectedBackground = FILE_BG_VENUS;
        });
        bg2.addActionListener(e -> {
            clickSound.playOnce();
            selectedBackground = FILE_BG_JUPITER;
        });
        bg3.addActionListener(e -> {
            clickSound.playOnce();
            selectedBackground = FILE_BG_URANUS;
        });
        bg4.addActionListener(e -> {
            clickSound.playOnce();
            selectedBackground = FILE_BG_MARS;
        });
        bg5.addActionListener(e -> {
            clickSound.playOnce();
            selectedBackground = FILE_BG_SATURN;
        });

        // ✅ Add radio buttons to the panel
        backgroundPanel.add(bg1);
        backgroundPanel.add(bg2);
        backgroundPanel.add(bg3);
        backgroundPanel.add(bg4);
        backgroundPanel.add(bg5);
        add(backgroundPanel, BorderLayout.CENTER);
        

        // ✅ Start Game Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Add spacing
        JButton startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 22));
        startGameButton.setPreferredSize(new Dimension(250, 50)); // Bigger button
         // ✅ Play sound when clicking "Start Game"
        startGameButton.addActionListener(e -> {
            clickSound.playOnce();
            startGame();
        });

        buttonPanel.add(startGameButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startGame() {
        JFrame gameFrame = new JFrame("Angry Ajarn Game - " + playerName);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT); // ✅ Same size as Main Menu
        gameFrame.setContentPane(new MainGame(playerName, selectedDifficulty, selectedBackground));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        this.dispose();
    }
    
    public static String getSelectedBackground() {
        return selectedBackground;
    }
}

