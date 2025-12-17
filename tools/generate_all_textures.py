#!/usr/bin/env python3
"""Generate all WitcherCraft textures for items and mobs.

This script creates 16×16 item textures and 128×128 mob textures with pixel-art style.
"""
from __future__ import annotations

import struct
import zlib
from pathlib import Path
from typing import Tuple

ROOT = Path(__file__).resolve().parents[1]
TEXTURE_ROOT = ROOT / "src/main/resources/assets/witchercraft/textures"

Color = Tuple[int, int, int, int]
RGB = Tuple[int, int, int]

# Color palette
COLORS = {
    # Metals
    'silver': (192, 192, 220),
    'steel': (160, 160, 170),
    'dark_steel': (100, 100, 110),
    'bronze': (205, 127, 50),
    'gold': (255, 215, 0),
    'copper': (184, 115, 51),
    
    # Woods
    'wood': (139, 90, 43),
    'dark_wood': (90, 60, 30),
    'light_wood': (210, 180, 140),
    
    # Gems and crystals
    'ruby': (224, 17, 95),
    'emerald': (80, 200, 120),
    'sapphire': (15, 82, 186),
    'amethyst': (153, 102, 204),
    'diamond': (185, 242, 255),
    
    # Liquids
    'red_potion': (220, 20, 60),
    'blue_potion': (30, 144, 255),
    'green_potion': (50, 205, 50),
    'yellow_potion': (255, 215, 0),
    'purple_potion': (138, 43, 226),
    
    # Monster parts
    'bone': (240, 234, 214),
    'flesh': (180, 100, 100),
    'dark_flesh': (120, 60, 60),
    
    # Natural
    'green': (34, 139, 34),
    'dark_green': (0, 100, 0),
    'brown': (139, 69, 19),
    'orange': (255, 140, 0),
    'white': (255, 255, 255),
    'black': (20, 20, 20),
    'gray': (128, 128, 128),
    'light_gray': (192, 192, 192),
    
    # Special effects
    'glow_blue': (100, 200, 255),
    'glow_green': (100, 255, 100),
    'glow_purple': (200, 100, 255),
    'fire': (255, 100, 0),
}

def clamp(value: float) -> int:
    return max(0, min(255, int(round(value))))

def rgba(rgb: RGB, alpha: int = 255) -> Color:
    return rgb[0], rgb[1], rgb[2], alpha

def adjust(rgb: RGB, delta: int) -> RGB:
    return (clamp(rgb[0] + delta), clamp(rgb[1] + delta), clamp(rgb[2] + delta))

class TextureCanvas:
    def __init__(self, width: int, height: int):
        self.width = width
        self.height = height
        self.pixels = [rgba((0, 0, 0), 0) for _ in range(width * height)]
    
    def set(self, x: int, y: int, color: Color):
        if 0 <= x < self.width and 0 <= y < self.height:
            self.pixels[y * self.width + x] = color
    
    def fill_rect(self, x: int, y: int, w: int, h: int, color: Color):
        for dy in range(h):
            for dx in range(w):
                self.set(x + dx, y + dy, color)
    
    def outline_rect(self, x: int, y: int, w: int, h: int, color: Color):
        for i in range(w):
            self.set(x + i, y, color)
            self.set(x + i, y + h - 1, color)
        for i in range(h):
            self.set(x, y + i, color)
            self.set(x + w - 1, y + i, color)
    
    def save_png(self, path: Path):
        path.parent.mkdir(parents=True, exist_ok=True)
        
        def chunk(name: bytes, data: bytes) -> bytes:
            return struct.pack('>I', len(data)) + name + data + struct.pack('>I', zlib.crc32(name + data))
        
        raw = bytearray()
        for y in range(self.height):
            raw.append(0)
            for x in range(self.width):
                pixel = self.pixels[y * self.width + x]
                if len(pixel) != 4:
                    print(f"ERROR at ({x},{y}): pixel = {pixel}, type = {type(pixel)}")
                    raise ValueError(f"Pixel at ({x},{y}) has {len(pixel)} values instead of 4: {pixel}")
                r, g, b, a = pixel
                raw.extend([r, g, b, a])
        
        ihdr = struct.pack('>IIBBBBB', self.width, self.height, 8, 6, 0, 0, 0)
        idat = zlib.compress(bytes(raw), 9)
        
        with path.open('wb') as f:
            f.write(b'\x89PNG\r\n\x1a\n')
            f.write(chunk(b'IHDR', ihdr))
            f.write(chunk(b'IDAT', idat))
            f.write(chunk(b'IEND', b''))
        
        print(f"Created: {path.relative_to(ROOT)}")

