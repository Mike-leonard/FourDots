package configuration;

/**
 * Created by ManuGil on 23/04/15.
 */

public class Settings {

    //CORE
    public static final int CORE_SIZE = 200;
    public static final int CORE_Y_VALUE = 300; //Distance from the floor ^
    public static final float ROTATION_DURATION = .15f;

    //DOTS
    public static final float DOTS_SIZE = 110;
    public static final float DISTANCE_CENTER_TO_DOT = 16;
    public static final int NUM_OF_DOTS = 4; //Up to 6 dots

    //FALLING BALLS
    public static final int BALL_SIZE = 50;
    public static final float INITAL_BALL_SPEED = -700;
    public static final float BALL_ACCELERATION = -20; //Negative because it goes down
    public static final float BALL_MAX_SPEED = -1400;
    public static final boolean UP_AND_DOWN_SPAWNS = false;

    //HUD
    public static final float HUD_ALPHA = .4f;
    public static final boolean FULL_HUD = true; //When true shows Score: and Best:, when false only points at Center
    public static final float HUD_SIZE = 110;
    public static final float HUD_TEXT_Y_DISTANCE = 18;

    //MENU
    public static final float PLAY_BUTTON_SIZE = 170;
    public static final float BUTTON_SIZE = 150;
    public static final boolean REMOVE_CIRCLE_BUTTONS = false; //Set to true if you have textures in buttons.png
    public static final float MENU_BACK_CIRCLE_SIZE = 1700; //Set to 0 if you dont want it
    public static final boolean SHOW_BACK_CIRCLE = false;
    public static final float MUSIC_VOLUME = .8f;


    public static final boolean USE_TEXTURES = false;
    public static final boolean USE_TITLE_TEXTURE = true;

    ////////COLORS/////////
    //DOTS //Recommended setting this to white (#FFF) when USE_TEXTURES = TRUE;
    public static final String DOT_1_COLOR = "#e74c3c";
    public static final String DOT_2_COLOR = "#2ecc71";
    public static final String DOT_3_COLOR = "#3498db";
    public static final String DOT_4_COLOR = "#f39c12";
    public static final String DOT_5_COLOR = "#9b59b6";
    public static final String DOT_6_COLOR = "#e67e22";

    ////////COLORS/////////
    //TEXTS
    public static final String TITLE_TEXT_COLOR = "#34495e";
    public static final String GAMEPLAY_TEXT_MENU_COLOR = "#34495e";
    public static final String SCORE_TEXT_MENU_COLOR = "#ecf0f1";
    public static final String HIGHSCORE_TEXT_MENU_COLOR = "#ecf0f1";

    ////////COLORS/////////
    //MENU
    public static final String BACK_RECTANGLE_MENU_COLOR = "#2c3e50";

    ////////COLORS/////////
    //MENU
    public static final String BEST_HUD_COLOR = "#34495e";
    public static final String SCORE_HUD_COLOR = "#34495e";
    public static final String BACK_RECTANGLE_HUD_COLOR = "#bdc3c7";
    public static final String SOUND_BUTTON_COLOR = "#34495e";

    ////////COLORS/////////
    //BUTTONS
    public static final String PLAY_BUTTON_COLOR = "#2ecc71";
    public static final String RANK_BUTTON_COLOR = "#e74c3c";
    public static final String ACHIEVEMENT_BUTTON_COLOR = "#f39c12";
    public static final String SHARE_BUTTON_COLOR = "#4aa3df";
    public static final String ADS_BUTTON_COLOR = "#9b59b6";

}
