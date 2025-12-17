#!/usr/bin/env python3
"""Generate handcrafted WitcherCraft textures.

This script draws stylized pixel art for 16×16 item icons and 128×128 humanoid
skins using deterministic instructions so the mod can ship real textures without
external art assets.
"""
from __future__ import annotations

import argparse
import struct
import zlib
from pathlib import Path
from typing import Callable, Dict, Iterable, Tuple

ROOT = Path(__file__).resolve().parents[1]
TEXTURE_ROOT = ROOT / "src/main/resources/assets/witchercraft/textures"

Color = Tuple[int, int, int, int]
RGB = Tuple[int, int, int]


def clamp(value: float) -> int:
    return max(0, min(255, int(round(value))))


def rgba(rgb: RGB, alpha: int = 255) -> Color:
    return rgb[0], rgb[1], rgb[2], alpha


def adjust(rgb: RGB, delta: int) -> RGB:
    return (clamp(rgb[0] + delta), clamp(rgb[1] + delta), clamp(rgb[2] + delta))


def blend(dst: Color, src: Color) -> Color:
    sr, sg, sb, sa = src
    dr, dg, db, da = dst
    if sa >= 255:
        return sr, sg, sb, 255
    if sa <= 0:
        return dst
    a = sa / 255.0
    inv = 1.0 - a
    out_a = clamp(sa + da * inv)
    return (
        clamp(sr * a + dr * inv),
        clamp(sg * a + dg * inv),
        clamp(sb * a + db * inv),
        out_a,
    )


class TextureCanvas:
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
        self.pixels[idx] = blend(self.pixels[idx], color)

    def fill(self, color: Color) -> None:
        self.pixels = [color] * (self.width * self.height)

    def fill_rect(self, x0: int, y0: int, x1: int, y1: int, color: Color) -> None:
        for y in range(max(0, y0), min(self.height, y1)):
            for x in range(max(0, x0), min(self.width, x1)):
                self.set_pixel(x, y, color)

    def draw_frame(self, x0: int, y0: int, x1: int, y1: int, color: Color) -> None:
        for x in range(x0, x1):
            self.set_pixel(x, y0, color)
            self.set_pixel(x, y1 - 1, color)
        for y in range(y0, y1):
            self.set_pixel(x0, y, color)
            self.set_pixel(x1 - 1, y, color)

    def gradient(self, vertical: bool, colors: Tuple[RGB, ...]) -> None:
        steps = len(colors) - 1
        for idx in range(self.height if vertical else self.width):
            t = idx / (self.height - 1) if vertical else idx / (self.width - 1)
            seg = min(int(t * steps), steps - 1)
            local = t * steps - seg
            c0 = colors[seg]
            c1 = colors[seg + 1]
            r = clamp(c0[0] * (1 - local) + c1[0] * local)
            g = clamp(c0[1] * (1 - local) + c1[1] * local)
            b = clamp(c0[2] * (1 - local) + c1[2] * local)
            for j in range(self.width if vertical else self.height):
                x, y = (j, idx) if vertical else (idx, j)
                self.set_pixel(x, y, (r, g, b, 255))

    def add_noise(self, strength: int, seed: int = 0) -> None:
        for y in range(self.height):
            for x in range(self.width):
                idx = y * self.width + x
                r, g, b, a = self.pixels[idx]
                n = (x * 374761 + y * 668265 + seed * 1447) & 0xFF
                delta = (n % (2 * strength + 1)) - strength
                self.pixels[idx] = (clamp(r + delta), clamp(g + delta), clamp(b + delta), a)


def write_png(path: Path, canvas: TextureCanvas) -> None:
    raw = bytearray()
    idx = 0
    for _y in range(canvas.height):
        raw.append(0)
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


# ---------------------------------------------------------------------------
# Item artwork helpers
# ---------------------------------------------------------------------------


def _draw_supports(canvas: TextureCanvas, xs: Iterable[int], color: RGB) -> None:
    for x in xs:
        canvas.fill_rect(x, 2, x + 1, 14, rgba(color))


