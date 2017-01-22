package ru.catstack.texture_resizer.private_engine;

public class EngineConfig {
    public int width = 1280;
    public int height = 720;
    public boolean resizable = true;
    public boolean fullscreen = false;
    public String title = "Catstack JavaFX Engine";
    public boolean maximized = false;
    public boolean alwaysOnTop = false;
    public double maxWidth = Double.MAX_VALUE;
    public double maxHeight = Double.MAX_VALUE;
    public double minWidth = 0;
    public double minHeight = 0;
    public double opacity = 1;
    public double x = -777; //default center x
    public double y = -777; //default center y
}
