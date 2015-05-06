package nik.mobil.gameofballs;

/**
 * Created by Gábor on 2015.05.02..
 */
enum Type{
    HEAVY,NORMAL,LIGHT
}

public class Ball {
    private float posX;
    private float posY;
    private float size;
    private Type type;

    public Type getType() {
        return type;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getSize() {
        return size;
    }



    public Ball(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.size=30;
        type=Type.NORMAL;
    }
    public void Move(float posX,float posY)
    {
        this.posX+=posX;
        this.posY+=posY;
    }

    public void Change(Type type)
    {
        this.type=type;
    }

}
