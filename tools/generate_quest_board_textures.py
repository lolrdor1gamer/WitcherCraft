#!/usr/bin/env python3
"""Generate placeholder textures for faction quest boards."""

import struct
import zlib

def create_png(width, height, pixels):
    """Create a PNG file from pixel data."""
    def png_chunk(chunk_type, data):
        chunk = chunk_type + data
        return struct.pack('>I', len(data)) + chunk + struct.pack('>I', zlib.crc32(chunk))
    
    # PNG signature
    png_data = b'\x89PNG\r\n\x1a\n'
    
    # IHDR chunk
    ihdr = struct.pack('>IIBBBBB', width, height, 8, 2, 0, 0, 0)  # 8-bit RGB
    png_data += png_chunk(b'IHDR', ihdr)
    
    # IDAT chunk
    raw_data = b''
    for row in pixels:
        raw_data += b'\x00'  # Filter type
        for r, g, b in row:
            raw_data += struct.pack('BBB', r, g, b)
    
    png_data += png_chunk(b'IDAT', zlib.compress(raw_data, 9))
    
    # IEND chunk
    png_data += png_chunk(b'IEND', b'')
    
    return png_data

def create_quest_board_texture(base_color, name):
    """Create a quest board texture with faction colors."""
    width, height = 16, 16
    pixels = []
    
    # Unpack base color
    r = (base_color >> 16) & 0xFF
    g = (base_color >> 8) & 0xFF
    b = base_color & 0xFF
    
    # Darken for wood color
    wood_r = min(255, r // 2 + 60)
    wood_g = min(255, g // 2 + 40)
    wood_b = min(255, b // 2 + 20)
    
    # Lighter accent
    accent_r = min(255, r)
    accent_g = min(255, g)
    accent_b = min(255, b)
    
    for y in range(height):
        row = []
        for x in range(width):
            # Border
            if x == 0 or x == 15 or y == 0 or y == 15:
                row.append((wood_r // 2, wood_g // 2, wood_b // 2))
            # Accent stripes (faction color)
            elif y in [2, 3, 12, 13]:
                row.append((accent_r, accent_g, accent_b))
            # Wood background
            else:
                # Add some variation
                variation = (x + y) % 3 - 1
                row.append((
                    max(0, min(255, wood_r + variation * 5)),
                    max(0, min(255, wood_g + variation * 5)),
                    max(0, min(255, wood_b + variation * 5))
                ))
        pixels.append(row)
    
    # Write PNG
    png_data = create_png(width, height, pixels)
    with open(f'src/main/resources/assets/witchercraft/textures/block/quest_board_{name}.png', 'wb') as f:
        f.write(png_data)
    print(f'Created quest_board_{name}.png')

# Faction colors from Faction.java
factions = {
    'kaedwen': 0x1E4A7C,      # Blue
    'redania': 0x8B0000,      # Dark red
    'temeria': 0x2C5F2D,      # Green
    'nilfgaard': 0x1A1A1A,    # Black
    'skellige': 0x4A7BA7,     # Light blue
    'witchers': 0x8B6914,     # Gold/brown
    'scoia_tael': 0x3A6B35    # Forest green
}

for name, color in factions.items():
    create_quest_board_texture(color, name)

print('\nAll quest board textures generated successfully!')
