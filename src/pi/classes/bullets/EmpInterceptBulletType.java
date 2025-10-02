package pi.classes.bullets;

import mindustry.entities.bullet.EmpBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;

public class EmpInterceptBulletType extends EmpBulletType {

    public EmpInterceptBulletType() {
        super();
        this.collidesTiles = true;
    }

    @Override
    public void updateHoming(Bullet b){
        var target = Groups.bullet.intersect(b.x - range, b.y - range, range * 2.0f, range * 2.0f)
                .min(tgt -> tgt.team != b.team && tgt.type.hittable, b::dst2);
        if(target == null){
            super.updateHoming(b);
        }
    }

    @Override
    public void hit(Bullet b, float x, float y) {
        super.hit(b, x, y);
        if(b.absorbed) return;

        Groups.bullet.intersect(x - radius, y - radius, radius * 2.0f, radius * 2.0f, other -> {
            if(b.team != other.team && b.type.hittable){
                float v = splashDamage;
                if(other.damage > v){
                    other.damage -= v;
                }else{
                    other.remove();
                }
            }
        });
    }
}
