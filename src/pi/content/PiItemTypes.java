package pi.content;

import mindustry.type.Item;

public class PiItemTypes {
    public static Item LuminousBerry, Biomass, IceCrystal, LavaBerry, LightningReed, CoffeeBean, Blueberries, Crystal;

    public static void load() {
        // 请勿模仿
        LuminousBerry = new Item("LuminousBerry");//发光浆果
        Biomass = new Item("Biomass");//生物质
        IceCrystal = new Item("IceCrystal");//冰晶
        LavaBerry = new Item("LavaBerry");//熔岩浆果
        LightningReed = new Item("LightningReed");//闪电芦苇
        CoffeeBean = new Item("CoffeeBean");//咖啡豆
        Blueberries = new Item("Blueberries");//蓝莓
        Crystal = new Item("水晶");
    }
}