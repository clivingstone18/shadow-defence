public class superTank extends Tank {
    int effectRadius = 150;
    int damage = super.getEffectRadius()*3;
    int cooldown = 500;
    int cost = 600;
    String projectileImage = "res/images/supertank_projectile.png";
    String image = "res/images/supertank.png";
}