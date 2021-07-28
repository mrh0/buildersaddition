from jsongen import *


with open("list/countertop.txt", 'r') as creader:
	for cline in creader:
		cname = cline.split(",")[0]
		ctexture = cline.split(",")[1]
		ccraft = cline.split(",")[2].replace("\r", "").replace("\n", "")

		with open("list/wood.txt", 'r') as wreader:
			for wline in wreader:
				wname = wline.split(",")[0]
				wplanks = wline.split(",")[1]
				wstripped = wline.split(",")[2].replace("\r", "").replace("\n", "")

				name = "counter_" + wname + "_" + cname

				copy_template_params("counter/model.json", "counter/model/block/"+name+".json", {
					"stripped": "stripped_"+wstripped,
					"stone": ctexture,
					"planks": wplanks
				})

				copy_template_params("counter/recipe.json", "counter/recipe/"+name+".json", {
					"name": name,
					"stone": "minecraft:"+ccraft+"_slab",
					"planks": "minecraft:"+wplanks
				})

				copy_template_params("counter/blockstate.json", "counter/blockstate/"+name+".json", {"name": name})

				copy_template_params("item_parent_generic.json", "counter/model/item/"+name+".json", {"name":name, "type":"counter"})
				copy_template(name, "drop_table_generic.json", 'counter/loot_table/{name}.json')

				print("\"block.buildersaddition."+name+"\":\""+name.replace("_", " ").title()+"\",")