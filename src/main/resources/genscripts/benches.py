from jsongen import *


with open("list/wood.txt", 'r') as reader:
	for woods in reader:

		woods = woods.replace("\n", "").replace("\r", "")
		woods = woods.split(",")
		wood = woods[0]

		copy_template_params("bench_recipe.json", 'bench/recipe/bench_'+wood+'.json', {"wood":wood})