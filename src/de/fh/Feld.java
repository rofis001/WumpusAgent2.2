package de.fh;

import de.fh.connection.Vector2;

/**
 * Created by Brian on 07.12.2016.
 */
public class Feld {
    private boolean visited=false;
    private byte trapdanger=0;
    private Vector2 coords;
    private boolean wall=false;

    public Feld(){}
    public Feld(Vector2 c)
    {
        coords=c;
    }
    public boolean IsVisited(){ return visited;}
    public byte GetTrapdanger(){return trapdanger;}
    public Vector2 GetCoords(){return coords;}
    public boolean IsWall(){return wall;}
    public void SetVisited(){visited=true;}
    public void SetTrapdanger(byte p){trapdanger=p;}
    public void SetCoords(int x, int y){coords.set(x,y);}
    public void SetWall(){wall=true;}
}
