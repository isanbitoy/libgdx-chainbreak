package com.chainbreak.game.enums;


public enum TrapBarrelType
{
    RED_BARREL("redBarrel"),
    GREEN_BARREL("greenBarrel"),
    YELLOW_BARREL("yellowBarrel");

    private String assetID;

    TrapBarrelType(String assetID)
    {
        this.assetID = assetID;
    }

    public String getAssetID()
    {
        return assetID;
    }

}
