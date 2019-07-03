package view.utils;

import utility.Position;

public class Animation {
    private int direction;
    private int num;
    private Position pos;


    public Animation(int direction,int num) {
        this.direction = direction;
        this.num = num;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void addNum() {
        num++;
        if (num==3) num =0;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public Position getOldPos() {
        return pos;
    }

    public void setOldPos(Position pos) {
        this.pos = pos;

    }
}
