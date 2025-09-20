const 伐木机 = extend(BeamDrill, "伐木机", {
	drawPlace(x, y, rotation, valid){
		this.drawPotentialLinks(x, y);
		this.super$drawPlace(x, y, rotation, valid);
	},
	setStats(){
		this.super$setStats();
		this.stats.add(Stat.boostEffect, StatUnit.timesSpeed);
	},
});
伐木机.buildType = prov(() => {
	const payloads = [
		new BuildPayload(Blocks.battery, Team.derelict)
	];
	return new JavaAdapter(BeamDrill.BeamDrillBuild, {
	//return extend(BeamDrill.BeamDrillBuild, 伐木机, {
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
/*伐木机.drillTime = 150;
伐木机.tier = 5;
伐木机.size = 2;
伐木机.range = 5;
伐木机.hasPower = true;
伐木机.drawArrow = true;
伐木机.consumes.power(1);
伐木机.consumes.liquid(Liquids.water, 0.03).boost();
伐木机.requirements = ItemStack.with(
	Items.copper, 85,
	Items.graphite, 55,
	Items.silicon, 55
);
伐木机.buildVisibility = BuildVisibility.shown;
伐木机.category = Category.production;*/
exports.伐木机 = 伐木机;