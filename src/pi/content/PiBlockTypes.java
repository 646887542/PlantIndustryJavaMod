package pi.content;

import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import pi.PlantIndustry;
import pi.classes.blocks.InterstellarLaunchPad;

public class PiBlockTypes {
    public static Block BaseCore, InterstellarLaunchPad;

    public static void load() {
        BaseCore = Vars.content.block(PlantIndustry.name("基地"));
        InterstellarLaunchPad = new InterstellarLaunchPad("星际发射台") {{
            consumesPower = true;
            consumePower(30f);
            consumeLiquid(Liquids.cryofluid, 5f);
            requirements(Category.effect, ItemStack.with(Items.copper, 500, Items.lead, 600));
            itemCapacity = 60;
            launchTime = 3600f;
            size = 3;
            liquidCapacity = 600f;
        }};
    }
}
