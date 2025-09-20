package pi.content;

import static mindustry.content.TechTree.*;
import static mindustry.content.Blocks.*;
import static pi.content.PiBlockTypes.*;

public class PiTechTree {
    public static void Load() {
        PiPlanets.Omega.techTree = nodeRoot("Omega", coreShard, true, ()->{
            //core
            node(BaseCore, ()->{});
            //LaunchPad
            node(InterstellarLaunchPad, ()->{});
        });
    }
}