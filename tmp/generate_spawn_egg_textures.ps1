param()

Add-Type -AssemblyName System.Drawing

function Convert-HexToColor {
    param([int]$Value)
    $r = ($Value -shr 16) -band 0xFF
    $g = ($Value -shr 8) -band 0xFF
    $b = $Value -band 0xFF
    return [System.Drawing.Color]::FromArgb($r, $g, $b)
}

function New-SpawnEggTexture {
    param(
        [string]$Path,
        [int]$Primary,
        [int]$Secondary
    )

    $bmp = New-Object System.Drawing.Bitmap 16, 16
    $primaryColor = Convert-HexToColor $Primary
    $secondaryColor = Convert-HexToColor $Secondary

    for ($x = 0; $x -lt 16; $x++) {
        for ($y = 0; $y -lt 16; $y++) {
            $bmp.SetPixel($x, $y, $primaryColor)
        }
    }

    $spots = @(
        @{ cx = 5.2; cy = 6.0; rsq = 11.5 },
        @{ cx = 11.3; cy = 10.0; rsq = 10.9 },
        @{ cx = 9.5; cy = 4.0; rsq = 6.5 }
    )

    for ($x = 0; $x -lt 16; $x++) {
        for ($y = 0; $y -lt 16; $y++) {
            foreach ($spot in $spots) {
                $dx = $x - $spot.cx
                $dy = $y - $spot.cy
                if (($dx * $dx + $dy * $dy) -le $spot.rsq) {
                    $bmp.SetPixel($x, $y, $secondaryColor)
                    break
                }
            }
        }
    }

    $directory = Split-Path $Path -Parent
    if (!(Test-Path $directory)) {
        New-Item -ItemType Directory -Path $directory -Force | Out-Null
    }

    $bmp.Save($Path, [System.Drawing.Imaging.ImageFormat]::Png)
    $bmp.Dispose()
}

New-SpawnEggTexture 'src/main/resources/assets/witchercraft/textures/item/kaedweni_soldier_spawn_egg.png' 0x3f577b 0xc8b28a
New-SpawnEggTexture 'src/main/resources/assets/witchercraft/textures/item/redanian_guard_spawn_egg.png' 0x2f3b59 0xd9d0b4
New-SpawnEggTexture 'src/main/resources/assets/witchercraft/textures/item/nilfgaardian_scout_spawn_egg.png' 0x2b2015 0xd0a64f
