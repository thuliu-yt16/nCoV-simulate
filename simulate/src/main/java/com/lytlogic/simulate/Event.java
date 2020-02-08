package com.lytlogic.simulate;

public interface Event {
    public void act();

    public Point getLocation();

    public int getTime();
}
