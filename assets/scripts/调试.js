exports.cont = {
	name : "调试",
	load(){
		let ui = this.ui = new BaseDialog(this.name);
		try {
			ui.cont.table(cons(t => {
				t.table(cons(t => {
					t.label(() => { return '星球调试模式:'+ (PlanetDialog.debugSelect? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!PlanetDialog.debugSelect)
						{
							PlanetDialog.debugSelect = true;
						}
						else
						{
							PlanetDialog.debugSelect = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '科技树显示研究需求:'+ (ResearchDialog.debugShowRequirements? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!ResearchDialog.debugShowRequirements)
						{
							ResearchDialog.debugShowRequirements = true;
						}
						else
						{
							ResearchDialog.debugShowRequirements = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '无限资源:'+ (Vars.state.rules.infiniteResources? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.state.rules.infiniteResources)
						{
							Vars.state.rules.infiniteResources = true;
						}
						else
						{
							Vars.state.rules.infiniteResources = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '作弊模式:'+ (Vars.player.team().rules().cheat? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.player.team().rules().cheat)
						{
							Vars.player.team().rules().cheat = true;
						}
						else
						{
							Vars.player.team().rules().cheat = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '游戏实验功能:'+ (Vars.experimental? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.experimental)
						{
							Vars.experimental = true;
						}
						else
						{
							Vars.experimental = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '允许拆除环境方块:'+ (Vars.state.rules.allowEnvironmentDeconstruct? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!(Vars.state.rules.allowEnvironmentDeconstruct === undefined))
						{
							if (!Vars.state.rules.allowEnvironmentDeconstruct)
							{
								Vars.state.rules.allowEnvironmentDeconstruct = true;
							}
							else
							{
								Vars.state.rules.allowEnvironmentDeconstruct = false;
							}
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '关闭光线:'+ ((!Vars.enableLight)? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.enableLight)
						{
							Vars.enableLight = true;
						}
						else
						{
							Vars.enableLight = false;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '关闭阴影:'+ ((!Vars.enableDarkness)? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.enableDarkness)
						{
							Vars.enableDarkness = true;
						}
						else
						{
							Vars.enableDarkness = false;
						}
					}));
				})).row();
				t.table(cons(t => {
				t.label(() => { return '启用世界处理器:'+ ((Blocks.worldProcessor.buildVisibility == BuildVisibility.shown && Blocks.worldProcessor.privileged == false)? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!(Blocks.worldProcessor.buildVisibility == BuildVisibility.shown && Blocks.worldProcessor.privileged == false))
						{
							Blocks.worldProcessor.buildVisibility=BuildVisibility.shown;
							Blocks.worldProcessor.privileged=false;
						}
						else
						{
							Blocks.worldProcessor.buildVisibility=BuildVisibility.editorOnly;
							Blocks.worldProcessor.privileged=true;
						}
					}));
				})).row();
				t.table(cons(t => {
					t.label(() => { return '有效载荷更新:'+ (Vars.state.rules.unitPayloadUpdate? '已开启  ' : '已关闭  ') });
					t.button('开启',run(() => {
						if (!Vars.state.rules.unitPayloadUpdate)
						{
							Vars.state.rules.unitPayloadUpdate = true;
						}
						else
						{
							Vars.state.rules.unitPayloadUpdate = false;
						}
					}));
				})).row();
			})).row();
		} catch (err) {
			Vars.ui.showErrorMessage(err);
		}
		ui.buttons.button('$back', Icon.left, run(() => {
			ui.hide();
		})).size(160, 65);
	},
	buildConfiguration(table){
		// 如果进入游戏才显示
		if(Vars.state.isGame()) this.ui.show();
	}
}