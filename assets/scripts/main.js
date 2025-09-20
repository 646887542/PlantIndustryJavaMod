/* 用函数封装，为了稍后执行 */
let contArr = ['调试','单位生成'].map(str => {
    let cont;
    /* 不让游戏崩溃 */
    try {
        cont = require(str).cont;
    } catch (err) {
        Vars.ui.showErrorMessage(err);
    }
    return cont;
});

function addTable(Table){
    Table.fillParent = true;
    Table.y = 150;
    Table.image().color(Color.sky).margin(0).pad(0).padBottom(0).fillX().height(40).get().addListener(
        extend(InputListener, {
            touchDown(event, x, y, pointer, button) {
                this.bx = x;
                this.by = y;
                return true;
            },
            touchDragged(event, x, y, pointer) {
                let v = Table.localToStageCoordinates(Tmp.v1.set(x, y));
                Table.x = -this.bx + v.x;
                Table.y = -this.by + v.y;
            }
        }));
    Table.row();
    Table.table(Styles.black5, cons(t => {
        for (var c of contArr) {
            let cont = c;
            if (cont == null) continue;
            if (cont.load instanceof Function) cont.load();

            t.button(cont.name, Styles.cleart, run(() => {
                cont.buildConfiguration(Table.cont);
            })).size(100, 40).row();
        }
    })).row();
    Table.update(run(() => {
        if(Table.x < Core.graphics.getWidth()*0.05) Table.x = Core.graphics.getWidth()*0.05;
        if(Table.y < Core.graphics.getHeight()*0.05) Table.y = Core.graphics.getHeight()*0.05;
        if(Table.x > Core.graphics.getWidth()*0.95) Table.x = Core.graphics.getWidth()*0.95;
        if(Table.y > Core.graphics.getHeight()*0.95) Table.y = Core.graphics.getHeight()*0.95;
    }));
    Table.visibility = () => {
        if(!Vars.ui.hudfrag.shown) return false;
        if(Vars.ui.minimapfrag.shown()) return false;
        if(!Vars.mobile) return true;
        if(Vars.player.unit().isBuilding()) return false;
        if(Vars.control.input.block != null) return false;
        if(Vars.control.input.mode == PlaceMode.breaking) return false;
        if(!Vars.control.input.selectRequests.isEmpty() && Vars.control.input.lastSchematic != null && !Vars.control.input.selectRequests.isEmpty()) return false;
        return true;
    };
}

if(!Vars.headless){
    var tc = new Table();

    Events.on(ClientLoadEvent, () => {
        tc.bottom().left();
        addTable(tc);
        Vars.ui.hudGroup.addChild(tc);
    });
}

//由于极其sb的原因，require有问题，导致了这一坨屎山。
/*-------------------------------------------------------------------------*/
const 伐木机 = extend(BeamDrill, "伐木机", {
    drawPlace(x, y, rotation, valid){
        this.drawPotentialLinks(x, y);
        this.super$drawPlace(x, y, rotation, valid);
    },
    setStats(){
        this.super$setStats();
    },
});
伐木机.buildType = prov(() => {
    return new JavaAdapter(BeamDrill.BeamDrillBuild, {
        acceptItem(source, item) {
            if(this.items.get(item) < this.block.itemCapacity) {
                    return true;
            }
        },
        delta(){
            return Time.delta * this.timeScale;
        }
    }, 伐木机);
});

/*-------------------------------------------------------------------------*/
const 基地 = extend(CoreBlock, "基地", {
    canBreak(tile) {
        return Vars.state.teams.cores(tile.team()).size > 1;
    },
    canReplace(other) {
        return other.alwaysReplace;
    },
    canPlaceOn(tile, team, rotation) {
        return Vars.state.teams.cores(team).size < 7;
    }
});
基地.buildType = prov(() => {
    let kill = false, num = 1, time = 60 * num;
    return extend(CoreBlock.CoreBuild, 基地, {
        updateTile() {
            this.super$updateTile();
            if (Vars.state.teams.cores(this.team).size > 10) kill = true;
            if (kill) {
                if (!Vars.headless) {
                    Vars.ui.showLabel("[red]     数据上行堵塞\n▲中央数据库过载▲\n     强制重启倒计时", 0.015, this.x, this.y);
                }
                time--
                if (time == 0) {
                    this.kill();
                }
            }
        },
        draw() {
            this.super$draw();
            Draw.z(Layer.effect);
            Lines.stroke(2, Color.valueOf("FF5B5B"));
            Draw.alpha(kill ? 1 : Vars.state.teams.cores(this.team).size > 9 ? 1 : 0);
            Lines.arc(this.x, this.y, 16, time * (6 / num) / 360, 90);
        }
    })
});

