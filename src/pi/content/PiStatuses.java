package pi.content;

import arc.graphics.Color;
import mindustry.type.StatusEffect;

public class PiStatuses {
    public static StatusEffect Mismatch;

    public static void load() {
        Mismatch = new StatusEffect("失谐") {{
            color = Color.valueOf("46d1fa");
            speedMultiplier = 0.5f;
            reloadMultiplier = 0.5f;
            damage = 0.5f;
        }};
    }
}