# ===== ITEM TEXTURE GENERATORS =====

def draw_potion(canvas: TextureCanvas, color: RGB):
    """Draw a potion bottle with liquid color"""
    glass = COLORS['light_gray']
    dark = COLORS['dark_steel']
    
    # Bottle shape
    canvas.fill_rect(6, 4, 4, 2, rgba(dark))  # Cork
    canvas.fill_rect(5, 6, 6, 8, rgba(glass, 200))  # Glass bottle
    canvas.outline_rect(5, 6, 6, 8, rgba(dark))
    
    # Liquid
    canvas.fill_rect(6, 9, 4, 4, rgba(color, 220))
    
    # Highlight
    canvas.set(6, 7, rgba(COLORS['white'], 180))
    canvas.set(7, 7, rgba(COLORS['white'], 180))

def draw_sword(canvas: TextureCanvas, blade_color: RGB, handle_color: RGB):
    """Draw a sword"""
    dark = adjust(blade_color, -40)
    
    # Blade
    canvas.fill_rect(7, 1, 2, 10, rgba(blade_color))
    canvas.fill_rect(7, 1, 1, 10, rgba(adjust(blade_color, 30)))  # Highlight
    canvas.outline_rect(7, 1, 2, 10, rgba(dark))
    
    # Guard
    canvas.fill_rect(5, 10, 6, 2, rgba(handle_color))
    
    # Handle
    canvas.fill_rect(7, 12, 2, 3, rgba(adjust(handle_color, -20)))

def draw_ingot(canvas: TextureCanvas, color: RGB):
    """Draw a metal ingot"""
    dark = adjust(color, -40)
    light = adjust(color, 30)
    
    canvas.fill_rect(3, 5, 10, 6, rgba(color))
    canvas.fill_rect(3, 5, 10, 1, rgba(light))  # Top highlight
    canvas.fill_rect(3, 10, 10, 1, rgba(dark))  # Bottom shadow
    canvas.outline_rect(3, 5, 10, 6, rgba(dark))

def draw_gem(canvas: TextureCanvas, color: RGB):
    """Draw a gemstone"""
    dark = adjust(color, -60)
    
    # Diamond shape
    points = [(8, 4), (5, 8), (8, 12), (11, 8)]
    for y in range(4, 13):
        for x in range(4, 13):
            if 5 <= x <= 11 and abs(x - 8) + abs(y - 8) <= 4:
                if x < 8 and y < 8:
                    canvas.set(x, y, rgba(adjust(color, 40)))
                else:
                    canvas.set(x, y, rgba(color))
    
    canvas.outline_rect(7, 7, 3, 3, rgba(dark))

def draw_plant(canvas: TextureCanvas):
    """Draw celandine herb"""
    green = COLORS['green']
    dark_green = COLORS['dark_green']
    yellow = COLORS['yellow_potion']
    
    # Stem
    canvas.fill_rect(7, 8, 2, 6, rgba(dark_green))
    
    # Leaves
    canvas.fill_rect(5, 10, 3, 2, rgba(green))
    canvas.fill_rect(10, 11, 3, 2, rgba(green))
    
    # Flowers
    canvas.set(7, 6, rgba(yellow))
    canvas.set(8, 5, rgba(yellow))
    canvas.set(9, 6, rgba(yellow))
    canvas.set(6, 7, rgba(yellow))
    canvas.set(10, 7, rgba(yellow))
    
    return canvas

