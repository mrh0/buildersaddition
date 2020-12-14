from jsongen import *


with open("list/vertical_slab.txt", 'r') as reader:
	for line in reader:
		
		name = line.split(",")[0]
		texture = line.split(",")[1]
		craft = line.split(",")[2]

		copy_template(name, "vertical_slab_blockstate.json", "blockstate/{name}_vertical_slab.json")

		if "smooth_sandstone" in name or ("cut" in name and "sandstone" in name):
			copy_template(name, "vertical_slab/base/smooth_sandstone_double_base.json", "model/slab/{name}_vertical_slab_double.json", texture=texture)
			copy_template(name, "vertical_slab/base/smooth_sandstone_east_base.json", "model/slab/{name}_vertical_slab_east.json", texture=texture)
			copy_template(name, "vertical_slab/base/smooth_sandstone_north_base.json", "model/slab/{name}_vertical_slab_north.json", texture=texture)
			copy_template(name, "vertical_slab/base/smooth_sandstone_south_base.json", "model/slab/{name}_vertical_slab_south.json", texture=texture)
			copy_template(name, "vertical_slab/base/smooth_sandstone_west_base.json", "model/slab/{name}_vertical_slab_west.json", texture=texture)
		elif "sandstone" in name:
			copy_template(name, "vertical_slab/base/sandstone_double_base.json", "model/slab/{name}_vertical_slab_double.json", texture=texture)
			copy_template(name, "vertical_slab/base/sandstone_east_base.json", "model/slab/{name}_vertical_slab_east.json", texture=texture)
			copy_template(name, "vertical_slab/base/sandstone_north_base.json", "model/slab/{name}_vertical_slab_north.json", texture=texture)
			copy_template(name, "vertical_slab/base/sandstone_south_base.json", "model/slab/{name}_vertical_slab_south.json", texture=texture)
			copy_template(name, "vertical_slab/base/sandstone_west_base.json", "model/slab/{name}_vertical_slab_west.json", texture=texture)
		else:
			copy_template(name, "vertical_slab/base/vertical_slab_double_base.json", "model/slab/{name}_vertical_slab_double.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_east_base.json", "model/slab/{name}_vertical_slab_east.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_north_base.json", "model/slab/{name}_vertical_slab_north.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_south_base.json", "model/slab/{name}_vertical_slab_south.json", texture=texture)
			copy_template(name, "vertical_slab/base/vertical_slab_west_base.json", "model/slab/{name}_vertical_slab_west.json", texture=texture)
