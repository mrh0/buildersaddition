from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]

		copy_template_params("cabinet_recipe.json", 'cabinet/recipe/cabinet_'+wood+'.json', {"mod":"buildersaddition", "wood":wood})
		copy_template_params("cabinet_recipe.json", 'cabinet/recipe/cabinet_'+wood+'_quark.json', {"mod":"quark", "wood":wood})