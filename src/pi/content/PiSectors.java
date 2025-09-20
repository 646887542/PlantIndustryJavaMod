package pi.content;

import mindustry.type.SectorPreset;

public class PiSectors {
    public static SectorPreset BaseArea;

    public static void load() {
        BaseArea = new SectorPreset("BaseArea", PiPlanets.Omega, 0) {{
            this.alwaysUnlocked = true;
        }};
    }
}
