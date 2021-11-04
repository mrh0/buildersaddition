from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]
		planks = "minecraft:block/" + woods[1]
		stripped = "minecraft:block/stripped_" + woods[2]
		name = "chair_" + wood

		copy_template_params("chair/blockstate.json", 'chair/blockstate/{name}.json', {"name":name, "wood":wood, "type":"chair"})

		copy_template_params("block_parent_generic.json", 'chair/block/'+name+'.json', 
				{"name":name, "parent":"chair_base", "type":"chair", "particle":planks, "props":props([prop("planks", planks),prop("stripped", stripped)])})

		copy_template_params("chair/loot_table.json", "chair/loot_table/{name}.json", {"name":name, "wood":wood})

		copy_template_params("item_parent_generic.json", 'chair/item/{name}.json', {"name":name, "type":"chair"})

		copy_template_params("chair/recipe.json", "chair/recipe/"+name+".json", {
			"name": "chair_"+wood,
			"wood": wood,
			"panel": "buildersaddition:"+wood+"_vertical_slab"
		})

		copy_template_params("chair/recipe_quark.json", "chair/recipe/"+name+"_quark.json", {
			"name": "chair_"+wood,
			"wood": wood,
			"panel": "quark:"+wood+"_vertical_slab"
		})

		with open("list/color.txt", 'r') as reader:
			for color in reader:
				color = color.replace("\n", "").replace("\r", "")
				name = 'chair_'+wood+'_'+color
				copy_template_params("block_parent_generic.json", 'chair/block/'+name+'.json', 
				{"name":name, "parent":"chair_base", "type":"chair", "particle":planks, "props":props([prop("planks", planks),prop("stripped", stripped)])})