/*-------------------------------------------------------------------------*/
const 树 = extend(TreeBlock, "树", {});
树.destructible = true;
树.health = 100;
树.buildVisibility = BuildVisibility.shown;
树.category = Category.production;

/*-------------------------------------------------------------------------*/
const 带桥 = extend(ItemBridge, "带桥", {});
带桥.update = true;
//设置可使用的弹药
const TItems = [Items.copper, Items.graphite, Items.pyratite, Items.silicon, Items.thorium]
带桥.buildType = prov(() => {
    //创建多炮塔
    //(方块，队伍(不需要设置))
    const payloads = [
        new BuildPayload(Blocks.salvo, Team.derelict),
        new BuildPayload(Blocks.salvo, Team.derelict),
        new BuildPayload(Blocks.salvo, Team.derelict),
        new BuildPayload(Blocks.salvo, Team.derelict)
    ];
    const build = extend(ItemBridge.ItemBridgeBuild, 带桥, {
        //设置方块进入物品规则
        //可以自己设置规则
        /*
        acceptItem(source, item) {
            for(var i = 0; i < TItems.length; i++){
                if(TItems[i] == item){
                    if(this.items.get(TItems[i]) < this.block.itemCapacity){
                        return true;
                    }
                }
            }

            return false;
        },*/
        updateTile() {
            this.super$updateTile();

            //可以让炮塔转起来的代码
            //删除注释以使用
            /*for (var i = 0; i < payloads.length; i++) {
                var t = payloads[i];
                var rotation = (360.0 / payloads.length) * i + Time.time;

                //这里的24为距离本体方块中心的多少距离旋转(8为1格)
                t.set(x + Angles.trnsx(rotation, 24), y + Angles.trnsy(rotation, 24), t.build.payloadRotation);
            }*/

            //设置模块
            for(var id = 0; id < payloads.length; id++){
                //设置队伍，如果在上面的创建位置设置，无用
                if(payloads[id].build.team != this.team){
                    payloads[id].build.team = this.team;
                }

                //执行炮塔更新
                payloads[id].update(null, this);

                //为物品炮塔添加弹药
                //你们需要可自己定义
                for(var i = 0; i < TItems.length; i++){
                    if(payloads[id].build.acceptItem(payloads[id].build, TItems[i]) && this.items.get(TItems[i]) >= 1) {
                        payloads[id].build.handleItem(payloads[id].build, TItems[i]);
                        this.items.remove(TItems[i], 1);
                    }
                }
            }

            //设置炮塔的位置
            //有需求你们可以自己定义
            //（x, y, r）
            payloads[0].set(this.x + 24, this.y + 24, payloads[0].build.payloadRotation);
            payloads[1].set(this.x + 24, this.y - 24, payloads[1].build.payloadRotation);
            payloads[2].set(this.x - 24, this.y - 24, payloads[2].build.payloadRotation);
            payloads[3].set(this.x - 24, this.y + 24, payloads[3].build.payloadRotation);
        },
        draw(){
            this.super$draw();

            //执行多炮塔的动画
            for(var i = 0; i < payloads.length; i++){
                payloads[i].draw();
            }
        },
        write(write) {
            this.super$write(write);

            //往地图里写入多炮塔的数据
            //用于保存地图
            for(var i = 0; i < payloads.length; i++){
                Payload.write(payloads[i], write);
            }
        },
        read(read, revision) {
            this.super$read(read, revision);

            //在地图里读取数据
            //用于加载地图
            for(var i = 0; i < payloads.length; i++){
                payloads[i] = Payload.read(read);
            }
        }
    });
    return build;
});

/*-------------------------------------------------------------------------*/
const 物品接收点 = extend(CoreBlock, "物品接收点", {
    canBreak() { return Vars.state.teams.cores(Vars.player.team()).size > 1; },
    canReplace(other) { return other.alwaysReplace; },
    canPlaceOn(tile, team) { return true; },
});

/*-------------------------------------------------------------------------*/
require("blocks/特殊核心");
