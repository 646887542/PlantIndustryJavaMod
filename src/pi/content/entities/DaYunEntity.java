package pi.content.entities;

import mindustry.content.UnitTypes;
import mindustry.gen.TankUnit;
import pi.classes.entities.EntityRegister;
import pi.classes.entities.MagicUnitEntity;
import pi.content.PiUnitTypes;

public class DaYunEntity extends TankUnit {
    public MagicUnitEntity<DaYunEntity> Mu = new MagicUnitEntity<>(){{
        this.limitDamage = 10000f;
        this.hitByLimitDamage = true;
        this.callHealthf = 0.5f;
        this.hitOverTotalDamage = true;
        this.totalLimitDamage = 30000f;
    }};
    public DaYunEntity() {
        super();
    }

    @Override
    public void update() {
        super.update();
        if (Mu != null) {
            Mu.update(this);
            Mu.callUnit(this, UnitTypes.oct, false, 1);
            Mu.callUnit(this, PiUnitTypes.RoadRoller, false, 0, 100);
            Mu.callUnit(this, PiUnitTypes.RoadRoller, true, 0, -100);
        }
    }

    @Override
    public void rawDamage(float amount) {
        if (Mu != null) {
            super.rawDamage(Mu.Damage(amount));
        }
    }

    @Override
    public int classId() {
        return EntityRegister.getID(getClass());
    }
}
