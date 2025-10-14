# AxisEngine — Minimal Java/LWJGL Engine

> Windowing, input, time, and rendering primitives with a tiny `Game` interface.  
> Voxel/game logic lives **outside** the engine (e.g., Chisel).

## Status
Scaffold only. M1 target: window + loop + input + time with a demo app.

## Modules (planned)
- `engine.core` — lifecycle, context
- `engine.window` — GLFW window wrapper
- `engine.input` — keyboard/mouse polling & edges
- `engine.time` — delta time, FPS
- `engine.gfx` — shaders, meshes, textures (later)
- `engine.util` — logging, debug tools
- `engine.demo` — demo app using the engine

## Roadmap
- M1: window & loop (demo clears screen, logs FPS)
- M2: shader/mesh/texture + MVP camera (rotating cube)
- M3: utilities & cleanup