def draw_frostglass_lantern(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((8, 12, 18), (24, 34, 46), (8, 12, 18)))
    canvas.draw_frame(2, 1, 14, 15, rgba((40, 66, 90)))
    canvas.draw_frame(3, 2, 13, 14, rgba((82, 110, 140)))
    canvas.fill_rect(4, 3, 12, 13, rgba((190, 238, 255)))
    canvas.fill_rect(5, 4, 11, 12, rgba((130, 205, 255), 235))
    canvas.fill_rect(6, 5, 10, 11, rgba((255, 255, 255), 210))
    canvas.fill_rect(6, 0, 10, 1, rgba((48, 72, 92)))
    canvas.fill_rect(7, 15, 9, 16, rgba((48, 72, 92)))


def draw_frozen_well_core(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((14, 40, 58), (40, 120, 160), (210, 255, 255)))
    canvas.draw_frame(1, 1, 15, 15, rgba((32, 60, 80)))
    for radius in range(2, 6):
        color = (90 + radius * 12, 200 - radius * 5, 240)
        canvas.fill_rect(8 - radius, 8 - radius, 8 + radius, 8 + radius, rgba(color, 210))
    canvas.fill_rect(7, 3, 9, 13, rgba((235, 255, 255), 220))
    canvas.fill_rect(3, 7, 13, 9, rgba((235, 255, 255), 220))


def draw_gatehouse_portcullis(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((38, 40, 46), (80, 86, 94)))
    for x in range(2, 15, 3):
        canvas.fill_rect(x, 1, x + 1, 15, rgba((140, 140, 148)))
        canvas.fill_rect(x + 1, 1, x + 2, 15, rgba((96, 96, 104)))
    for y in range(2, 16, 4):
        canvas.fill_rect(1, y, 15, y + 1, rgba((64, 64, 72)))
    canvas.draw_frame(0, 0, 16, 16, rgba((24, 24, 28)))


def draw_herbal_drying_rack(canvas: TextureCanvas) -> None:
    canvas.fill(rgba((30, 18, 8)))
    canvas.fill_rect(3, 2, 13, 3, rgba((104, 72, 40)))
    canvas.fill_rect(3, 13, 13, 14, rgba((104, 72, 40)))
    canvas.fill_rect(2, 3, 4, 13, rgba((88, 58, 34)))
    canvas.fill_rect(12, 3, 14, 13, rgba((88, 58, 34)))
    herbs = [((70, 122, 60), 5), ((94, 150, 74), 8), ((64, 112, 50), 11)]
    for color, x in herbs:
        for y in range(4, 12):
            canvas.set_pixel(x, y, rgba(adjust(color, (y - 8) * 6)))
            canvas.set_pixel(x + 1, y, rgba(adjust(color, -10)))


def draw_notice_board(canvas: TextureCanvas) -> None:
    canvas.fill(rgba((52, 28, 12)))
    canvas.draw_frame(1, 1, 15, 15, rgba((136, 90, 46)))
    canvas.fill_rect(2, 2, 14, 14, rgba((236, 216, 182)))
    canvas.fill_rect(3, 3, 13, 5, rgba((248, 238, 210)))
    canvas.fill_rect(3, 6, 13, 8, rgba((220, 200, 166)))
    canvas.fill_rect(3, 9, 13, 13, rgba((242, 224, 190)))
    for pin_x in (4, 11):
        canvas.set_pixel(pin_x, 3, rgba((166, 86, 58)))


def draw_plaster_wall(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((226, 224, 214), (194, 190, 180)))
    canvas.add_noise(5, seed=7)
    canvas.fill_rect(0, 0, 16, 2, rgba((124, 110, 90)))
    canvas.fill_rect(0, 14, 16, 16, rgba((124, 110, 90)))
    for y in range(3, 14, 4):
        canvas.fill_rect(0, y, 16, y + 1, rgba((164, 160, 150), 200))


def _draw_roof(canvas: TextureCanvas, accent: RGB) -> None:
    canvas.gradient(True, ((accent[0] - 20, accent[1] - 20, accent[2] - 20), accent))
    for y in range(3, 16, 4):
        canvas.fill_rect(0, y, 16, y + 1, rgba(adjust(accent, -30)))
    for x in range(0, 16, 4):
        canvas.fill_rect(x, 0, x + 1, 16, rgba(adjust(accent, -40), 180))


