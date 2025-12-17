#!/usr/bin/env python3
"""Compatibility wrapper for the new handcrafted texture generator."""
from __future__ import annotations

from pathlib import Path

from generate_textures import main as _generate


def main() -> None:  # pragma: no cover - compatibility glue
    script = Path(__file__).name
    print(
        f"[notice] {script} now delegates to tools/generate_textures.py. "
        "Please update your workflow to call the new script directly."
    )
    _generate()


if __name__ == "__main__":
    main()
