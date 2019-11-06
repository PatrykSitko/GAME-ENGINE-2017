package game.gfx.background;

public enum Backgrounds {
    SKY("/backgrounds/sky.png", true),
    STARFIELD("/backgrounds/starfield.png", true),
    DESERT("/backgrounds/desert.png", true),
    SWAMP("/backgrounds/swamp.png", true),
    CLOUD1("/backgrounds/clouds/cloud1.png", false),
    CLOUD2("/backgrounds/clouds/cloud2.png", false),
    CLOUD3("/backgrounds/clouds/cloud3.png", false),
    CLOUD4("/backgrounds/clouds/cloud4.png", false),
    CLOUD5("/backgrounds/clouds/cloud5.png", false),
    CLOUD6("/backgrounds/clouds/cloud6.png", false),
    CLOUD7("/backgrounds/clouds/cloud7.png", false),
    CLOUD8("/backgrounds/clouds/cloud8.png", false),
    CLOUD9("/backgrounds/clouds/cloud9.png", false),
    CLOUD10("/backgrounds/clouds/cloud10.png", false),
    CLOUD11("/backgrounds/clouds/cloud11.png", false),
    CLOUD12("/backgrounds/clouds/cloud12.png", false),
    FOREST_LAYER_1("/backgrounds/parallax/forest/layer_01.png", true),
    FOREST_LAYER_2("/backgrounds/parallax/forest/layer_02.png", true),
    FOREST_LAYER_3("/backgrounds/parallax/forest/layer_03.png", true),
    FOREST_LAYER_4("/backgrounds/parallax/forest/layer_04.png", true),
    FOREST_LAYER_5("/backgrounds/parallax/forest/layer_05.png", true),
    DUNGEON("/levels/dungeon1.png", false);

    private Background background;

    Backgrounds(String path, boolean fitGameWindow) {
        this.background = new Background(path, fitGameWindow);
    }

    Backgrounds(String path, int x, int y, int width, int height) {
        this.background = new Background(path, x, y, width, height);
    }

    public Background get() {
        return new Background(background);
    }
}