def draw_roof_shingles(canvas: TextureCanvas) -> None:
    _draw_roof(canvas, (84, 108, 140))


def draw_roof_shingle_slab(canvas: TextureCanvas) -> None:
    _draw_roof(canvas, (92, 118, 150))
    canvas.fill_rect(0, 0, 16, 8, rgba((160, 178, 202), 160))
    canvas.fill_rect(0, 7, 16, 8, rgba((48, 54, 62)))


def draw_roof_shingle_stairs(canvas: TextureCanvas) -> None:
    _draw_roof(canvas, (78, 100, 130))
    for step, offset in enumerate(range(4, 16, 4)):
        canvas.fill_rect(offset, 0, 16, step + 5, rgba((40, 48, 58), 200))
        canvas.fill_rect(0, step + 4, offset, step + 5, rgba((150, 168, 188), 180))


def draw_roof_shingle_vertical_slab(canvas: TextureCanvas) -> None:
    _draw_roof(canvas, (70, 94, 126))
    canvas.fill_rect(8, 0, 16, 16, rgba((28, 34, 44), 220))
    canvas.fill_rect(7, 0, 8, 16, rgba((160, 176, 196), 150))


def draw_stone(canvas: TextureCanvas, mossy: bool, slab: bool) -> None:
    canvas.gradient(True, ((96, 98, 108), (134, 136, 148)))
    canvas.add_noise(8, seed=11)
    for y in range(4, 16, 6):
        canvas.fill_rect(0, y, 16, y + 1, rgba((70, 72, 82)))
    for x in (4, 9, 13):
        canvas.fill_rect(x, 0, x + 1, 16, rgba((70, 72, 82)))
    if slab:
        canvas.fill_rect(0, 0, 16, 8, rgba((150, 152, 164), 220))
        canvas.fill_rect(0, 7, 16, 8, rgba((60, 60, 70)))
    if mossy:
        for y in range(0, 16, 2):
            for x in range(16):
                if (x * 3 + y * 5) % 11 == 0:
                    canvas.set_pixel(x, y, rgba((70, 110, 62), 220))


def draw_stone_foundation(canvas: TextureCanvas) -> None:
    draw_stone(canvas, mossy=False, slab=False)


def draw_stone_foundation_mossy(canvas: TextureCanvas) -> None:
    draw_stone(canvas, mossy=True, slab=False)


def draw_stone_foundation_slab(canvas: TextureCanvas) -> None:
    draw_stone(canvas, mossy=False, slab=True)


def draw_stone_foundation_slab_mossy(canvas: TextureCanvas) -> None:
    draw_stone(canvas, mossy=True, slab=True)


def draw_timber_beam(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((60, 30, 12), (118, 72, 36)))
    canvas.add_noise(4, seed=3)
    for y in range(2, 16, 4):
        canvas.fill_rect(0, y, 16, y + 1, rgba((80, 44, 20), 220))
    for ring in (4, 11):
        canvas.fill_rect(ring, 0, ring + 1, 16, rgba((34, 20, 10), 200))


def draw_reinforced_palisade_log(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((52, 24, 10), (116, 70, 34)))
    canvas.add_noise(5, seed=4)
    canvas.fill_rect(0, 6, 16, 10, rgba((102, 60, 24)))
    canvas.fill_rect(0, 7, 16, 9, rgba((40, 40, 40), 220))
    for strap in (2, 9, 14):
        canvas.fill_rect(strap, 0, strap + 1, 16, rgba((28, 16, 8), 200))


def draw_supply_crate(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((78, 46, 22), (140, 92, 50)))
    canvas.draw_frame(1, 1, 15, 15, rgba((46, 28, 12)))
    canvas.fill_rect(2, 2, 14, 14, rgba((120, 78, 40)))
    canvas.fill_rect(2, 7, 14, 9, rgba((170, 120, 72)))
    canvas.fill_rect(7, 2, 9, 14, rgba((170, 120, 72)))
    for corner in ((2, 2), (11, 2), (2, 11), (11, 11)):
        canvas.fill_rect(corner[0], corner[1], corner[0] + 3, corner[1] + 3, rgba((92, 60, 28)))


