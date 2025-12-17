"""
Generate Alchemy GUI style textures for Reputation and Quest screens.
"""

import struct
import zlib
from pathlib import Path

def create_png(width, height, pixels):
    """Create PNG from RGBA pixels"""
    def chunk(typ, data):
        return struct.pack('>I', len(data)) + typ + data + struct.pack('>I', zlib.crc32(typ + data) & 0xffffffff)
    
    png_data = b'\x89PNG\r\n\x1a\n'
    png_data += chunk(b'IHDR', struct.pack('>IIBBBBB', width, height, 8, 6, 0, 0, 0))
    
    raw = b''
    for y in range(height):
        raw += b'\x00'
        for x in range(width):
            r, g, b, a = pixels[y][x]
            raw += struct.pack('BBBB', r, g, b, a)
    
    png_data += chunk(b'IDAT', zlib.compress(raw, 9))
    png_data += chunk(b'IEND', b'')
    return png_data

# Create reputation GUI
width, height = 256, 256
pixels = [[(0, 0, 0, 0) for _ in range(width)] for _ in range(height)]

bg_dark = (30, 19, 12)
bg_medium = (42, 27, 17)
border_gold = (139, 105, 20)
accent_light = (229, 210, 173)

panel_width = 176
panel_height = 210

# Background
for y in range(panel_height):
    for x in range(panel_width):
        pixels[y][x] = bg_dark + (255,)

for y in range(8, panel_height - 8):
    for x in range(8, panel_width - 8):
        pixels[y][x] = bg_medium + (255,)

# Borders
for x in range(panel_width):
    for y in range(3):
        pixels[y][x] = border_gold + (255,)
        pixels[panel_height - 1 - y][x] = border_gold + (255,)

for y in range(panel_height):
    for x in range(3):
        pixels[y][x] = border_gold + (255,)
        pixels[y][panel_width - 1 - x] = border_gold + (255,)

# Title bar
for y in range(3, 23):
    for x in range(3, panel_width - 3):
        blend = int(bg_medium[0] + (accent_light[0] - bg_medium[0]) * 0.3)
        pixels[y][x] = (blend, blend - 10, blend - 20, 255)

# Faction slots
slot_start_y = 30
slot_height = 24
slot_spacing = 2

for i in range(7):
    slot_y = slot_start_y + i * (slot_height + slot_spacing)
    for y in range(slot_y, slot_y + slot_height):
        for x in range(12, panel_width - 12):
            if y < panel_height:
                gradient = 1.0 - ((y - slot_y) / slot_height) * 0.1
                r = int(bg_medium[0] * gradient)
                g = int(bg_medium[1] * gradient)
                b = int(bg_medium[2] * gradient)
                pixels[y][x] = (r, g, b, 255)
    
    for y in range(slot_y, slot_y + slot_height):
        for x in range(12, 16):
            if y < panel_height:
                pixels[y][x] = border_gold + (255,)
    
    bar_y = slot_y + slot_height - 10
    bar_x_start = 20
    bar_width = panel_width - 40
    
    for y in range(bar_y, bar_y + 6):
        for x in range(bar_x_start, bar_x_start + bar_width):
            if y < panel_height and x < panel_width:
                pixels[y][x] = (20, 15, 10, 255)

# Global reputation area
global_y = panel_height - 30
for y in range(global_y, panel_height - 5):
    for x in range(8, panel_width - 8):
        if y < panel_height:
            blend = int(bg_dark[0] + (accent_light[0] - bg_dark[0]) * 0.2)
            pixels[y][x] = (blend, blend - 15, blend - 25, 255)

output_dir = Path("src/main/resources/assets/witchercraft/textures/gui")
output_dir.mkdir(parents=True, exist_ok=True)

reputation_path = output_dir / "reputation.png"
with open(reputation_path, 'wb') as f:
    f.write(create_png(width, height, pixels))
print(f"✅ Created {reputation_path}")

# Create quest GUI  
pixels = [[(0, 0, 0, 0) for _ in range(width)] for _ in range(height)]

panel_width = 256
panel_height = 256

for y in range(panel_height):
    for x in range(panel_width):
        pixels[y][x] = bg_dark + (255,)

for y in range(8, panel_height - 8):
    for x in range(8, panel_width - 8):
        pixels[y][x] = bg_medium + (255,)

for x in range(panel_width):
    for y in range(3):
        pixels[y][x] = border_gold + (255,)
        pixels[panel_height - 1 - y][x] = border_gold + (255,)

for y in range(panel_height):
    for x in range(3):
        pixels[y][x] = border_gold + (255,)
        pixels[y][panel_width - 1 - x] = border_gold + (255,)

for y in range(3, 25):
    for x in range(3, panel_width - 3):
        blend = int(bg_medium[0] + (accent_light[0] - bg_medium[0]) * 0.3)
        pixels[y][x] = (blend, blend - 10, blend - 20, 255)

list_width = int(panel_width * 0.6)
list_x_end = list_width

for y in range(25, panel_height - 5):
    for x in range(list_x_end - 2, list_x_end + 2):
        pixels[y][x] = border_gold + (255,)

entry_height = 20
entry_spacing = 2
entry_start_y = 30

for i in range(10):
    entry_y = entry_start_y + i * (entry_height + entry_spacing)
    for y in range(entry_y, entry_y + entry_height):
        for x in range(12, list_x_end - 8):
            if y < panel_height:
                gradient = 1.0 - ((y - entry_y) / entry_height) * 0.08
                r = int(bg_medium[0] * gradient)
                g = int(bg_medium[1] * gradient)
                b = int(bg_medium[2] * gradient)
                pixels[y][x] = (r, g, b, 255)
    
    for y in range(entry_y, entry_y + entry_height):
        for x in range(12, 15):
            if y < panel_height:
                pixels[y][x] = border_gold + (255,)

detail_start_x = list_x_end + 5

for y in range(30, 55):
    for x in range(detail_start_x, panel_width - 12):
        blend = int(bg_dark[0] + (accent_light[0] - bg_dark[0]) * 0.25)
        pixels[y][x] = (blend, blend - 12, blend - 22, 255)

for y in range(60, 140):
    for x in range(detail_start_x, panel_width - 12):
        pixels[y][x] = bg_medium + (255,)

progress_y = 145
for y in range(progress_y, progress_y + 12):
    for x in range(detail_start_x + 5, panel_width - 17):
        pixels[y][x] = (20, 15, 10, 255)

for y in range(165, panel_height - 12):
    for x in range(detail_start_x, panel_width - 12):
        blend = int(bg_dark[0] + (accent_light[0] - bg_dark[0]) * 0.15)
        pixels[y][x] = (blend, blend - 18, blend - 28, 255)

quest_path = output_dir / "quest.png"
with open(quest_path, 'wb') as f:
    f.write(create_png(width, height, pixels))
print(f"✅ Created {quest_path}")

print("\n✅ GUI textures generated!")
