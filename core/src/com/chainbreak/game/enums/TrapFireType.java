package com.chainbreak.game.enums;

public enum TrapFireType
{
    RED("redFlame"),
    BLUE("blueFlame"),
    PURPLE("purpleFlame");

    private String assetID;

    TrapFireType(String assetID)
    {
        this.assetID = assetID;
    }

    public String getAssetID()
    {
        return assetID;
    }
}
