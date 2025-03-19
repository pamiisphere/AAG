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

public class MainApplication extends JFrame implements MyConstants {
    
    private MySoundEffect themeMusic;

    public MainApplication() {
        setTitle("Game Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAMEWIDTH, FRAMEHEIGHT);
        setLocationRelativeTo(null);

        JLabel background = new JLabel(new MyImageIcon(FILE_MENU_BG).resize(FRAMEWIDTH, FRAMEHEIGHT));
        background.setLayout(new GridBagLayout());
        add(background);
        
         // Play background music clearly
        themeMusic = new MySoundEffect(FILE_THEME);
        themeMusic.playLoop();
        themeMusic.setVolume(0.5f); // adjust volume as needed (0.0f to 1.0f)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        
        // Game title clearly at the top
        JLabel titleLabel = new JLabel("Angry Ajarn Game");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 45));
        titleLabel.setForeground(Color.BLACK);
        
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(0, 0, 20, 0); // Clearly add extra bottom margin to push buttons down
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.anchor = GridBagConstraints.NORTH; // clearly anchor to the top
        gbc.gridy = 0;
        background.add(titleLabel, gbcTitle);
        
        // Buttons setup
        String[] buttons = {"PLAY", "SETTINGS", "CREDITS", "EXIT"};
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 0, 10, 0);
        gbcButtons.gridx = 0;
        
        // Sound effect for button click
        MySoundEffect clickSound = new MySoundEffect(FILE_SFX_CLICK);


        for (int i = 0; i < buttons.length; i++) {
            JButton btn = new JButton(buttons[i]);
            btn.setPreferredSize(new Dimension(250, 50));
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setFocusPainted(false);

            gbcButtons.gridy = i + 1;  // clearly positioned after title (row 1 onwards)
            background.add(btn, gbcButtons);

            btn.addActionListener(e -> {
                clickSound.playOnce(); // Play click sound on every click
                
                String command = e.getActionCommand();
                if (command.equals("PLAY")) {
                    JFrame gameFrame = new JFrame("Angry Ajarn Game");
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gameFrame.setContentPane(new MainGame());
                    gameFrame.pack();
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.setVisible(true);
                    themeMusic.stop(); // stop menu music when game starts
                    this.dispose(); // close menu if you want clearly
                } else if (command.equals("SETTINGS")) {
                    JFrame optionsFrame = new JFrame("Game Settings");
                    optionsFrame.setSize(600, 500);
                    optionsFrame.setLocationRelativeTo(null);
                    optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel panel = new JPanel(new GridBagLayout());
                    //GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.fill = GridBagConstraints.HORIZONTAL;

                    // JTextField (Player name)
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    panel.add(new JLabel("Player Name:"), gbc);
                    JTextField playerName = new JTextField(20);
                    gbc.gridx = 1;
                    panel.add(playerName, gbc);

                    // JPasswordField (Enter Password)
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    panel.add(new JLabel("Enter Password:"), gbc);
                    JPasswordField passwordField = new JPasswordField(20);
                    gbc.gridx = 1;
                    panel.add(passwordField, gbc);

                    // JTextArea (Player notes)
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    panel.add(new JLabel("Player Notes:"), gbc);
                    JTextArea notesArea = new JTextArea(4, 20);
                    gbc.gridx = 1;
                    panel.add(new JScrollPane(notesArea), gbc);

                    // JCheckBox (5 Game Options)
                    gbc.gridx = 0;
                    gbc.gridy = 3;
                    panel.add(new JLabel("Enable Features:"), gbc);
                    JPanel featuresPanel = new JPanel();
                    JCheckBox[] featuresCheck = {
                        new JCheckBox("Music"),
                        new JCheckBox("Sound Effects"),
                        new JCheckBox("Notifications"),
                        new JCheckBox("Auto-Save"),
                        new JCheckBox("Subtitles")
                    };
                    for (JCheckBox cb : featuresCheck) featuresPanel.add(cb);
                    gbc.gridx = 1;
                    panel.add(featuresPanel, gbc);

                    // JRadioButton (Location Selection)
                    gbc.gridx = 0;
                    gbc.gridy = 4;
                    panel.add(new JLabel("Location:"), gbc);
                    JPanel locationPanel = new JPanel();
                    ButtonGroup locationGroup = new ButtonGroup();
                    JRadioButton[] locationButtons = {
                        new JRadioButton("Venus"),
                        new JRadioButton("Jupiter"),
                        new JRadioButton("Uranus"),
                        new JRadioButton("Mars"),
                        new JRadioButton("Saturn")
                    };
                    for (JRadioButton rb : locationButtons) {
                        locationGroup.add(rb);
                        locationPanel.add(rb);

                        // ActionListener to change background clearly
                        rb.addActionListener(ev -> {
                            String selected = ev.getActionCommand();
                            String bgFile = FILE_BG_VENUS; // default background
                            switch (selected) {
                                case "Venus" -> bgFile = PATH + "Venus.jpg";
                                case "Jupiter" -> bgFile = PATH + "Jupiter.jpg";
                                case "Uranus" -> bgFile = PATH + "Uranus.jpg";
                                case "Mars" -> bgFile = PATH + "Mars.jpg";
                                case "Saturn" -> bgFile = PATH + "Saturn.jpg";
                            }
                            MyImageIcon backgroundImg = new MyImageIcon(bgFile).resize(FRAMEWIDTH, FRAMEHEIGHT);
                            repaint(); // clearly repaint to update background immediately
                        });
                    }
                    gbc.gridx = 1;
                    panel.add(locationPanel, gbc);
                    
                    
                    // JComboBox (Game Difficulty)
                    gbc.gridx = 0;
                    gbc.gridy = 5;
                    panel.add(new JLabel("Game Difficulty:"), gbc);
                    JComboBox<String> difficultyBox = new JComboBox<>(new String[]{
                        "Easy", "Normal", "Hard", "Expert", "Nightmare"
                    });
                    gbc.gridx = 1;
                    panel.add(difficultyBox, gbc);


                    // JList (Preferred Themes)
                    gbc.gridx = 0;
                    gbc.gridy = 6;
                    panel.add(new JLabel("Select Theme:"), gbc);
                    JList<String> themeList = new JList<>(new String[]{
                        "Dark", "Light", "Classic", "Modern", "Retro"
                    });
                    themeList.setVisibleRowCount(3);
                    gbc.gridx = 1;
                    panel.add(new JScrollPane(themeList), gbc);
                    
                    // Save Button to Apply Settings
                    JButton saveButton = new JButton("Save");
                    gbc.gridx = 1;
                    gbc.gridy = 7;
                    panel.add(saveButton, gbc);

                    optionsFrame.add(panel);
                    optionsFrame.setVisible(true);
                    
                            
                }else if (command.equals("CREDITS")) {
                    JFrame creditsFrame = new JFrame("Credits");
                    creditsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    creditsFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
                    creditsFrame.setLocationRelativeTo(null);

                    JLabel creditsBackground = new JLabel(new MyImageIcon(FILE_CREDITS_BG).resize(FRAMEWIDTH, FRAMEHEIGHT));
                    creditsBackground.setLayout(new GridBagLayout());
                    creditsFrame.add(creditsBackground);

                    //GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(10, 10, 10, 10);
                    gbc.gridx = 0;

                    JLabel creditsTitle = new JLabel("Credits");
                    creditsTitle.setFont(new Font("Serif", Font.BOLD, 40));
                    creditsTitle.setForeground(Color.BLACK);
                    gbc.gridy = 0;
                    creditsBackground.add(creditsTitle, gbc);

                    JButton teamButton = new JButton("View Team Members");
                    teamButton.setFont(new Font("Arial", Font.PLAIN, 15));
                    gbc.gridy = 1;
                    creditsBackground.add(teamButton, gbc);

                    teamButton.addActionListener(ev -> {
                        JOptionPane.showMessageDialog(creditsFrame,
                            "Sukumarn Srimai - 6581085\n" +
                            "Rueti Limpacharoenkul - 6581166\n" +
                            "Chantapat Tivalai - 6581193\n" +
                            "Thanarat Bunbangyang - 6581195\n" +
                            "Banthawan Udomsap - 6581204",
                            "Team Members",
                            JOptionPane.INFORMATION_MESSAGE);
                    });

                    creditsFrame.setVisible(true);
                }else if (command.equals("EXIT")) {
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(this, command + " clicked");
                }
            });
        }
        setVisible(true);
    }

    public static void main(String[] args) {
        MainApplication MainApplication = new MainApplication();
    }
}