def draw_brain(canvas: TextureCanvas):
    """Draw drowner brain"""
    flesh = COLORS['flesh']
    dark = COLORS['dark_flesh']
    
    # Brain shape
    canvas.fill_rect(5, 6, 6, 5, rgba(flesh))
    canvas.fill_rect(6, 5, 4, 1, rgba(flesh))
    canvas.fill_rect(6, 11, 4, 1, rgba(flesh))
    
    # Wrinkles
    canvas.set(6, 7, rgba(dark))
    canvas.set(8, 8, rgba(dark))
    canvas.set(9, 7, rgba(dark))
    canvas.set(7, 9, rgba(dark))
    
    return canvas

def draw_monster_part(canvas: TextureCanvas, color: RGB, secondary: RGB):
    """Generic monster part"""
    canvas.fill_rect(4, 5, 8, 7, rgba(color))
    canvas.fill_rect(5, 6, 6, 5, rgba(secondary))
    canvas.outline_rect(4, 5, 8, 7, rgba(adjust(color, -50)))

def draw_vial(canvas: TextureCanvas, liquid_color: RGB):
    """Draw an alchemy vial"""
    glass = COLORS['light_gray']
    dark = COLORS['dark_steel']
    
    canvas.fill_rect(6, 5, 4, 8, rgba(glass, 200))
    canvas.fill_rect(6, 9, 4, 3, rgba(liquid_color, 220))
    canvas.outline_rect(6, 5, 4, 8, rgba(dark))
    canvas.fill_rect(7, 4, 2, 1, rgba(dark))  # Cork

def draw_tool(canvas: TextureCanvas, head_color: RGB, handle_color: RGB):
    """Draw a tool (hammer/chisel)"""
    # Handle
    canvas.fill_rect(3, 10, 10, 2, rgba(handle_color))
    # Head
    canvas.fill_rect(9, 7, 4, 5, rgba(head_color))
    canvas.outline_rect(9, 7, 4, 5, rgba(adjust(head_color, -40)))

def draw_rune(canvas: TextureCanvas, color: RGB):
    """Draw a runestone"""
    stone = COLORS['gray']
    
    canvas.fill_rect(5, 4, 6, 8, rgba(stone))
    canvas.outline_rect(5, 4, 6, 8, rgba(COLORS['dark_steel']))
    
    # Rune symbol
    canvas.set(7, 6, rgba(color))
    canvas.set(8, 6, rgba(color))
    canvas.set(7, 7, rgba(color))
    canvas.set(8, 8, rgba(color))
    canvas.set(7, 9, rgba(color))
    canvas.set(8, 9, rgba(color))

def draw_powder(canvas: TextureCanvas, color: RGB):
    """Draw powder in a pile"""
    for y in range(10, 14):
        width = 14 - y
        x_start = 8 - width // 2
        canvas.fill_rect(x_start, y, width, 1, rgba(color))

def draw_armor_piece(canvas: TextureCanvas, color: RGB):
    """Draw an armor component"""
    dark = adjust(color, -40)
    light = adjust(color, 30)
    
    # Chest plate shape
    canvas.fill_rect(5, 4, 6, 8, rgba(color))
    canvas.fill_rect(5, 4, 2, 8, rgba(light))  # Left highlight
    canvas.fill_rect(9, 4, 2, 8, rgba(dark))   # Right shadow
    canvas.outline_rect(5, 4, 6, 8, rgba(COLORS['dark_steel']))

def draw_wood(canvas: TextureCanvas, color: RGB):
    """Draw a wooden plank"""
    dark = adjust(color, -30)
    
    canvas.fill_rect(3, 6, 10, 4, rgba(color))
    # Wood grain
    canvas.fill_rect(4, 6, 1, 4, rgba(dark))
    canvas.fill_rect(7, 6, 1, 4, rgba(dark))
    canvas.fill_rect(10, 6, 1, 4, rgba(dark))
    canvas.outline_rect(3, 6, 10, 4, rgba(dark))

def draw_crystal(canvas: TextureCanvas, color: RGB):
    """Draw a magical crystal"""
    dark = adjust(color, -60)
    light = adjust(color, 60)
    
    # Crystal shape
    canvas.fill_rect(7, 3, 2, 10, rgba(color))
    canvas.set(7, 2, rgba(color))
    canvas.set(8, 2, rgba(color))
    canvas.fill_rect(6, 4, 1, 8, rgba(light))  # Highlight
    canvas.fill_rect(9, 5, 1, 7, rgba(dark))   # Shadow
    canvas.outline_rect(7, 2, 2, 11, rgba(dark))

