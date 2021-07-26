from jsongen import *


with open("list/vertical_slab.txt", 'r') as reader:
	for line in reader:
		
		name = line.split(",")[0]
		texture = line.split(",")[1]
		craft = line.split(",")[2].replace("\r", "").replace("\n", "")
		slab = craft.replace("bricks", "brick").replace("tiles", "tile")

		#state
		copy_template(name, "vertical_slab/blockstate.json", "panel/blockstate/{name}_vertical_slab.json")
		#recipe
		copy_template_params("stonecutting_recipe.json", "panel/recipe/"+name+"_vertical_slab_stonecutting.json", {
			"item": "minecraft:"+craft,
			"result": "buildersaddition:"+name+"_vertical_slab",
			"count": "2"
		})
		copy_template_params("vertical_slab/crafting.json", "panel/recipe/"+name+"_vertical_slab.json", {
			"item": "minecraft:"+name,
			"result": "buildersaddition:"+name+"_vertical_slab"
		})
		copy_template_params("vertical_slab/reverse.json", "slab/recipe/reverse/"+name+"_slab.json", {
			"item": "minecraft:"+slab+"_slab",
			"result": "minecraft:"+craft
		})
		copy_template_params("vertical_slab/reverse.json", "panel/recipe/reverse/"+name+"_vertical_slab.json", {
			"item": "buildersaddition:"+name+"_vertical_slab",
			"result": "minecraft:"+craft
		})
		#loot
		copy_template_params("vertical_slab/loottable.json", "panel/loottable/"+name+"_vertical_slab.json", {"name": name+"_vertical_slab"})
		#Item
		copy_template_params("item_parent_generic.json", "panel/item/"+name+"_vertical_slab.json", {"name": name+"_north", "type": "slab"})
		#model
		
		sandstone_top = texture.replace("cut_", "")+"_top"
		sandstone_bottom = texture.replace("cut_", "")+"_bottom"

		if "smooth" in name:
			copy_template(name, "vertical_slab/base/vertical_slab_double_base.json", "panel/model/slab/{name}_double.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_east_base.json", "panel/model/slab/{name}_east.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_north_base.json", "panel/model/slab/{name}_north.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_south_base.json", "panel/model/slab/{name}_south.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_west_base.json", "panel/model/slab/{name}_west.json", texture=texture)
		elif "cut" in name and "sandstone" in name:
			copy_template(name, "vertical_slab/base/sandstone_double_base.json", "panel/model/slab/{name}_double.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_east_base.json", "panel/model/slab/{name}_east.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_north_base.json", "panel/model/slab/{name}_north.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_south_base.json", "panel/model/slab/{name}_south.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_west_base.json", "panel/model/slab/{name}_west.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
		elif "sandstone" in name:
			copy_template(name, "vertical_slab/base/sandstone_double_base.json", "panel/model/slab/{name}_double.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_east_base.json", "panel/model/slab/{name}_east.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_north_base.json", "panel/model/slab/{name}_north.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_south_base.json", "panel/model/slab/{name}_south.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
			copy_template(name, "vertical_slab/base/sandstone_west_base.json", "panel/model/slab/{name}_west.json", texture=texture, top=sandstone_top, bottom=sandstone_bottom)
		else:
			copy_template(name, "vertical_slab/base/vertical_slab_double_base.json", "panel/model/slab/{name}_double.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_east_base.json", "panel/model/slab/{name}_east.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_north_base.json", "panel/model/slab/{name}_north.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_south_base.json", "panel/model/slab/{name}_south.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_west_base.json", "panel/model/slab/{name}_west.json", texture=texture)

		print("\"buildersaddition:"+name+"_vertical_slab\",")
