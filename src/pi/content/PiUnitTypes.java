package pi.content;

import arc.math.geom.Rect;
import arc.struct.ObjectSet;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.ShieldArcAbility;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.EntityMapping;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.unit.TankUnitType;
import mindustry.world.meta.BlockFlag;
import pi.PlantIndustry;
import pi.content.entities.DaYunEntity;

public class PiUnitTypes {
    public static UnitType DaYun, RoadRoller;
    static {
        EntityMapping.nameMap.put(PlantIndustry.name("DaYun"), DaYunEntity::new);
        EntityMapping.nameMap.put(PlantIndustry.name("RoadRoller"), EntityMapping.idMap[43]);
    }

    public static void load() {
        DaYun = new TankUnitType("DaYun") {
            {
                constructor = DaYunEntity::new;
                drag = 0.66f;
                speed = 15f;
                rotateSpeed = 5f;
                accel = 0.6f;
                itemCapacity = 40;
                health = 90000f;
                canBoost = true;
                hitSize = 60f;
                armor = 220f;
                crushDamage = 100f;
                treadPullOffset = 1;
                clipSize = 60f;
                targetGround = false;
                autoFindTarget = true;
                faceTarget = true;
                hovering = true;
                canDrown = false;
                treadRects = new Rect[]{new Rect(0, 0, 600, 300)};
                immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.freezing, StatusEffects.invincible,
                        StatusEffects.corroded, StatusEffects.slow, StatusEffects.melting, StatusEffects.wet, StatusEffects.unmoving,
                        StatusEffects.sapped, StatusEffects.sporeSlowed, StatusEffects.tarred
                );

                abilities.add(new RepairFieldAbility(8000f, 60f, 5f));

                targetFlags = new BlockFlag[]{BlockFlag.factory, BlockFlag.turret};

                weapons.add(new Weapon("none") {{
                    this.bullet = new BasicBulletType() {{
                        this.maxRange = 100f;
                        this.lifetime = 0f;
                    }};
                }});
            }
        };

        RoadRoller = new TankUnitType("RoadRoller"){
            {
                drag = 1.0f;
                accel = 0.9f;
                health = 60000f;
                armor = 12000f;
                crushDamage = 40f;
                hitSize = 40f;
                range = 180;
                autoFindTarget = true;
                faceTarget = true;
                canBoost = true;
                hovering = true;
                canDrown = false;
                speed = 12f;
                rotateSpeed = 6.5f;
                immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.freezing, StatusEffects.invincible,
                        StatusEffects.corroded, StatusEffects.slow, StatusEffects.unmoving,
                        StatusEffects.sapped, StatusEffects.sporeSlowed, StatusEffects.tarred
                );

                targetFlags = new BlockFlag[]{BlockFlag.battery, BlockFlag.factory, BlockFlag.hasFogRadius};

                abilities.add(new RepairFieldAbility(3000f, 60f, 5f));
                abilities.add(new ShieldArcAbility() {{
                    this.max = 5000f;
                    this.cooldown = 30f;
                    this.width = 60f;
                    this.angle = 120f;
                }});

                weapons.add(new Weapon("MuDaMuDa") {{
                    this.top = true;
                    this.reload = 0.1f;
                    this.rotate = true;
                    this.bullet = new BasicBulletType() {{
                        this.maxRange = 180f;
                        this.lifetime = 0F;
                        this.speed = 0f;
                        this.fragBullets = 5;
                        this.fragRandomSpread = 60f;
                        this.fragLifeMax = 0.35f;
                        this.fragLifeMin = 0.15f;
                        this.fragVelocityMax = 12f;
                        this.fragVelocityMin = 9f;
                        this.fragBullet = new BasicBulletType() {{
                            this.width = 42.0F;
                            this.height = 54.0F;
                            this.pierce = true;
                            this.damage = 100f;
                            this.sprite = PlantIndustry.name("MuDa");
                        }};
                    }};
                }});
            }
        };
    }
}