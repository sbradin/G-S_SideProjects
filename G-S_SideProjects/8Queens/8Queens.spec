# -*- mode: python ; coding: utf-8 -*-


block_cipher = None


a = Analysis(
    ['8Queens.py'],
    pathex=[],
    binaries=[],
    datas=[('data/move-self.mp3','data'), ('data/loss_sound.mp3','data'), ('data/win_sound.mp3','data'), ('data/LightQueen.webp','data'), ('data/checkbox_unchecked.png','data'), ('data/checkbox_checked.png','data'), ('data/alert.mp3','data')],
    hiddenimports=[],
    hookspath=[],
    hooksconfig={},
    runtime_hooks=[],
    excludes=[],
    win_no_prefer_redirects=False,
    win_private_assemblies=False,
    cipher=block_cipher,
    noarchive=False,
)
pyz = PYZ(a.pure, a.zipped_data, cipher=block_cipher)

exe = EXE(
    pyz,
    a.scripts,
    a.binaries,
    a.zipfiles,
    a.datas,
    [],
    name='8Queens',
    debug=True,
    bootloader_ignore_signals=False,
    strip=False,
    upx=False,
    upx_exclude=[],
    runtime_tmpdir=None,
    console=False,
    disable_windowed_traceback=True,
    argv_emulation=False,
    target_arch=None,
    codesign_identity=None,
    entitlements_file=None,
    icon = 'Icon8Queens.icns',
    bundle_files = 1,
)

app = BUNDLE(exe,
    name='8Queens.app',
    icon='Icon8Queens.icns',
    bundle_identifier = None,
    version='0.0.1',
    CFBundleExecutable = '8Queens',
    info_plist={
        'NSPrincipalClass': 'NSApplication',
        'NSAppleScriptEnabled': True,
        'CFBundleIconFile': 'Icon8Queens.icns',
        'CFBundleDocumentTypes': [
            {
                'CFBundleTypeName': '8Queens Game',
                'LSItemContentTypes': ['com.example.8Queens'],
                'LSHandlerRank': 'Owner',
                'CFBundleTypeRole': 'Viewer'
                }
            ]
        },
)
