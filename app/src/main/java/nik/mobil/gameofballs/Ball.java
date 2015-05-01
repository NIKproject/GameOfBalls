package nik.mobil.gameofballs;

/**
 * Created by Gábor on 2015.05.02..
 */

public class Ball {
    private int posX;
    private int posY;
    private Type type;

    public enum Type{
        HEAVY,NORMAL,LIGHT
    }

    public Ball(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        type=Type.NORMAL;
    }


}
