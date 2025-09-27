package pi.content;

import arc.struct.Seq;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.*;
import static mindustry.content.Blocks.*;
import static pi.content.PiBlockTypes.*;
import static pi.content.PiSectors.*;

public class PiTechTree {
    public static void Load() {
        PiPlanets.Omega.techTree = nodeRoot("Omega", coreShard, true, ()->{
            //core
            node(BaseCore, ()->{});
            //LaunchPad
            node(InterstellarLaunchPad, ()->{});
            //turret
            node(Gypsophila, Seq.with(new Objectives.SectorComplete(BaseArea), new Objectives.Research(BaseCore)), ()->{});
            //sector
            node(BaseArea, ()->{});
        });
    }
}