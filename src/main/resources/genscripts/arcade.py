from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]
		stripped = woods[2]
		name = "arcade_" + wood

		copy_template_params("arcade/state.json", 'arcade/state/{name}.json', {"name":name, "wood":wood})

		copy_template_params("drop_table_generic.json", "arcade/loot_table/{name}.json", {"name":name})

		copy_template_params("arcade/recipe.json", 'arcade/recipe/arcade_'+wood+'.json', {"wood":stripped, "name": name})

		copy_template_params("arcade/full.json", 'arcade/item/arcade_'+wood+'.json', {"wood":stripped})
		copy_template_params("arcade/upper.json", 'arcade/model/arcade_upper_'+wood+'.json', {"wood":stripped})
		copy_template_params("arcade/lower.json", 'arcade/model/arcade_lower_'+wood+'.json', {"wood":stripped})