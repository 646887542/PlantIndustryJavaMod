const 树 = extend(TreeBlock, "树", {});
树.itemDrop = Items.copper;
树.destructible = true;
树.health = 100;
树.buildVisibility = BuildVisibility.shown;
树.category = Category.production;
exports.树 = 树;