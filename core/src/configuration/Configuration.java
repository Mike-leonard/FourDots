package configuration;

/**
 * Created by ManuGil on 09/03/15.
 */

public class Configuration {

    public static final String GAME_NAME = "Four Dots";

    public static boolean DEBUG = false;
    public static final boolean SPLASHSCREEN = true;

    //ADMOB IDS
    public static final String AD_UNIT_ID_BANNER = "ca-app-pub-2957577039807154/2581519826";
    public static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2957577039807154/4497236720";
    public static float AD_FREQUENCY = .9f;

    //In App Purchases
    public static final boolean IAP_ON = true;
 /*   public static final String ENCODED_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhE0PVGAvJr1pP4s2DEDKghoa7Ded9QGxPjkiRE5xaGMO3Wuk+bLsWgiZh0pkmcQOzxJ2K8zGj/3mvK/xvWs7l5e68o3NQpHqXo+A5pc4V/rrcxjJcqy2ydisYU92zJIm6kqBD0oVidVl9iyz95fnErVncd+dVbYG0pgebSyMl2uUlcc8PZ/98Hc6WtIa7iaj4nqnrq2LRHKnk7Aq6d7nRHpK6nE46KN83Y6kQjmpzR2iVewCKh/BQqSzikb1BfRqnh6rgRdCVRhTsaE6+DC3DKnWCKJ+2yZJ7d1nHn0/qdGioASMeUJaZLnTFVAT48JNvorX2omaAA2S4DvUBT4kDQIDAQAB";
    public static final String PRODUCT_ID = "removeads";*/
 public static final String ENCODED_PUBLIC_KEY = "MIIBI";
    public static final String PRODUCT_ID = "removeads";

    //LEADERBOARDS
    public static final String LEADERBOARD_HIGHSCORE = "CggIiNPRvm8QAhAB";
    public static final String LEADERBOARD_GAMESPLAYED = "CggIiNPRvm8QAhAC";

    //ACHIEVEMENTS IDS Points
    public static final String ACHIEVEMENT_5_P = "CggIiNPRvm8QAhAD";
    public static final String ACHIEVEMENT_10_P = "CggIiNPRvm8QAhAE";
    public static final String ACHIEVEMENT_25_P = "CggIiNPRvm8QAhAF";
    public static final String ACHIEVEMENT_50_P = "CggIiNPRvm8QAhAG";
    public static final String ACHIEVEMENT_100_P = "CggIiNPRvm8QAhAH";
    public static final String ACHIEVEMENT_200_P = "CggIiNPRvm8QAhAI";
    //GAMES PLAYED
    public static final String ACHIEVEMENT_10_GP = "CggIiNPRvm8QAhAJ";
    public static final String ACHIEVEMENT_25_GP = "CggIiNPRvm8QAhAK";
    public static final String ACHIEVEMENT_50_GP = "CggIiNPRvm8QAhAL";
    public static final String ACHIEVEMENT_100_GP = "CggIiNPRvm8QAhAM";
    public static final String ACHIEVEMENT_200_GP = "CggIiNPRvm8QAhAN";

    //COLORS
    public static final String COLOR_BACKGROUND_COLOR = "#ecf0f1";


    //TEXTs
    public static final String SCORE_TEXT = "Score: ";
    public static final String BEST_TEXT = "Best: ";
    public static final String HIGH_SCORE_TEXT = "High Score: ";
    public static final String SCORE_TEXT_MENU = "Score: ";
    public static final String GAMES_PLAYED_TEXT = "Games Played: ";

    //Share Message
    public static final String SHARE_MESSAGE = "Can you beat my High Score at " + GAME_NAME + "? #FourDots";

}
