package pi.content;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.ShapePart;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import pi.PlantIndustry;
import pi.classes.blocks.DefensiveTower;
import pi.classes.blocks.InterstellarLaunchPad;
import pi.classes.blocks.ItemDefenseTurret;
import pi.classes.bullets.EmpInterceptBulletType;

import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static mindustry.entities.UnitSorts.*;
import static mindustry.gen.Sounds.*;
import static mindustry.type.ItemStack.with;
import static pi.content.PiItemTypes.*;

public class PiBlockTypes {
    public static Block BaseCore, InterstellarLaunchPad, Gypsophila, OrientalPearlDefenseTower;

    public static void load() {
        BaseCore = Vars.content.block(PlantIndustry.name("基地"));
        InterstellarLaunchPad = new InterstellarLaunchPad("星际发射台") {{
            consumesPower = true;
            consumePower(30f);
            consumeLiquid(cryofluid, 5f);
            requirements(Category.effect, with(copper, 5000, lead, 6000, Crystal, 1200));
            itemCapacity = 60;
            launchTime = 3600f;
            size = 3;
            liquidCapacity = 600f;
        }};

        OrientalPearlDefenseTower = new DefensiveTower("东方明珠防御塔") {{
            consumesPower = true;
            consumePower(15f);
            consumeLiquid(cryofluid, 3f);
            requirements(Category.effect, with(copper, 1000, lead, 2000, plastanium, 500, phaseFabric, 500));
            size = 6;
            health = 6000;
            liquidCapacity = 320;
            shootSound = release;
            range = 800f;
            reload = 60f;
            cooldownTime = 120f;
            inaccuracy = 0f;
            rotateSpeed = 6f;
            targetAir = true;
            targetGround = true;
            PedestalOffsetY = 24;
            headOffsetY = 80;
            shootType = new LaserBoltBulletType() {{
                hitSize = 5;
                height = 400;
                width = 5;
                pierce = pierceBuilding = true;
                ammoMultiplier = 1f;
                speed = 20;
                lifetime = 60f;
                damage = 5000;
                collideTerrain = true;
                collidesTiles = true;
            }};
        }};

        Gypsophila = new ItemDefenseTurret("Gypsophila") {{
            // 基础属性
            size = 3;
            health = 3800;
            armor = 20f;
            liquidCapacity = 320;
            shootSound = release;

            // 战斗属性
            ammoPerShot = 1;
            range = 480f;
            reload = 60f;
            cooldownTime = 40f;
            inaccuracy = 0f;
            rotateSpeed = 3f;

            // 目标选择
            targetAir = true;
            targetHealing = true;
            targetGround = true;
            unitSort = weakest;

            // 资源消耗
            consumePower(45f);
            consumeLiquid(cryofluid, 1.5f);
            requirements(Category.turret, with(
                    plastanium, 60,
                    silicon, 100,
                    Biomass, 90,
                    Crystal, 150
            ));

            ammo(
                    titanium, new EmpInterceptBulletType() {{
                        // 基础属性
                        scaleLife = true;
                        timeIncrease = 2f;
                        powerDamageScl = 2.5f;
                        powerSclDecrease = 0.6f;
                        unitDamageScl = 1.6f;
                        height = 16f;
                        width = 12f;
                        hitSize = 8f;
                        weaveScale = 5f;
                        weaveMag = 4f;
                        absorbable = false;

                        // 治疗属性
                        healAmount = 108f;
                        healPercent = 10.8f;

                        // 制导属性
                        homingDelay = 25f;
                        homingPower = 0.1f;
                        homingRange = 60f;

                        // 伤害属性
                        damage = 108f;
                        pierceArmor = true;
                        splashDamageRadius = 100f;
                        splashDamage = 108f;
                        status = PiStatuses.Mismatch;

                        // 视觉效果
                        sprite = "missile-large";
                        frontColor = Color.valueOf("FFFFFF");
                        backColor = Color.valueOf("7e88dd");
                        trailLength = 46;
                        trailWidth = 2f;
                        trailColor = Color.valueOf("7e88dd");

                        // 运动属性
                        speed = 6f;
                        lifetime = 90f;

                        // 部件配置
                        parts.add(new ShapePart(){
                            {
                                color = Color.valueOf("7e88dd");
                                circle = true;
                                hollow = true;
                                layer = 100f;
                                stroke = 0f;
                                strokeTo = 3f;
                                radius = 60f;
                                radiusTo = 7f;
                            }
                        });

                        // 命中效果
                        hitEffect = new MultiEffect(
                                new WaveEffect(){
                                    {
                                        lifetime = 60f;
                                        sizeFrom = 60f;
                                        sizeTo = 100f;
                                        strokeFrom = 8f;
                                        strokeTo = 0f;
                                        interp = Interp.circleOut;
                                        colorFrom = Color.valueOf("7e88dd");
                                        colorTo = Color.valueOf("7e88dd");
                                    }
                                }
                        );

                        // 音效
                        shootEffect = Fx.shootBig2;
                        hitSound = Sounds.plasmaboom;

                        // 间隔子弹配置
                        bulletInterval = 2f;
                        intervalBullets = 1;
                        intervalDelay = 20f;
                        intervalBullet = new EmpInterceptBulletType(){
                            {
                                timeIncrease = 1f;
                                powerDamageScl = 2f;
                                powerSclDecrease = 0.6f;
                                unitDamageScl = 2f;
                                width = 6f;
                                height = 6f;
                                shrinkY = 0f;
                                sprite = "circle-bullet";
                                frontColor = Color.valueOf("7e88dd");
                                backColor = Color.valueOf("7e88dd");
                                absorbable = false;
                                collidesTeam = true;
                                healAmount = 48f;
                                trailLength = 10;
                                trailWidth = 1.8f;
                                trailColor = Color.valueOf("7e88dd");
                                homingDelay = 1f;
                                homingPower = 0.64f;
                                homingRange = 100f;
                                speed = 8f;
                                lifetime = 20f;
                                damage = 28f;
                                splashDamage = 30f;
                                splashDamageRadius = 40f;
                                pierceArmor = true;
                                radius = 100f;
                                knockback = 10f;
                                hitColor = Color.valueOf("7e88dd");
                                status = PiStatuses.Mismatch;
                                statusDuration = 8f * 60f;

                                hitEffect = new MultiEffect(
                                        Fx.hitBulletColor,
                                        new ParticleEffect(){
                                            {
                                                particles = 5;
                                                strokeFrom = 3f;
                                                strokeTo = 0f;
                                                lenFrom = 18f;
                                                lenTo = 0f;
                                                line = true;
                                                length = 30f;
                                                baseLength = 0f;
                                                lifetime = 20f;
                                                sizeInterp = Interp.circleOut;
                                                colorFrom = Color.valueOf("7e88dd");
                                                colorTo = Color.valueOf("7e88dd");
                                                cone = 30f;
                                            }
                                        }
                                );

                                despawnEffect = new WaveEffect(){
                                    {
                                        strokeFrom = 5f;
                                        strokeTo = 0f;
                                        lifetime = 30f;
                                        sizeFrom = 2f;
                                        sizeTo = 10f;
                                        interp = Interp.circleOut;
                                    }
                                };
                            }
                        };
                    }}
            );
        }};
    }

    public static void loadBlock() {
    }
}
