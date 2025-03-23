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
        
        // ✅ Load the logo image with transparency
        JLabel logoLabel = new JLabel(new MyImageIcon(FILE_LOGO).resize(400, 200)); // Adjust size as needed
        logoLabel.setOpaque(false); // ✅ Ensure transparency
        logoLabel.setBackground(new Color(0, 0, 0, 0)); // Fully transparent background

        // ✅ GridBagConstraints for the logo
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.insets = new Insets(0, 0, 20, 0); // Extra bottom margin
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.anchor = GridBagConstraints.NORTH; // Anchor at the top

        // ✅ Add only the logo to the background (removing the text title)
        background.add(logoLabel, gbcTitle);

        // Buttons setup
        String[] buttons = {"PLAY", "HOW TO PLAY", "CREDITS", "EXIT"};
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(0, 0, 20, 0);
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
                switch (command) {
                    case "PLAY":
                        JFrame nameFrame = new NameFrame(this, themeMusic);
                        nameFrame.setVisible(true);
                        themeMusic.stop();
                        this.dispose(); // Close the menu screen
                        break;
                // Add picture of HOW TO PLAY
                    case "HOW TO PLAY":
                        // ✅ Create a new JFrame for the How to Play screen
                        JFrame howToPlayFrame = new JFrame("How to Play");
                        howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        howToPlayFrame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
                        howToPlayFrame.setLocationRelativeTo(null);

                        // ✅ Load the "How to Play" image
                        JLabel howToPlayLabel = new JLabel(new MyImageIcon(FILE_HOW_TO_PLAY).resize(FRAMEWIDTH, FRAMEHEIGHT));

                        // ✅ Add the image to the frame
                        howToPlayFrame.add(howToPlayLabel);

                        // ✅ Display the frame
                        howToPlayFrame.setVisible(true);
                        break;
                    case "CREDITS":
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
                        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
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
                            JFrame teamFrame = new JFrame("Member List");
                            teamFrame.setSize(400, 300);
                            teamFrame.setLocationRelativeTo(null);
                            teamFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            
                            // ✅ Create a JList with team members
                            String[] teamMembers = {
                                "Sukumarn Srimai - 6581085",
                                "Rueti Limpacharoenkul - 6581166",
                                "Chantapat Tivalai - 6581193",
                                "Thanarat Bunbangyang - 6581195",
                                "Banthawan Udomsap - 6581204"
                            };
                            
                            JList<String> teamList = new JList<>(teamMembers);
                            teamList.setFont(new Font("Arial", Font.PLAIN, 16));
                            teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                            
                            // ✅ Add JList to a scroll pane
                            JScrollPane scrollPane = new JScrollPane(teamList);
                            //scrollPane.setBorder(BorderFactory.createTitledBorder("Team Members"));
                            
                            teamFrame.add(scrollPane);
                            teamFrame.setVisible(true);
                        }); creditsFrame.setVisible(true);
                        break;
                    case "EXIT":
                        System.exit(0);
                    default:
                        JOptionPane.showMessageDialog(this, command + " clicked");
                        break;
                }
            });
        }
        setVisible(true);
    }

    public static void main(String[] args) {
        MainApplication MainApplication = new MainApplication();
    }
}

