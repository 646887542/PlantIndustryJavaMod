const SpriteDrawf = {
	drawTrait(picture, x, y, width, length, rotation){
		var oy = 17 / 63 * length ;
		Draw.rect(Core.atlas.find(picture), x, y - oy + length / 2, width, length, width / 2, oy, rotation - 90);
	}
}

this.global.SpriteDrawf = SpriteDrawf;