def draw_feather(canvas: TextureCanvas, color: RGB):
    """Draw a feather"""
    stem = COLORS['brown']
    
    # Stem
    canvas.fill_rect(7, 4, 2, 10, rgba(stem))
    
    # Feather barbs
    for y in range(4, 12):
        offset = (12 - y) // 2
        canvas.set(7 - offset, y, rgba(color))
        canvas.set(8 + offset, y, rgba(color))

def draw_eye(canvas: TextureCanvas):
    """Draw a monster eye"""
    white = COLORS['white']
    iris = COLORS['emerald']
    pupil = COLORS['black']
    
    # Eye white
    canvas.fill_rect(5, 6, 6, 5, rgba(white))
    canvas.outline_rect(5, 6, 6, 5, rgba(COLORS['dark_flesh']))
    
    # Iris
    canvas.fill_rect(7, 7, 3, 3, rgba(iris))
    
    # Pupil
    canvas.set(8, 8, rgba(pupil))
    
    return canvas

def draw_liquid_jar(canvas: TextureCanvas, color: RGB):
    """Draw a jar of liquid/mist"""
    glass = COLORS['light_gray']
    
    canvas.fill_rect(5, 5, 6, 7, rgba(glass, 200))
    canvas.fill_rect(6, 7, 4, 4, rgba(color, 180))
    canvas.outline_rect(5, 5, 6, 7, rgba(COLORS['dark_steel']))
    canvas.fill_rect(6, 4, 4, 1, rgba(COLORS['dark_steel']))  # Lid

def draw_spawn_egg(canvas: TextureCanvas, primary: RGB, secondary: RGB):
    """Draw a spawn egg with dots"""
    # Egg shape
    for y in range(4, 13):
        for x in range(5, 12):
            dist = ((x - 8) ** 2) / 9 + ((y - 8) ** 2) / 16
            if dist <= 1:
                canvas.set(x, y, rgba(primary))
    
    # Spots
    canvas.set(7, 6, rgba(secondary))
    canvas.set(9, 7, rgba(secondary))
    canvas.set(6, 9, rgba(secondary))
    canvas.set(10, 10, rgba(secondary))

# ===== MOB TEXTURE GENERATOR =====

def draw_humanoid(canvas: TextureCanvas, skin_color: RGB, armor_color: RGB, detail_color: RGB):
    """Draw a humanoid mob texture (128x128)"""
    w, h = 128, 128
    
    # Helper to draw armor plating
    def armor_segment(x, y, w, h):
        canvas.fill_rect(x, y, w, h, rgba(armor_color))
        canvas.fill_rect(x, y, w, 1, rgba(adjust(armor_color, 40)))  # Highlight
        canvas.fill_rect(x, y + h - 1, w, 1, rgba(adjust(armor_color, -40)))  # Shadow
    
    # Head (8x8x8)
    canvas.fill_rect(8, 8, 8, 8, rgba(skin_color))
    canvas.fill_rect(16, 8, 8, 8, rgba(skin_color))
    canvas.fill_rect(8, 0, 8, 8, rgba(skin_color))
    canvas.fill_rect(16, 0, 8, 8, rgba(skin_color))
    
    # Face details
    canvas.fill_rect(10, 10, 2, 1, rgba(COLORS['black']))  # Left eye
    canvas.fill_rect(14, 10, 2, 1, rgba(COLORS['black']))  # Right eye
    canvas.fill_rect(11, 13, 4, 1, rgba(detail_color))     # Mouth
    
    # Body (8x12x4)
    armor_segment(20, 20, 8, 12)
    armor_segment(28, 20, 8, 12)
    armor_segment(20, 16, 8, 4)
    armor_segment(28, 16, 8, 4)
    
    # Arms (left and right, 4x12x4)
    armor_segment(44, 20, 4, 12)  # Right arm front
    armor_segment(48, 20, 4, 12)  # Right arm back
    
    armor_segment(36, 52, 4, 12)  # Left arm front
    armor_segment(40, 52, 4, 12)  # Left arm back
    
    # Legs (left and right, 4x12x4)
    canvas.fill_rect(4, 20, 4, 12, rgba(adjust(armor_color, -20)))   # Right leg
    canvas.fill_rect(8, 20, 4, 12, rgba(adjust(armor_color, -20)))   # Right leg back
    
    canvas.fill_rect(20, 52, 4, 12, rgba(adjust(armor_color, -20)))  # Left leg
    canvas.fill_rect(24, 52, 4, 12, rgba(adjust(armor_color, -20)))  # Left leg back
    
    # Helmet/detail overlay
    canvas.fill_rect(40, 8, 8, 8, rgba(detail_color, 200))
    canvas.fill_rect(48, 8, 8, 8, rgba(detail_color, 200))

