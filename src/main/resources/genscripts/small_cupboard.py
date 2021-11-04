from jsongen import *



with open("list/wood.txt", 'r') as wreader:
	for wline in wreader:
		wname = wline.split(",")[0]
		wplanks = wline.split(",")[1]
		wstripped = wline.split(",")[2].replace("\r", "").replace("\n", "")

		name = "small_cupboard_" + wname

		copy_template_params("block_parent_generic.json", 'small_cupboard/block/'+name+'.json', 
				{"name":name, "parent":"small_cupboard_base", "type":"small_cupboard", "particle":"minecraft:block/"+wplanks, "props":props([prop("planks", "minecraft:block/"+wplanks),prop("handle", "minecraft:block/polished_andesite"),prop("stripped", "minecraft:block/stripped_"+wstripped)])})

		copy_template_params("block_parent_generic.json", 'small_cupboard/block/'+name+'_mirror.json', 
				{"name":name, "parent":"small_cupboard_base_mirror", "type":"small_cupboard", "particle":"minecraft:block/"+wplanks, "props":props([prop("planks", "minecraft:block/"+wplanks),prop("handle", "minecraft:block/polished_andesite"),prop("stripped", "minecraft:block/stripped_"+wstripped)])})

		copy_template_params("small_cupboard/recipe.json", "small_cupboard/recipe/"+name+"_right.json", {
			"name": "small_cupboard_"+wname,
			"panel": "buildersaddition:"+wname+"_vertical_slab"
		})

		copy_template_params("small_cupboard/recipe_mirror.json", "small_cupboard/recipe/"+name+"_left.json", {
			"name": "small_cupboard_"+wname,
			"panel": "buildersaddition:"+wname+"_vertical_slab"
		})

		copy_template_params("small_cupboard/recipe_quark.json", "small_cupboard/recipe/"+name+"_right_quark.json", {
			"name": "small_cupboard_"+wname,
			"panel": "quark:"+wname+"_vertical_slab"
		})

		copy_template_params("small_cupboard/recipe_mirror_quark.json", "small_cupboard/recipe/"+name+"_left_quark.json", {
			"name": "small_cupboard_"+wname,
			"panel": "quark:"+wname+"_vertical_slab"
		})

		copy_template_params("small_cupboard/blockstate.json", "small_cupboard/blockstate/"+name+".json", {"name": name})

		copy_template_params("item_parent_generic.json", "small_cupboard/model/item/"+name+".json", {"name":name, "type":"small_cupboard"})
		copy_template(name, "drop_table_generic.json", 'small_cupboard/loot_table/{name}.json')

		print("\"block.buildersaddition."+name+"\":\"Small "+wname+" Cupboard\",")