package pi.classes.blocks;

import mindustry.gen.Groups;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class ItemDefenseTurret extends ItemTurret {

    public ItemDefenseTurret(String name) {
        super(name);
        buildType = ItemDefenseBuild::new;
    }

    public class ItemDefenseBuild extends ItemTurret.ItemTurretBuild {

        @Override
        public void updateTile() {
            if (Groups.bullet.intersect(this.x - ItemDefenseTurret.this.range, this.y - ItemDefenseTurret.this.range, ItemDefenseTurret.this.range * 2.0F, ItemDefenseTurret.this.range * 2.0F).min((b) -> b.team != this.team && b.type().hittable, (b) -> b.dst2(this)) != null) {
                this.target = Groups.bullet.intersect(this.x - ItemDefenseTurret.this.range, this.y - ItemDefenseTurret.this.range, ItemDefenseTurret.this.range * 2.0F, ItemDefenseTurret.this.range * 2.0F).min((b) -> b.team != this.team && b.type().hittable, (b) -> b.dst2(this));

                if (this.target != null && !this.target.isAdded()) {
                    this.target = null;
                }
            }

            super.updateTile();
        }
    }
}
