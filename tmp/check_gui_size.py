from PIL import Image
from pathlib import Path
path = Path('src/main/resources/assets/witchercraft/textures/gui/alchemy_table.png')
print(path, Image.open(path).size)
