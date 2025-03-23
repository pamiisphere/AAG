/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Project3_6581204;

/**
 *
 * @author  Sukumarn Srimai 6581085
 *          Rueti Limpacharoenkul 6581166
 *          Chantapat Tivalai 6581193
 *          Thanarat Bunbangyang 6581195
 *          Banthawan Udomsap 6581204
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NameFrame extends JFrame implements MyConstants {
    private JTextField nameField;
    private JButton submitButton;
    private MySoundEffect themeMusic;
    private MainApplication menuReference;
    private JLabel nameLabel;
    private MyImageIcon backgroundImg; // ✅ Background Image
    private static String playerName; // ✅ Store Player's Name
    private static int selectedDifficulty; // ✅ Store Difficulty Level
    

    public NameFrame(MainApplication menuReference, MySoundEffect themeMusic) {
        this.menuReference = menuReference;
        this.themeMusic = themeMusic;

        setTitle("Enter Your Name");
        setSize(FRAMEWIDTH, FRAMEHEIGHT); // ✅ Same size as Main Menu
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ Load Custom Background Image
        backgroundImg = new MyImageIcon(FILE_MENU_BG).resize(FRAMEWIDTH, FRAMEHEIGHT);

        // ✅ Create Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                backgroundImg.paintIcon(this, g, 0, 0); // Draw background
            }
        };
        backgroundPanel.setLayout(null); // Absolute positioning

        // ✅ Name Input Label
        nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setBounds(500, 200, 300, 30); // Centered position

        // ✅ Name Input Field
        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nameField.setBounds(450, 250, 300, 40); // Wider input field

        // ✅ Submit Button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setBounds(550, 320, 150, 50);
        submitButton.setBackground(Color.ORANGE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);

        // ✅ Sound Effect for Click
        MySoundEffect clickSound = new MySoundEffect(FILE_SFX_CLICK);

        // ✅ ActionListener for Button Click
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitName(clickSound);
            }
        });

        // ✅ KeyListener for Enter Key Submission
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitName(clickSound);
                }
            }
        });

        // ✅ Add Components to Background Panel
        backgroundPanel.add(nameLabel);
        backgroundPanel.add(nameField);
        backgroundPanel.add(submitButton);

        // ✅ Add Background Panel to Frame
        add(backgroundPanel);
    }

    private void submitName(MySoundEffect clickSound) {
        clickSound.playOnce(); // Play click sound
        playerName = nameField.getText().trim();
        if (!playerName.isEmpty()) {
            openDifficultySelection(); // Open difficulty selection
            //openMainGame();
        } else {
            JOptionPane.showMessageDialog(NameFrame.this, "Please enter your name.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    // ✅ New Frame: Select Difficulty Level
    private void openDifficultySelection() {
        JFrame difficultyFrame = new JFrame("Select Difficulty");
        difficultyFrame.setSize(500, 300);
        difficultyFrame.setLocationRelativeTo(null);
        difficultyFrame.setLayout(new GridLayout(3, 1));

        JLabel difficultyLabel = new JLabel("Select Difficulty:", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 40));

        // ✅ Difficulty Options
        String[] difficulties = { "Easy", "Normal", "Hard", "Extreme", "Nightmare" };
        JComboBox<String> difficultyBox = new JComboBox<>(difficulties);
        difficultyBox.setFont(new Font("Arial", Font.PLAIN, 20));
        
        // ✅ Center the text inside the JComboBox
        difficultyBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER); // ✅ Center text
                return label;
            }
        });

        JButton selectButton = new JButton("Select");
        selectButton.setFont(new Font("Arial", Font.BOLD, 30));

        // ✅ Set Difficulty on Selection
        selectButton.addActionListener(e -> {
            selectedDifficulty = difficultyBox.getSelectedIndex(); // 0 = Easy, 1 = Normal, etc.
            difficultyFrame.dispose();
            openBackgroundSelection();
        });

        difficultyFrame.add(difficultyLabel);
        difficultyFrame.add(difficultyBox);
        difficultyFrame.add(selectButton);

        difficultyFrame.setVisible(true);
    }
    
    private void openBackgroundSelection() {
        BackgroundSelectionFrame backgroundFrame = new BackgroundSelectionFrame(playerName, selectedDifficulty);
        backgroundFrame.setVisible(true);
        this.dispose();
    }


    // ✅ Get Player Name (to use in Game Over screen)
    public static String getPlayerName() {
        return playerName;
    }
    
    // ✅ Get Difficulty Level
    public static int getSelectedDifficulty() {
        return selectedDifficulty;
    }
}
