from jsongen import *


with open("list/pillars.txt", 'r') as reader:
	for line in reader:
		name = line.split(",")[0]
		top = line.split(",")[1]
		side = line.split(",")[2]
		platform = line.split(",")[3].replace("\r", "").replace("\n", "")
		fname = "cut_"+name+"_pillar"

		copy_template(fname, "pillar/blockstate.json", "pillar/blockstate/{name}.json")
		copy_template_params("item_parent_generic.json", "pillar/model/item/"+fname+".json", {"name":fname+"_both", "type":"pillar"})

		copy_template_params("pillar/model_both.json", "pillar/model/block/"+fname+"_both.json", {
			"side": side,
			"top": top,
			"platform": platform
		})
		copy_template_params("pillar/model_none.json", "pillar/model/block/"+fname+".json", {
			"side": side,
			"top": top,
			"platform": platform
		})
		copy_template_params("pillar/model_top.json", "pillar/model/block/"+fname+"_top.json", {
			"side": side,
			"top": top,
			"platform": platform
		})
		copy_template_params("pillar/model_bottom.json", "pillar/model/block/"+fname+"_bottom.json", {
			"side": side,
			"top": top,
			"platform": platform
		})
		copy_template(fname, "drop_table_generic.json", 'pillar/loot_table/{name}.json')

		print("\"block.buildersaddition."+fname+"\":\""+fname.replace("_", " ").title()+"\",")

with open("list/pillar_recipes.txt", 'r') as reader:
	for line in reader:
		fext = line.split(",")[0]
		name = line.split(",")[1]
		craft = line.split(",")[2].replace("\r", "").replace("\n", "")

		fname = "cut_"+name+"_pillar"
		if fext == "#":
			fext = ""

		copy_template_params("stonecutting_recipe.json", "pillar/recipe/"+fname+"_stonecutting"+fext+".json", {
			"item": "minecraft:"+craft,
			"result": "buildersaddition:"+fname,
			"count": "1"
		})