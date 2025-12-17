from pathlib import Path
from PIL import Image, ImageDraw

WIDTH, HEIGHT = 176, 166
ALCHEMY_HEIGHT = 72
PLAYER_INV_X = 8
PLAYER_INV_Y = 86
HOTBAR_OFFSET_Y = 58
ROOT = Path(__file__).resolve().parents[1]
OUTPUT_PATH = ROOT / "src/main/resources/assets/witchercraft/textures/gui/alchemy_table.png"
OUTPUT_PATH.parent.mkdir(parents=True, exist_ok=True)

img = Image.new("RGBA", (WIDTH, HEIGHT), (0, 0, 0, 0))
draw = ImageDraw.Draw(img)

def gradient_band(y0, y1, start, end):
    span = max(1, y1 - y0 - 1)
    for idx, y in enumerate(range(y0, y1)):
        t = idx / span
        r = int(start[0] + (end[0] - start[0]) * t)
        g = int(start[1] + (end[1] - start[1]) * t)
        b = int(start[2] + (end[2] - start[2]) * t)
        draw.line((0, y, WIDTH, y), fill=(r, g, b, 255))


gradient_band(0, ALCHEMY_HEIGHT, (70, 52, 38), (38, 26, 18))
gradient_band(ALCHEMY_HEIGHT, HEIGHT, (70, 56, 38), (52, 42, 30))


draw.rectangle((0, 0, WIDTH - 1, HEIGHT - 1), outline=(22, 14, 9, 255), width=2)
draw.rectangle((3, 3, WIDTH - 4, HEIGHT - 4), outline=(110, 86, 62, 200), width=1)


header_bottom = 24
draw.rounded_rectangle((10, 6, WIDTH - 10, header_bottom), radius=7, fill=(48, 34, 24, 235), outline=(138, 108, 78, 255))
for stud_x in (18, WIDTH - 18):
    draw.ellipse((stud_x - 2, header_bottom - 5, stud_x + 2, header_bottom - 1), fill=(188, 152, 102, 255))


status_top = ALCHEMY_HEIGHT - 18
draw.rounded_rectangle((12, status_top, WIDTH - 12, status_top + 12), radius=5, fill=(30, 22, 16, 230), outline=(112, 86, 62, 255))


slots = {
    "base": (30, 28),
    "reagent": (62, 14),
    "catalyst": (94, 28),
    "output": (140, 22)
}

def ringed_slot(x, y, accent=False):
    base = (x - 11, y - 9, x + 27, y + 23)
    inner = (x - 8, y - 6, x + 24, y + 20)
    recess = (x - 2, y - 2, x + 18, y + 18)
    draw.rounded_rectangle(base, radius=6, fill=(28, 19, 12, 235))
    draw.rounded_rectangle(inner, radius=5, fill=(52, 36, 26, 255))
    outline = (204, 174, 120, 255) if accent else (102, 74, 52, 255)
    draw.rounded_rectangle(recess, radius=3, fill=(28, 20, 14, 255), outline=outline)
    draw.rectangle((x, y, x + 16, y + 16), fill=(34, 24, 18, 255))

for name, (sx, sy) in slots.items():
    ringed_slot(sx, sy, accent=(name == "output"))

# Progress recess decoration
progress_rect = (112 - 4, 22 - 4, 112 + 30 + 4, 22 + 22 + 4)
draw.rounded_rectangle(progress_rect, radius=6, outline=(136, 104, 76, 180))

# Rune etchings
for ox in range(16, WIDTH - 16, 28):
    draw.arc((ox, 42, ox + 16, 58), 210, 330, fill=(90, 68, 50, 130), width=1)

# Brass band separating alchemy panel from inventory
draw.rectangle((8, ALCHEMY_HEIGHT - 2, WIDTH - 8, ALCHEMY_HEIGHT + 2), fill=(96, 74, 52, 220))
draw.rectangle((8, ALCHEMY_HEIGHT + 4, WIDTH - 8, ALCHEMY_HEIGHT + 6), fill=(36, 24, 16, 180))

# Inventory felt background
inv_bg = (PLAYER_INV_X - 6, PLAYER_INV_Y - 10, WIDTH - (PLAYER_INV_X - 6), PLAYER_INV_Y + 18 * 3 + 14)
draw.rounded_rectangle(inv_bg, radius=8, fill=(44, 30, 20, 220), outline=(18, 12, 8, 200))
hotbar_top = PLAYER_INV_Y + HOTBAR_OFFSET_Y
hotbar_bg = (PLAYER_INV_X - 6, hotbar_top - 8, WIDTH - (PLAYER_INV_X - 6), hotbar_top + 22)
draw.rounded_rectangle(hotbar_bg, radius=8, fill=(40, 26, 18, 220), outline=(18, 10, 6, 160))

def draw_slot_grid(top, rows):
    slot_spacing = 18
    for row in range(rows):
        y = top + row * slot_spacing
        for col in range(9):
            x = PLAYER_INV_X + col * slot_spacing
            inset = (x - 1, y - 1, x + 17, y + 17)
            face = (x, y, x + 16, y + 16)
            draw.rounded_rectangle(inset, radius=3, fill=(30, 20, 14, 255))
            draw.rounded_rectangle(face, radius=2, fill=(68, 52, 38, 255), outline=(110, 84, 60, 180))

draw_slot_grid(PLAYER_INV_Y, 3)
draw_slot_grid(hotbar_top, 1)

# Soft vignette for depth
for inset in range(4):
    alpha = 90 - inset * 18
    draw.rectangle((inset, inset, WIDTH - inset - 1, HEIGHT - inset - 1), outline=(0, 0, 0, alpha))

img.save(OUTPUT_PATH)
print(f"Wrote {OUTPUT_PATH}")
