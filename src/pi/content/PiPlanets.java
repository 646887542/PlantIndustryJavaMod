package pi.content;

import arc.graphics.Color;
import mindustry.content.Planets;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.type.Planet;
import pi.classes.maps.generator.OmegaPlanetGenerator;

public class PiPlanets {
    public static Planet Omega;

    public static void load() {
        Omega = new Planet("Omega", Planets.sun, 1f, 3){{
            this.generator = new OmegaPlanetGenerator();
            this.meshLoader = () -> new HexMesh(this, 5);
            this.cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("09a7ff").a(0.85F), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("d0f0f9").a(0.75F), 2, 0.42f, 1.2f, 0.45f)
            );
            this.sectorSeed = 5;
            this.alwaysUnlocked = true;
            this.iconColor = Color.valueOf("88c8f8");
            this.atmosphereColor = Color.valueOf("c5f2f6");
            this.landCloudColor = Color.valueOf("00e0d5");
            this.atmosphereRadIn = 0.02F;
            this.atmosphereRadOut = 0.3F;
            this.orbitSpacing = 2f;
            this.lightSrcTo = 0.5f;
            this.lightDstFrom = 0.2f;
            this.startSector = 0;
            this.allowLaunchLoadout = true;
            this.allowLaunchSchematics = true;
            this.clearSectorOnLose = true;
            this.ruleSetter = rules -> rules.coreDestroyClear = false;
        }};
    }
}
