import Bagel.Image;

public class buyPanel {
    List<purchaseItem> purchaseItemList = new ArrayList<purchaseItem>;
    Image background = new Image("res/images/buypanel.png");


    public render(int funds) {
        Image.draw(background);
        for (purchaseItem purchaseItem: purchaseItemList) {
            purchaseItem.drawItem();



        }














    }







}