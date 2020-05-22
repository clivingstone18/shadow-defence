import bagel.util.Image;

public abstract Tower extends Image {
    private int effectRadius;
    private int damage;
    private int cooldown;
    private Image projectileImage;
    private Image tankImage;

    public getTankImage(){
        return tankImage;
    }

}