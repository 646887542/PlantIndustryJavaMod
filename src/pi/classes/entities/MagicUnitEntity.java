package pi.classes.entities;

//import arc.util.Log;
import arc.util.Time;
import mindustry.gen.Unit;
import mindustry.type.UnitType;

public class MagicUnitEntity<unit extends Unit> {
    private float coolant = 0;
    public float limitDamage = -1f;
    public float totalLimitDamage = -1f;
    public float totalDamage;
    public float callHealthf = 0.5f;
    public float sec;
    public float coolantTime = 180f;
    public boolean hitByLimitDamage = false; //单次攻击限伤
    public boolean hitOverTotalDamage = false; //每秒攻击限伤
    public boolean canCallUnit = false;

    public void callUnit(unit u, UnitType ut, boolean recode, int amount) {
        for (int i = 0; i < amount; i++) {
            this.callUnit(u, ut, recode, 0, 0);
        }
    }

    public void callUnit(unit u, UnitType ut, boolean recode, float fx, float fy) {
        if (this.canCallUnit && ut != null && u.healthf() < callHealthf && this.coolant <= 0) {
            ut.spawn(u.team, u.x + fx, u.y + fy);
            if (recode) {
                this.coolant = this.coolantTime;
                this.canCallUnit = false;
            }
        }
    }

    public void update(unit u) {
        if (this.hitOverTotalDamage && this.totalLimitDamage != -1f) {
            this.sec += Time.delta;

            if (this.coolant >= 0) {
                this.coolant -= Time.delta;
            }

            if (this.sec >= Time.toSeconds / 12) {
                //Log.infoTag(String.valueOf(u.healthf()), String.valueOf(this.canCallUnit)/*u+"totalDamage:"+ this.totalDamage*/);
                this.sec = 0;
                this.totalDamage = 0;
            }
        }

        if (u.healthf() >= callHealthf) {
            this.canCallUnit = true;
        }
    }

    public float Damage(float amount) {
        if (limitDamage != -1f && hitByLimitDamage) {
            if (amount > limitDamage) {
                amount = 0f;
            }
        } else {
            amount = Math.min(amount, limitDamage);
        }

        if (this.hitOverTotalDamage && this.totalLimitDamage != -1f) {
            if (totalLimitDamage / 12 >= this.totalDamage + amount) {
                this.totalDamage += amount;
            } else {
                this.totalDamage = this.totalLimitDamage;
                amount = this.totalLimitDamage - this.totalDamage;
            }
        }

        return amount;
    }
}