# ===== MAIN GENERATION =====

def generate_all():
    """Generate all textures"""
    item_path = TEXTURE_ROOT / "item"
    entity_path = TEXTURE_ROOT / "entity" / "humanoids"
    
    # Potions
    items = {
        'swallow_potion': lambda c: draw_potion(c, COLORS['red_potion']),
        'cat_potion': lambda c: draw_potion(c, COLORS['green_potion']),
        'thunderbolt_potion': lambda c: draw_potion(c, COLORS['yellow_potion']),
        'white_raffard_decoction': lambda c: draw_potion(c, COLORS['white']),
        'tawny_owl_potion': lambda c: draw_potion(c, COLORS['blue_potion']),
        
        # Herbs
        'celandine': draw_plant,
        
        # Pommels
        'dimeritium_pommel': lambda c: draw_ingot(c, COLORS['dark_steel']),
        'meteorite_iron_pommel': lambda c: draw_ingot(c, COLORS['steel']),
        'runestone_encrusted_pommel': lambda c: draw_gem(c, COLORS['amethyst']),
        
        # Grips
        'leather_wrapped_grip': lambda c: draw_wood(c, COLORS['brown']),
        'wyvern_hide_grip': lambda c: draw_wood(c, COLORS['dark_green']),
        'elder_resin_bound_grip': lambda c: draw_wood(c, COLORS['dark_wood']),
        
        # Guards
        'cross_steel_guard': lambda c: draw_armor_piece(c, COLORS['steel']),
        'temerian_forged_guard': lambda c: draw_armor_piece(c, COLORS['bronze']),
        'runic_wolf_guard': lambda c: draw_armor_piece(c, COLORS['silver']),
        
        # Blade materials
        'witcher_steel_ingot': lambda c: draw_ingot(c, COLORS['steel']),
        'meteorite_steel_ingot': lambda c: draw_ingot(c, COLORS['dark_steel']),
        'purified_silver_ingot': lambda c: draw_ingot(c, COLORS['silver']),
        
        # Blade cores
        'dimeritium_core_rod': lambda c: draw_crystal(c, COLORS['dark_steel']),
        'griffin_school_core': lambda c: draw_crystal(c, COLORS['glow_blue']),
        'bear_school_core': lambda c: draw_crystal(c, COLORS['brown']),
        
        # Forge
        'forging_bill': lambda c: draw_armor_piece(c, COLORS['white']),
        'forged_sword_output': lambda c: draw_sword(c, COLORS['silver'], COLORS['wood']),
        
        # Monster parts
        'alghoul_bone_marrow': lambda c: draw_monster_part(c, COLORS['bone'], COLORS['flesh']),
        'leshen_resin_core': lambda c: draw_monster_part(c, COLORS['wood'], COLORS['green']),
        'griffin_feathers': lambda c: draw_feather(c, COLORS['gold']),
        'basilisk_venom_gland': lambda c: draw_vial(c, COLORS['green_potion']),
        'noonwraith_essence_threads': lambda c: draw_monster_part(c, COLORS['white'], COLORS['glow_blue']),
        'fiend_eye_lens': lambda c: draw_eye(c),
        'foglet_condensed_mist': lambda c: draw_liquid_jar(c, COLORS['gray']),
        'gargoyle_heartstone': lambda c: draw_gem(c, COLORS['ruby']),
        
        # Alchemy reagents
        'white_gull': lambda c: draw_vial(c, COLORS['white']),
        'dwarven_spirit': lambda c: draw_vial(c, COLORS['orange']),
        'aether_extract': lambda c: draw_vial(c, COLORS['glow_purple']),
        
        # Upgrade components
        'runestone_veles': lambda c: draw_rune(c, COLORS['glow_blue']),
        'runestone_perun': lambda c: draw_rune(c, COLORS['yellow_potion']),
        'witcher_alloy_plate': lambda c: draw_armor_piece(c, COLORS['steel']),
        'runestone_uroboros': lambda c: draw_rune(c, COLORS['glow_green']),
        'runestone_aerondight': lambda c: draw_rune(c, COLORS['glow_blue']),
        'runestone_drakon': lambda c: draw_rune(c, COLORS['fire']),
        
        # Metals
        'elder_silver_plate': lambda c: draw_ingot(c, COLORS['silver']),
        'monster_trophy': lambda c: draw_monster_part(c, COLORS['bone'], COLORS['brown']),
        'arachas_hardened_chitin_plate': lambda c: draw_armor_piece(c, COLORS['dark_green']),
        'moonsteel_ingot': lambda c: draw_ingot(c, COLORS['glow_blue']),
        
        # Magical composites
        'etherglass_shard': lambda c: draw_crystal(c, COLORS['glow_blue']),
        'spiritbound_alloy': lambda c: draw_ingot(c, COLORS['glow_purple']),
        'volatile_glowstone_core': lambda c: draw_crystal(c, COLORS['fire']),
        
        # Woods
        'ancient_yew_timber': lambda c: draw_wood(c, COLORS['wood']),
        'petrified_spriggan_root': lambda c: draw_wood(c, COLORS['gray']),
        'wolfsbane_treated_oak': lambda c: draw_wood(c, COLORS['dark_wood']),
        
        # Witcher reagents
        'stammelfords_dust': lambda c: draw_powder(c, COLORS['glow_blue']),
        'mandrake_distillate': lambda c: draw_vial(c, COLORS['purple_potion']),
        'rebis_crystal': lambda c: draw_crystal(c, COLORS['amethyst']),
        'vitriol_resin': lambda c: draw_vial(c, COLORS['green_potion']),
        
        # Tools
        'precision_tempering_hammer': lambda c: draw_tool(c, COLORS['steel'], COLORS['wood']),
        'runic_engraving_chisel': lambda c: draw_tool(c, COLORS['silver'], COLORS['dark_wood']),
        'cold_quench_flask': lambda c: draw_vial(c, COLORS['blue_potion']),
        
        # Armor components
        'laminated_witcher_leather': lambda c: draw_armor_piece(c, COLORS['brown']),
        'scaled_griffin_padding': lambda c: draw_armor_piece(c, COLORS['gold']),
        'chromatic_chain_links': lambda c: draw_armor_piece(c, COLORS['silver']),
        
        # Powders
        'bonegrind_powder': lambda c: draw_powder(c, COLORS['bone']),
        'spectral_ash': lambda c: draw_powder(c, COLORS['gray']),
        'fulminating_ember_dust': lambda c: draw_powder(c, COLORS['fire']),
        
        # Infusions
        'igni_fireblood_infusion': lambda c: draw_vial(c, COLORS['fire']),
        'yrden_geomantic_infusion': lambda c: draw_vial(c, COLORS['purple_potion']),
        'quen_auric_coating': lambda c: draw_vial(c, COLORS['gold']),
        
        # Relic components
        'shattered_relic_sigil': lambda c: draw_gem(c, COLORS['glow_purple']),
        'elder_blood_trace_sample': lambda c: draw_vial(c, COLORS['ruby']),
        'ancient_draconid_bone': lambda c: draw_ingot(c, COLORS['bone']),
        
        # Weapons (simplified icons)
        'steel_bastard_sword': lambda c: draw_sword(c, COLORS['steel'], COLORS['wood']),
        'wolfsteel_longsword': lambda c: draw_sword(c, COLORS['dark_steel'], COLORS['dark_wood']),
        'tempered_silver_saber': lambda c: draw_sword(c, COLORS['silver'], COLORS['wood']),
        'griffin_casting_silverblade': lambda c: draw_sword(c, COLORS['silver'], COLORS['gold']),
        'skellige_heavy_axe': lambda c: draw_tool(c, COLORS['steel'], COLORS['wood']),
        'dwarven_carbon_steel_hammer': lambda c: draw_tool(c, COLORS['dark_steel'], COLORS['dark_wood']),
        'stygga_curved_blade': lambda c: draw_sword(c, COLORS['bronze'], COLORS['brown']),
        'runic_shortspear': lambda c: draw_sword(c, COLORS['steel'], COLORS['light_wood']),
        'viper_duelist_blade': lambda c: draw_sword(c, COLORS['steel'], COLORS['dark_wood']),
        'northern_reaver_halberd': lambda c: draw_sword(c, COLORS['steel'], COLORS['wood']),
        'blade_of_the_leshen_thorn': lambda c: draw_sword(c, COLORS['green'], COLORS['dark_wood']),
        'moonlit_veil': lambda c: draw_sword(c, COLORS['glow_blue'], COLORS['silver']),
        'fenrirs_hunger': lambda c: draw_sword(c, COLORS['dark_steel'], COLORS['dark_wood']),
        'stormcallers_pike': lambda c: draw_sword(c, COLORS['glow_blue'], COLORS['light_wood']),
        'heartfire_cleaver': lambda c: draw_tool(c, COLORS['fire'], COLORS['dark_wood']),
        
        # Armor (simplified icons)
        'witcher_leather_vest': lambda c: draw_armor_piece(c, COLORS['brown']),
        'wolf_school_gambeson': lambda c: draw_armor_piece(c, COLORS['dark_steel']),
        'griffin_battlemage_coat': lambda c: draw_armor_piece(c, COLORS['gold']),
        'bear_reinforced_plate': lambda c: draw_armor_piece(c, COLORS['steel']),
        'skellige_raider_jacket': lambda c: draw_armor_piece(c, COLORS['brown']),
        'viper_duelist_jacket': lambda c: draw_armor_piece(c, COLORS['dark_green']),
        'nilfgaardian_scout_brigandine': lambda c: draw_armor_piece(c, COLORS['dark_steel']),
        'redanian_guard_cuirass': lambda c: draw_armor_piece(c, COLORS['bronze']),
        'runic_lamellar_harness': lambda c: draw_armor_piece(c, COLORS['silver']),
        'arachas_chitin_breastplate': lambda c: draw_armor_piece(c, COLORS['dark_green']),
        'eternal_oak_carapace': lambda c: draw_armor_piece(c, COLORS['wood']),
        'spectral_warden_vestments': lambda c: draw_armor_piece(c, COLORS['glow_blue']),
        'draconid_scale_mantle': lambda c: draw_armor_piece(c, COLORS['ruby']),
        'witcher_kingslayer_mail': lambda c: draw_armor_piece(c, COLORS['dark_steel']),
    }
    
    # Generate item textures
    print("Generating item textures...")
    for name, draw_func in items.items():
        try:
            canvas = TextureCanvas(16, 16)
            draw_func(canvas)
            canvas.save_png(item_path / f"{name}.png")
        except Exception as e:
            print(f"Error generating {name}: {e}")
            import traceback
            traceback.print_exc()
            break
    
    # Generate mob textures
    print("\nGenerating mob textures...")
    
    # Kaedweni Soldier - blue and white
    canvas = TextureCanvas(128, 128)
    draw_humanoid(canvas, (220, 180, 160), (30, 80, 150), (255, 255, 255))
    canvas.save_png(entity_path / "kaedweni_soldier.png")
    
    # Redanian Guard - red and gold
    canvas = TextureCanvas(128, 128)
    draw_humanoid(canvas, (220, 180, 160), (180, 30, 30), (255, 215, 0))
    canvas.save_png(entity_path / "redanian_guard.png")
    
    # Nilfgaardian Scout - black and gold
    canvas = TextureCanvas(128, 128)
    draw_humanoid(canvas, (200, 160, 140), (20, 20, 20), (255, 215, 0))
    canvas.save_png(entity_path / "nilfgaardian_scout.png")
    
    print(f"\n✓ Generated {len(items)} item textures and 3 mob textures")

if __name__ == '__main__':
    generate_all()
