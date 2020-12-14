from jsongen import *

with open("list/color.txt", 'r') as reader:
	for color in reader:
		color = color.replace("\n", "").replace("\r", "")
		name = "pillow_" + color

		tex = 'minecraft:block/{color}_wool'.format(color=color)

		copy_template_params("blockstate_generic.json", 'pillow/blockstate/{name}.json', {"name":name, "type":"pillow"})
		copy_template_params("block_parent_generic.json", 'pillow/block/{name}.json', 
		{"name":name, "parent":"pillow_base", "type":"pillow", "particle":tex, "props":props([prop("wool", tex)])})

		copy_template_params("item_parent_generic.json", 'pillow/item/{name}.json', {"name":name, "type":"pillow"})
		copy_template(name, "drop_table_generic.json", 'pillow/loot_table/{name}.json')

		#print('{\n\"when\": { \"pillow\": \"'+color+'\" },\n\"apply\": { \"model\": \"buildersaddition:block/stool/stool_pillow_'+color+'\" }\n},')

		#s = """
		#{
		#	"name": "buildersaddition:stool_%wood%",
		#	"rolls": 1,
		#	"entries": [
		#		{
		#			"type": "minecraft:item",
		#			"name": "buildersaddition:pillow_%color%"
		#		}
		#	],
		#	"conditions": [
		#			{
		#			"condition": "minecraft:block_state_property",
		#			"block":"buildersaddition:stool_%wood%",
		#			"properties": {
		#				"pillow":"%color%"
		#			}
		#		}
		#	]
		#},
		#"""
		#print(s.replace("%color%", color))

		copy_template_params("pillow_recipe_1.json", "pillow/recipe/{name}.json", {"name":name, "color":color})
		copy_template_params("pillow_recipe_2.json", "pillow/recipe/{name}_mirror.json", {"name":name, "color":color})

		copy_template_params("block_parent_generic.json", 'stool/block/stool_pillow_'+color+'.json', 
		{"name":name, "parent":"stool_pillow_base", "type":"stool", "particle":tex, "props":props([prop("wool", tex)])})
