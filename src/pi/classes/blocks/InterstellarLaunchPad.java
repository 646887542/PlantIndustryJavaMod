package pi.classes.blocks;
//代码来自：https://github.com/zeutd/Disintegration/blob/master/main/src/disintegration/world/blocks/campaign/SpaceStationLaunchPad.java

import arc.Core;
import arc.Graphics.Cursor;
import arc.Graphics.Cursor.SystemCursor;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.LaunchPayload;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import pi.content.PiItemTypes;

import static mindustry.Vars.*;

public class InterstellarLaunchPad extends Block{
    /** Time inbetween launches. */
    public float launchTime = 1f;
    public Sound launchSound = Sounds.none;

    public TextureRegion lightRegion;
    public TextureRegion podRegion;
    public Color lightColor = Color.valueOf("eab678");
    public static Planet selectedPlanet;

    public InterstellarLaunchPad(String name){
        super(name);
        hasItems = true;
        solid = true;
        update = true;
        configurable = true;
        flags = EnumSet.of(BlockFlag.launchPad);
    }

    @Override
    public void load() {
        super.load();
        region = Core.atlas.find(name);
        lightRegion = Core.atlas.find(name + "-light");
        podRegion = Core.atlas.find("-launchPod");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.launchTime, launchTime / 60f, StatUnit.seconds);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("items", entity -> new Bar(() -> Core.bundle.format("bar.items", entity.items.total()), () -> Pal.items, () -> (float)entity.items.total() / itemCapacity));

        //TODO is "bar.launchcooldown" the right terminology?
        addBar("progress", (InterstellarLaunchPadBuild build) -> new Bar(() -> Core.bundle.get("bar.launchcooldown"), () -> Pal.ammo, () -> Mathf.clamp(build.launchCounter / launchTime)));
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class InterstellarLaunchPadBuild extends Building{
        public float launchCounter;

        @Override
        public Cursor getCursor(){
            return !state.isCampaign() || net.client() ? SystemCursor.arrow : super.getCursor();
        }

        @Override
        public boolean shouldConsume(){
            //TODO add launch costs, maybe legacy version
            return launchCounter < launchTime;
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress) return Mathf.clamp(launchCounter / launchTime);
            return super.sense(sensor);
        }

        @Override
        public void draw(){
            super.draw();

            if(!state.isCampaign()) return;

            if(lightRegion.found()){
                Draw.color(lightColor);
                float progress = Math.min((float)items.total() / itemCapacity, launchCounter / launchTime);
                int steps = 3;
                float step = 1f;

                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < steps; j++){
                        float alpha = Mathf.curve(progress, (float)j / steps, (j+1f) / steps);
                        float offset = -(j - 1f) * step;

                        Draw.color(Pal.metalGrayDark, lightColor, alpha);
                        Draw.rect(lightRegion, x + Geometry.d8edge(i).x * offset, y + Geometry.d8edge(i).y * offset, i * 90);
                    }
                }

                Draw.reset();
            }

            Draw.rect(podRegion, x, y);

            Draw.reset();
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            Item[] pItems = {PiItemTypes.LuminousBerry, PiItemTypes.Biomass};
            for (Item pItem : pItems) {
                if (pItem == item) return items.total() < itemCapacity;
            }
            return false;
        }

        @Override
        public void updateTile(){
            if(!state.isCampaign()) return;

            //increment launchCounter then launch when full and base conditions are met
            if((launchCounter += edelta()) >= launchTime && items.total() >= itemCapacity){
                //if there are item requirements, use those.
                consume();
                launchSound.at(x, y);
                LaunchPayload entity = LaunchPayload.create();
                items.each((item, amount) -> entity.stacks.add(new ItemStack(item, amount)));
                entity.set(this);
                entity.lifetime(120f);
                entity.team(team);
                entity.add();
                Fx.launchPod.at(this);
                items.clear();
                Effect.shake(3f, 3f, this);
                launchCounter = 0f;
            }
        }

        @Override
        public void display(Table table){
            super.display(table);

            if(!state.isCampaign() || net.client() || team != player.team()) return;

            table.row();
            table.label(() -> {
                Sector dest = state.rules.sector == null ? null : state.rules.sector.info.getRealDestination();

                return Core.bundle.format("launch.destination",
                        dest == null || !dest.hasBase() ? Core.bundle.get("sectors.nonelaunch") :
                                "[accent]" + dest.name());
            }).pad(4).wrap().width(200f).left();
        }

        @Override
        public void buildConfiguration(Table table) {
            if (!state.isCampaign() || net.client()) {
                deselect();
                return;
            }
            for (int i = 0; i < content.planets().size; i++) {
                Planet planet = content.planets().get(i);
                table.button(planet.localizedName,
                            Icon.icons.get(planet.icon + "Small",
                            Icon.icons.get(planet.icon, Icon.commandRallySmall)),
                            Styles.flatTogglet, () -> {
                                selectedPlanet = planet;
                                ui.planet.showSelect(selectedPlanet.getLastSector(), other -> {
                                    if(state.isCampaign() && planet.getStartSector() != null){
                                        state.rules.sector.info.destination = other;
                                    }
                                });
                                deselect();
                            })
                            .width(190).height(40).growX()
                            .update(bb -> bb.setChecked(selectedPlanet == planet && planet.getStartSector() != null))
                            .with(w -> w.marginLeft(10f))
                            .disabled(planet.getStartSector() == null)
                            .get().getChildren().get(1)
                            .setColor(planet.iconColor);
                    table.row();
                }
            }

        @Override
        public byte version(){
            return 1;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(launchCounter);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(revision >= 1){
                launchCounter = read.f();
            }
        }
    }
}