def draw_witcher_forge_block(canvas: TextureCanvas) -> None:
    canvas.gradient(True, ((30, 6, 6), (160, 38, 26)))
    canvas.add_noise(6, seed=9)
    canvas.fill_rect(0, 0, 16, 4, rgba((20, 20, 20)))
    canvas.fill_rect(4, 4, 12, 10, rgba((60, 60, 68)))
    canvas.fill_rect(3, 10, 13, 13, rgba((32, 10, 8)))
    canvas.fill_rect(5, 6, 11, 8, rgba((200, 100, 50), 200))
    canvas.fill_rect(6, 7, 10, 9, rgba((255, 200, 120), 220))


ITEM_DRAWERS: Dict[str, Callable[[TextureCanvas], None]] = {
    "frostglass_lantern": draw_frostglass_lantern,
    "frozen_well_core": draw_frozen_well_core,
    "gatehouse_portcullis": draw_gatehouse_portcullis,
    "herbal_drying_rack": draw_herbal_drying_rack,
    "kaedweni_notice_board": draw_notice_board,
    "kaedweni_plaster_wall": draw_plaster_wall,
    "kaedweni_roof_shingles": draw_roof_shingles,
    "kaedweni_roof_shingle_slab": draw_roof_shingle_slab,
    "kaedweni_roof_shingle_stairs": draw_roof_shingle_stairs,
    "kaedweni_roof_shingle_vertical_slab": draw_roof_shingle_vertical_slab,
    "kaedweni_stone_foundation": draw_stone_foundation,
    "kaedweni_stone_foundation_mossy": draw_stone_foundation_mossy,
    "kaedweni_stone_foundation_slab": draw_stone_foundation_slab,
    "kaedweni_stone_foundation_slab_mossy": draw_stone_foundation_slab_mossy,
    "kaedweni_timber_beam": draw_timber_beam,
    "reinforced_palisade_log": draw_reinforced_palisade_log,
    "supply_crate_kaedwen": draw_supply_crate,
    "witcher_forge_block": draw_witcher_forge_block,
}


# ---------------------------------------------------------------------------
# Humanoid skins (128×128)
# ---------------------------------------------------------------------------


def _fill_rgb(canvas: TextureCanvas, rect: Tuple[int, int, int, int], color: RGB, alpha: int = 255) -> None:
    x0, y0, x1, y1 = rect
    canvas.fill_rect(x0, y0, x1, y1, rgba(color, alpha))


def _draw_emblem(canvas: TextureCanvas, rect: Tuple[int, int, int, int], color: RGB) -> None:
    x0, y0, x1, y1 = rect
    cx = (x0 + x1) // 2
    cy = (y0 + y1) // 2
    canvas.fill_rect(x0, cy - 2, x1, cy + 2, rgba(color))
    canvas.fill_rect(cx - 2, y0, cx + 2, y1, rgba(color))


def draw_humanoid(canvas: TextureCanvas, palette: Dict[str, RGB]) -> None:
    canvas.gradient(True, (palette["bg_dark"], palette["bg_light"]))
    head_rect = (16, 16, 48, 48)
    chest_rect = (16, 48, 48, 96)
    belt_rect = (16, 96, 48, 104)
    legs_rect = (16, 104, 48, 144)
    arms_rect = (48, 48, 64, 112)
    helmet = palette["helmet"]
    trim = palette["trim"]
    tunic = palette["tunic"]
    accent = palette["accent"]
    _fill_rgb(canvas, head_rect, helmet)
    canvas.draw_frame(*head_rect, rgba(trim))
    _fill_rgb(canvas, (20, 20, 44, 44), adjust(helmet, 18))
    canvas.fill_rect(26, 24, 38, 34, rgba((palette["visor"])) )
    _fill_rgb(canvas, chest_rect, tunic)
    canvas.draw_frame(*chest_rect, rgba(trim, 200))
    canvas.fill_rect(18, 56, 46, 88, rgba(adjust(tunic, 20)))
    canvas.fill_rect(20, 60, 44, 64, rgba(adjust(tunic, -20)))
    _fill_rgb(canvas, belt_rect, palette["belt"])
    canvas.fill_rect(24, 96, 40, 104, rgba(adjust(palette["belt"], 20)))
    _fill_rgb(canvas, legs_rect, palette["legs"])
    canvas.fill_rect(16, 120, 32, 144, rgba(adjust(palette["legs"], -14)))
    canvas.fill_rect(32, 120, 48, 144, rgba(adjust(palette["legs"], 8)))
    _fill_rgb(canvas, arms_rect, palette["sleeve"])
    canvas.fill_rect(48, 88, 64, 96, rgba(palette["glove"]))
    _draw_emblem(canvas, (22, 64, 42, 88), accent)


