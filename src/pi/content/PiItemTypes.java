package pi.content;

import mindustry.Vars;
import mindustry.type.Item;
import pi.PlantIndustry;

public class PiItemTypes {
    public static Item LuminousBerry, Biomass, IceCrystal, LavaBerry, LightningReed, CoffeeBean, Blueberries, Crystal;

    public static void loadItem() {
        LuminousBerry = Vars.content.item(PlantIndustry.name("LuminousBerry"));//发光浆果
        Biomass = Vars.content.item(PlantIndustry.name("Biomass"));//生物质
        IceCrystal = Vars.content.item(PlantIndustry.name("IceCrystal"));//冰晶
        LavaBerry = Vars.content.item(PlantIndustry.name("LavaBerry"));//熔岩浆果
        LightningReed = Vars.content.item(PlantIndustry.name("LightningReed"));//闪电芦苇
        CoffeeBean = Vars.content.item(PlantIndustry.name("CoffeeBean"));//咖啡豆
        Blueberries = Vars.content.item(PlantIndustry.name("Blueberries"));//蓝莓
        Crystal = Vars.content.item(PlantIndustry.name("水晶"));
    }
}