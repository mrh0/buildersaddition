from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]

		extra = ''',
	"conditions": [ {
		"type": "forge:mod_loaded",
		"modid": "quark"
    } ]'''

		copy_template_params("support_bracket_recipe_left.json", 'support_bracket/recipe/support_bracket_'+wood+'_left.json', {"mod":"buildersaddition", "wood":wood, "extra": ""})
		copy_template_params("support_bracket_recipe_left.json", 'support_bracket/recipe/support_bracket_'+wood+'_left_quark.json', {"mod":"quark", "wood":wood, "extra": extra})

		copy_template_params("support_bracket_recipe_right.json", 'support_bracket/recipe/support_bracket_'+wood+'_right.json', {"mod":"buildersaddition", "wood":wood, "extra": ""})
		copy_template_params("support_bracket_recipe_right.json", 'support_bracket/recipe/support_bracket_'+wood+'_right_quark.json', {"mod":"quark", "wood":wood, "extra": extra})