def draw_kaedweni_soldier(canvas: TextureCanvas) -> None:
    palette = {
        "bg_dark": (12, 18, 30),
        "bg_light": (34, 46, 68),
        "helmet": (34, 60, 104),
        "trim": (200, 180, 120),
        "visor": (230, 230, 240),
        "tunic": (44, 78, 132),
        "accent": (212, 180, 92),
        "belt": (36, 28, 18),
        "legs": (28, 34, 52),
        "sleeve": (40, 72, 120),
        "glove": (26, 20, 18),
    }
    draw_humanoid(canvas, palette)


def draw_redanian_guard(canvas: TextureCanvas) -> None:
    palette = {
        "bg_dark": (30, 10, 10),
        "bg_light": (70, 18, 18),
        "helmet": (92, 0, 0),
        "trim": (230, 190, 120),
        "visor": (250, 240, 220),
        "tunic": (150, 24, 24),
        "accent": (22, 42, 100),
        "belt": (38, 20, 12),
        "legs": (26, 30, 56),
        "sleeve": (120, 30, 30),
        "glove": (40, 12, 6),
    }
    draw_humanoid(canvas, palette)


def draw_nilfgaardian_scout(canvas: TextureCanvas) -> None:
    palette = {
        "bg_dark": (8, 8, 8),
        "bg_light": (26, 26, 30),
        "helmet": (30, 30, 34),
        "trim": (212, 156, 64),
        "visor": (220, 210, 170),
        "tunic": (40, 40, 46),
        "accent": (210, 156, 52),
        "belt": (18, 12, 8),
        "legs": (18, 18, 26),
        "sleeve": (30, 30, 38),
        "glove": (18, 18, 24),
    }
    draw_humanoid(canvas, palette)


MOB_DRAWERS: Dict[str, Callable[[TextureCanvas], None]] = {
    "kaedweni_soldier": draw_kaedweni_soldier,
    "redanian_guard": draw_redanian_guard,
    "nilfgaardian_scout": draw_nilfgaardian_scout,
}


def build(force: bool, target: str) -> int:
    generated = 0
    if target in {"all", "items"}:
        for name, drawer in ITEM_DRAWERS.items():
            path = TEXTURE_ROOT / "item" / f"{name}.png"
            if path.exists() and not force:
                print(f"[skip] {path.relative_to(ROOT)}")
                continue
            canvas = TextureCanvas(16, 16)
            drawer(canvas)
            write_png(path, canvas)
            generated += 1
            print(f"[wrote] {path.relative_to(ROOT)}")
    if target in {"all", "mobs"}:
        for name, drawer in MOB_DRAWERS.items():
            path = TEXTURE_ROOT / "entity/humanoids" / f"{name}.png"
            if path.exists() and not force:
                print(f"[skip] {path.relative_to(ROOT)}")
                continue
            canvas = TextureCanvas(128, 128)
            drawer(canvas)
            write_png(path, canvas)
            generated += 1
            print(f"[wrote] {path.relative_to(ROOT)}")
    return generated


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Generate WitcherCraft textures")
    parser.add_argument("--force", action="store_true", help="Overwrite existing textures")
    parser.add_argument(
        "--target",
        choices=["all", "items", "mobs"],
        default="all",
        help="Limit generation to a subset",
    )
    return parser.parse_args()


def main() -> None:
    args = parse_args()
    count = build(force=args.force, target=args.target)
    print(f"Done. Generated {count} texture(s).")


if __name__ == "__main__":
    main()
