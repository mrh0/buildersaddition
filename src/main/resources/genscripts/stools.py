from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]
		planks = "minecraft:block/" + woods[1]
		stripped = "minecraft:block/stripped_" + woods[2]
		name = "stool_" + wood

		copy_template_params("stool_blockstate.json", 'stool/blockstate/{name}.json', {"name":name, "wood":wood, "type":"stool"})

		copy_template_params("block_parent_generic.json", 'stool/block/'+name+'.json', 
				{"name":name, "parent":"stool_base", "type":"stool", "particle":planks, "props":props([prop("planks", planks),prop("stripped", stripped)])})

		copy_template_params("stool_loot_table.json", "stool/loot_table/{name}.json", {"name":name, "wood":wood})

		copy_template_params("item_parent_generic.json", 'stool/item/{name}.json', {"name":name, "type":"stool"})

		#with open("list/color.txt", 'r') as reader:
		#	for color in reader:
		#		color = color.replace("\n", "").replace("\r", "")
		#		name = 'stool_'+wood+'_'+color
		#		copy_template_params("block_parent_generic.json", 'stool/block/'+name+'.json', 
		#		{"name":name, "parent":"stool_base", "type":"stool", "particle":planks, "props":props([prop("planks", planks),prop("stripped", stripped)])})