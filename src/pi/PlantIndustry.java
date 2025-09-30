package pi;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import pi.classes.entities.EntityRegister;
import pi.content.*;

public class PlantIndustry extends Mod{

    public static final String MOD_NAME = "plantindustry";//
    public static String name(String name){
        return MOD_NAME + "-" + name;
    }
    public PlantIndustry() {
        Events.on(ClientLoadEvent.class, e -> {
            PiItemTypes.loadItem();
            PiBlockTypes.loadBlock();
            Log.infoTag("Debug", String.valueOf(PiBlockTypes.BaseCore));
            Log.infoTag("Debug", String.valueOf(PiItemTypes.Crystal));
        });
    }

    @Override
    public void loadContent() {
        EntityRegister.load();
        PiUnitTypes.load();
        PiBlockTypes.load();
        PiStatuses.load();
        PiPlanets.load();
        PiSectors.load();
        PiTechTree.Load();
    }
}
