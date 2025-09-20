const 特殊核心 = extend(CoreBlock, "特殊核心", {});

特殊核心.buildType = prov(() => {
	let kill = false, num = 1, time = 60 * num;
	return extend(CoreBlock.CoreBuild, 特殊核心, {
		handleDamage(amount) {
			return 0;
		}
	})
});
exports.特殊核心 = 特殊核心;