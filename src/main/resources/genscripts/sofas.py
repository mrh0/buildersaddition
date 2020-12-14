
from jsongen import *

with open("list/color.txt", 'r') as reader:
	for color in reader:
		color = color.replace("\n", "").replace("\r", "")
		name = "sofa_" + color

		copy_template_params("sofa_recipe.json", "sofa/recipe/{name}.json", {"name":name, "color":color})