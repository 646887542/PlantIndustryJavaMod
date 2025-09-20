const 星际发射台 = extend(LaunchPad, "星际发射台", {
});
星际发射台.buildType = prov(() => {
	return extend(LaunchPad.LaunchPadBuild, 星际发射台, {
		updateTile() {
			this.super$updateTile();
		},
		draw() {
			this.super$draw();
		},
		buildConfiguration(table) {
			this.super$buildConfiguration(table);
			for(let i = 0; i < Vars.content.planets().size; i++) {
				table.row();
				let planet = Vars.content.planets().get(i);
				if(Vars.state.isCampaign()) {
					table.button(planet.localizedName,
						Icon.icons.get(planet.icon + "Small",
						Icon.icons.get(planet.icon, Icon.commandRallySmall)),
						Styles.flatTogglet,
						run(()=> {
							let other = Vars.ui.planet.listener;
							other => {
								if(state.isCampaign() && other.planet == planet){
									Vars.state.rules.sector.info.destination = planet;
								}
							}
							Vars.ui.planet.showSelect(planet, other);
							deselect();
						}))
						.width(190).height(40).growX()
						.update(bb => bb.setChecked(Vars.state.rules.sector.planet == planet))
						.with(w => w.marginLeft(10))
						//.disabled()禁用按钮，但用不了。
						.get().getChildren().get(1)
						.setColor(planet.iconColor);
				}
			}
		}
	})
});
exports.星际发射台 = 星际发射台;