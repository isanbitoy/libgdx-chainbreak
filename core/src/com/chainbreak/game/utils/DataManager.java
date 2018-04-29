package com.chainbreak.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class DataManager
{
    private static final String KEY_PREFERENCES = "ChainBreak_Preferences";

    private static DataManager instance = null;

    private static boolean isUnlockLvl[] = new boolean[24];

    private DataManager()
    {
        super();
    }

    public static DataManager getInstance()
    {
        if (instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    public void load()
    {
        Preferences preferences = Gdx.app.getPreferences(KEY_PREFERENCES);

        isUnlockLvl[2] = preferences.getBoolean("level2");
        isUnlockLvl[3] = preferences.getBoolean("level3");
        isUnlockLvl[4] = preferences.getBoolean("level4");
        isUnlockLvl[5] = preferences.getBoolean("level5");
        isUnlockLvl[6] = preferences.getBoolean("level6");
        isUnlockLvl[7] = preferences.getBoolean("level7");
        isUnlockLvl[8] = preferences.getBoolean("level8");
        isUnlockLvl[9] = preferences.getBoolean("level9");
        isUnlockLvl[10] = preferences.getBoolean("level10");
        isUnlockLvl[11] = preferences.getBoolean("level11");
        isUnlockLvl[12] = preferences.getBoolean("level12");
        isUnlockLvl[13] = preferences.getBoolean("level13");
        isUnlockLvl[14] = preferences.getBoolean("level14");
        isUnlockLvl[15] = preferences.getBoolean("level15");
        isUnlockLvl[16] = preferences.getBoolean("level16");
        isUnlockLvl[17] = preferences.getBoolean("level17");
        isUnlockLvl[18] = preferences.getBoolean("level18");
        isUnlockLvl[19] = preferences.getBoolean("level19");
        isUnlockLvl[20] = preferences.getBoolean("level20");
    }

    public void save(int level, boolean unlock)
    {
        Preferences preferences = Gdx.app.getPreferences(KEY_PREFERENCES);

        switch(level)
        {
            case 2:
                preferences.putBoolean("level2", unlock);
                break;
            case 3:
                preferences.putBoolean("level3", unlock);
                break;
            case 4:
                preferences.putBoolean("level4", unlock);
                break;
            case 5:
                preferences.putBoolean("level5", unlock);
                break;
            case 6:
                preferences.putBoolean("level6", unlock);
                break;
            case 7:
                preferences.putBoolean("level7", unlock);
                break;
            case 8:
                preferences.putBoolean("level8", unlock);
                break;
            case 9:
                preferences.putBoolean("level9", unlock);
                break;
            case 10:
                preferences.putBoolean("level10", unlock);
                break;
            case 11:
                preferences.putBoolean("level11", unlock);
                break;
            case 12:
                preferences.putBoolean("level12", unlock);
                break;
            case 13:
                preferences.putBoolean("level13", unlock);
                break;
            case 14:
                preferences.putBoolean("level14", unlock);
                break;
            case 15:
                preferences.putBoolean("level15", unlock);
                break;
            case 16:
                preferences.putBoolean("level16", unlock);
                break;
            case 17:
                preferences.putBoolean("level17", unlock);
                break;
            case 18:
                preferences.putBoolean("level18", unlock);
                break;
            case 19:
                preferences.putBoolean("level19", unlock);
                break;
            case 20:
                preferences.putBoolean("level20", unlock);
                break;
        }

        preferences.flush();
    }

    public boolean getUnlockLvl2()
    {
        return isUnlockLvl[2];
    }
    public boolean getUnlockLvl3()
    {
        return isUnlockLvl[3];
    }
    public boolean getUnlockLvl4()
    {
        return isUnlockLvl[4];
    }
    public boolean getUnlockLvl5()
    {
        return isUnlockLvl[5];
    }
    public boolean getUnlockLvl6()
    {
        return isUnlockLvl[6];
    }
    public boolean getUnlockLvl7()
    {
        return isUnlockLvl[7];
    }
    public boolean getUnlockLvl8()
    {
        return isUnlockLvl[8];
    }
    public boolean getUnlockLvl9()
    {
        return isUnlockLvl[9];
    }
    public boolean getUnlockLvl10()
    {
        return isUnlockLvl[10];
    }
    public boolean getUnlockLvl11()
    {
        return isUnlockLvl[11];
    }
    public boolean getUnlockLvl12()
    {
        return isUnlockLvl[12];
    }
    public boolean getUnlockLvl13()
    {
        return isUnlockLvl[13];
    }
    public boolean getUnlockLvl14()
    {
        return isUnlockLvl[14];
    }
    public boolean getUnlockLvl15()
    {
        return isUnlockLvl[15];
    }
    public boolean getUnlockLvl16()
    {
        return isUnlockLvl[16];
    }
    public boolean getUnlockLvl17()
    {
        return isUnlockLvl[17];
    }
    public boolean getUnlockLvl18()
    {
        return isUnlockLvl[18];
    }
    public boolean getUnlockLvl19()
    {
        return isUnlockLvl[19];
    }
    public boolean getUnlockLvl20()
    {
        return isUnlockLvl[20];
    }
}
