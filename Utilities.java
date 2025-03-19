package Project3_6581204;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.sound.sampled.*;     // for sounds


// Interface for keeping constant values
interface MyConstants
{
    //----- Resource files
    static final String PATH               = "src/main/java/Project3_6581204/resources/";
    static final String FILE_MENU_BG       = PATH + "main_bg.jpg";
    static final String FILE_CREDITS_BG    = PATH + "main_credits.png";
    static final String FILE_BG_VENUS      = PATH + "Venus.jpg";
    static final String FILE_BG_JUPITER    = PATH + "Jupiter.jpg";
    static final String FILE_BG_URANUS     = PATH + "Uranus.jpg";
    static final String FILE_BG_MARS       = PATH + "Mars3.jpg";
    static final String FILE_BG_SATURN     = PATH + "Saturn.jpg";

    // Right character with leg animation frames (Change to .gif later)
    static final String FILE_CHARACTER_MOVE  = PATH + "character_move.gif"; 
    static final String FILE_CHARACTER_HIT   = PATH + "character_hit.gif"; 

    
    static final String FILE_ENEMY         = PATH + "alien2.gif";
    static final String FILE_HEART         = PATH + "Student.gif";    
    
    static final String FILE_THEME         = PATH + "sound_bg.wav";
    static final String FILE_SFX_CLICK     = PATH + "sound_click.wav";
    static final String FILE_SFX_GOOD      = PATH + "sound_point.wav";
    static final String FILE_SFX_BAD       = PATH + "sound_lose.wav";
    static final String FILE_GAMEOVER      = PATH + "sound_GameOver.wav";
    
    //----- Sizes and locations
    static final int FRAMEWIDTH    = 1200;
    static final int FRAMEHEIGHT   = 550;
    static final int FLOOR_UP      = 170;
    static final int FLOOR_DOWN    = 440;
    
    static final int CHARACTERWIDTH   = 140;
    static final int CHARACTERHEIGHT  = 180;
    
    static final int ITEMWIDTH     = 60;
    static final int ITEMHEIGHT    = 60;
    
    //----- Game settings
    static int BG_SCROLL_SPEED = 3;  // Background scrolling speed
}


// Auxiliary class to resize image
class MyImageIcon extends ImageIcon
{
    public MyImageIcon(String fname)  { super(fname); }
    public MyImageIcon(Image image)   { super(image); }

    public MyImageIcon resize(int width, int height)
    {
	Image oldimg = this.getImage();
	Image newimg = oldimg.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        return new MyImageIcon(newimg);
    }
}


// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;         

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }

    void play() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
