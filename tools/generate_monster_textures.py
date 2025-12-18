#!/usr/bin/env python3
"""Generate placeholder monster entity textures for WitcherCraft mod.

Uses the same pure Python PNG generation as generate_textures.py (no PIL needed).
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

# Monster texture configurations (using zombie-style 64x64 format)
MONSTERS = {
    'drowner': {'primary': (40, 80, 60), 'secondary': (60, 100, 80), 'accent': (30, 60, 45)},
    'nekker': {'primary': (60, 50, 40), 'secondary': (80, 70, 55), 'accent': (45, 35, 30)},
    'ghoul': {'primary': (70, 60, 55), 'secondary': (90, 80, 70), 'accent': (50, 45, 40)},
    'alghoul': {'primary': (50, 40, 50), 'secondary': (70, 55, 70), 'accent': (35, 28, 35)},
    'wraith': {'primary': (180, 200, 220), 'secondary': (200, 220, 240), 'accent': (150, 170, 190)},
    'werewolf': {'primary': (60, 45, 35), 'secondary': (80, 60, 45), 'accent': (100, 80, 60)},
}


def clamp(value: float) -> int:
    return max(0, min(255, int(round(value))))


def rgba(rgb: RGB, alpha: int = 255) -> Color:
    return rgb[0], rgb[1], rgb[2], alpha


class TextureCanvas:
    """Simple RGBA texture canvas."""
    
    def __init__(self, width: int, height: int):
        self.width = width
        self.height = height
        self.pixels: list[Color] = [(0, 0, 0, 0)] * (width * height)

    def _inside(self, x: int, y: int) -> bool:
        return 0 <= x < self.width and 0 <= y < self.height

    def set_pixel(self, x: int, y: int, color: Color) -> None:
        if not self._inside(x, y):
            return
        idx = y * self.width + x
        self.pixels[idx] = color

    def get_pixel(self, x: int, y: int) -> Color:
        if not self._inside(x, y):
            return (0, 0, 0, 0)
        return self.pixels[y * self.width + x]

    def fill_rect(self, x0: int, y0: int, x1: int, y1: int, color: Color) -> None:
        for y in range(max(0, y0), min(self.height, y1)):
            for x in range(max(0, x0), min(self.width, x1)):
                self.set_pixel(x, y, color)


def write_png(path: Path, canvas: TextureCanvas) -> None:
    """Write a TextureCanvas to a PNG file."""
    raw = bytearray()
    idx = 0
    for _y in range(canvas.height):
        raw.append(0)  # PNG filter byte
        for _x in range(canvas.width):
            raw.extend(canvas.pixels[idx])
            idx += 1
    compressed = zlib.compress(bytes(raw), level=9)

    def chunk(tag: bytes, data: bytes) -> bytes:
        return struct.pack(">I", len(data)) + tag + data + struct.pack(">I", zlib.crc32(tag + data) & 0xFFFFFFFF)

    ihdr = struct.pack(">IIBBBBB", canvas.width, canvas.height, 8, 6, 0, 0, 0)
    data = bytearray(b"\x89PNG\r\n\x1a\n")
    data.extend(chunk(b"IHDR", ihdr))
    data.extend(chunk(b"IDAT", compressed))
    data.extend(chunk(b"IEND", b""))
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_bytes(data)


def create_zombie_style_texture(name: str, colors: dict) -> TextureCanvas:
    """Create a zombie-model compatible 64x64 texture."""
    canvas = TextureCanvas(64, 64)
    
    primary = rgba(colors['primary'])
    secondary = rgba(colors['secondary'])
    accent = rgba(colors['accent'])
    
    # Standard Minecraft zombie/player UV layout (64x64)
    # Head (8x8 pixels) - appears at UV 0-31 horizontally, 0-15 vertically
    
    # Head front (pixels 8-15, 8-15 in UV)
    canvas.fill_rect(8, 8, 16, 16, secondary)
    
    # Head top (pixels 8-15, 0-7)
    canvas.fill_rect(8, 0, 16, 8, primary)
    
    # Head left side (pixels 0-7, 8-15)
    canvas.fill_rect(0, 8, 8, 16, primary)
    
    # Head right side (pixels 16-23, 8-15)
    canvas.fill_rect(16, 8, 24, 16, primary)
    
    # Head back (pixels 24-31, 8-15)
    canvas.fill_rect(24, 8, 32, 16, accent)
    
    # Head bottom (pixels 16-23, 0-7)
    canvas.fill_rect(16, 0, 24, 8, accent)
    
    # Eyes - red for most monsters, white/glowing for wraith
    if name == 'wraith':
        eye_color = rgba((255, 255, 255), 200)
    else:
        eye_color = rgba((200, 50, 50))
    
    canvas.set_pixel(10, 11, eye_color)
    canvas.set_pixel(11, 11, eye_color)
    canvas.set_pixel(13, 11, eye_color)
    canvas.set_pixel(14, 11, eye_color)
    
    # Body (8x12 pixels) - UV starts around 16,16
    # Body front
    canvas.fill_rect(20, 20, 28, 32, primary)
    
    # Body right side
    canvas.fill_rect(16, 20, 20, 32, secondary)
    
    # Body back
    canvas.fill_rect(32, 20, 40, 32, accent)
    
    # Body left side
    canvas.fill_rect(28, 20, 32, 32, secondary)
    
    # Body top
    canvas.fill_rect(20, 16, 28, 20, primary)
    
    # Body bottom
    canvas.fill_rect(28, 16, 36, 20, accent)
    
    # Right arm (4x12) - UV 40-55 x 16-32
    canvas.fill_rect(44, 20, 48, 32, secondary)
    canvas.fill_rect(40, 20, 44, 32, primary)
    canvas.fill_rect(48, 20, 52, 32, accent)
    canvas.fill_rect(52, 20, 56, 32, primary)
    
    # Left arm (4x12) - UV 32-47 x 48-64
    canvas.fill_rect(36, 52, 40, 64, secondary)
    canvas.fill_rect(32, 52, 36, 64, primary)
    canvas.fill_rect(40, 52, 44, 64, accent)
    canvas.fill_rect(44, 52, 48, 64, primary)
    
    # Right leg (4x12) - UV 0-15 x 16-32
    canvas.fill_rect(4, 20, 8, 32, secondary)
    canvas.fill_rect(0, 20, 4, 32, primary)
    canvas.fill_rect(8, 20, 12, 32, accent)
    canvas.fill_rect(12, 20, 16, 32, primary)
    
    # Left leg (4x12) - UV 16-31 x 48-64
    canvas.fill_rect(20, 52, 24, 64, secondary)
    canvas.fill_rect(16, 52, 20, 64, primary)
    canvas.fill_rect(24, 52, 28, 64, accent)
    canvas.fill_rect(28, 52, 32, 64, primary)
    
    # Add some variation/texture using simple pattern
    for i in range(40):
        x = (i * 7 + 3) % 64
        y = (i * 11 + 5) % 64
        current = canvas.get_pixel(x, y)
        if current[3] > 0:  # Only modify visible pixels
            darker = (
                max(0, current[0] - 12),
                max(0, current[1] - 12),
                max(0, current[2] - 12),
                current[3]
            )
            canvas.set_pixel(x, y, darker)
    
    # Special effects for wraith - make semi-transparent
    if name == 'wraith':
        for y in range(64):
            for x in range(64):
                px = canvas.get_pixel(x, y)
                if px[3] > 0:
                    canvas.set_pixel(x, y, (px[0], px[1], px[2], min(px[3], 180)))
    
    return canvas


def main() -> None:
    output_dir = TEXTURE_ROOT / "entity" / "monsters"
    output_dir.mkdir(parents=True, exist_ok=True)
    
    for name, colors in MONSTERS.items():
        canvas = create_zombie_style_texture(name, colors)
        output_path = output_dir / f"{name}.png"
        write_png(output_path, canvas)
        print(f"Generated: {output_path}")
    
    print(f"\nGenerated {len(MONSTERS)} monster textures in {output_dir}")


if __name__ == "__main__":
    main()
