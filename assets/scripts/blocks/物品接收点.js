const 物品接收点 = extend(CoreBlock, "物品接收点", {
	canBreak() { return Vars.state.teams.cores(Vars.player.team()).size > 1; },
	canReplace(other) { return other.alwaysReplace; },
	canPlaceOn(tile, team) { return true; },
});
exports.物品接收点 = 物品接收点;