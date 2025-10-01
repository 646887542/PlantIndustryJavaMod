package pi.classes.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Blocks;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.PowerTurret;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DefensiveTower extends PowerTurret {
    private static final Set<Block> WATER_BLOCKS = new HashSet<>(Arrays.asList(
            Blocks.water,
            Blocks.deepwater,
            Blocks.darksandWater,
            Blocks.sandWater,
            Blocks.darksandTaintedWater,
            Blocks.taintedWater,
            Blocks.deepTaintedWater
    ));
    // 底座偏移
    public float PedestalOffsetY;
    // 头部偏移
    public float headOffsetY;
    // 身体贴图
    public TextureRegion bodyTR;
    // 头部贴图
    public TextureRegion headTR;
    public DefensiveTower(String name) {
        super(name);
        buildType = DefensiveTowerBuild::new;
        // 不渲染阴影
        hasShadow = false;
        // 开启加载
        update = true;
    }

    @Override
    public void load() {
        super.load();
        // 加载对应的贴图
        bodyTR = Core.atlas.find(name + "-body");
        Log.infoTag("Debug", String.valueOf(bodyTR));
        headTR = Core.atlas.find(name + "-head");
        Log.infoTag("Debug", String.valueOf(headTR));
    }

    // 这里需要覆写这个方法，因为Turret类默认会加上底座
    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{region};
    }

    public class DefensiveTowerBuild extends PowerTurretBuild {
        // 无需调用父类，进行自定义绘制
        @Override
        public void draw() {
            float anchorX = x;
            float anchorY = y;
            // 绘制阴影
            // Drawf.shadow(anchorX, anchorY, 40f);
            Draw.rect(bodyTR, anchorX, anchorY + PedestalOffsetY);
            Draw.z(Layer.turret + 1);
            // 将炮台的heat字段设置为alpha值，heat是从[1f,0f]的float
            Draw.alpha(heat);
            Draw.rect(headTR, anchorX, anchorY + headOffsetY);
            // 清空颜色的设置
            Draw.color();
        }

        @Override
        protected void shoot(BulletType type) {
            float bulletX = this.x + Angles.trnsx(this.rotation - 90.0F, DefensiveTower.this.shootX, DefensiveTower.this.shootY);
            float bulletY = this.y + headOffsetY + Angles.trnsy(this.rotation - 90.0F, DefensiveTower.this.shootX, DefensiveTower.this.shootY);
            if (DefensiveTower.this.shoot.firstShotDelay > 0.0F) {
                DefensiveTower.this.chargeSound.at(bulletX, bulletY, Mathf.random(DefensiveTower.this.soundPitchMin, DefensiveTower.this.soundPitchMax));
                type.chargeEffect.at(bulletX, bulletY, this.rotation);
            }

            DefensiveTower.this.shoot.shoot(this.barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
                ++this.queuedBullets;
                if (delay > 0.0F) {
                    Time.run(delay, () -> this.bullet(type, xOffset, yOffset + headOffsetY, angle, mover));
                } else {
                    this.bullet(type, xOffset, yOffset + headOffsetY, angle, mover);
                }

            }, () -> ++this.barrelCounter);
            if (DefensiveTower.this.consumeAmmoOnce) {
                this.useAmmo();
            }

        }

        @Override
        public void updateTile() {
            this.target = Units.bestTarget(this.team, this.x, this.y, DefensiveTower.this.range * 2, (e) -> !e.dead() && DefensiveTower.this.unitFilter.get(e) && (e.isGrounded() || DefensiveTower.this.targetAir) && (!e.isGrounded() || DefensiveTower.this.targetGround), (b) -> DefensiveTower.this.targetGround && DefensiveTower.this.buildingFilter.get(b), DefensiveTower.this.unitSort);
            if (this.target != null && !this.target.getClass().getName().equals("water-extractor")) {
                if (!WATER_BLOCKS.contains(this.target.tileOn().floor())) {
                    this.target = null;
                }
            }

            if (this.target != null && !this.target.isAdded()) {
                this.target = null;
            }

            super.updateTile();
        }
    }
}
