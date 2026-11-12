# Kosmos component previews

Each of the 22 component Kotlin files contains one `@Preview` function. That single scrollable gallery brings together all of the component's style, effect and interaction examples: 68 examples in total. They are examples of the main effects and configurations, rather than every possible combination of parameters. The shared preview frame is internal. Preview annotations are available to both build variants; the interactive tooling dependency remains debug-only.

Select the `debug` build variant for `kosmos`, open a component file under `src/main/java/com/kosmos/components` (for example `shapes/KosmosBlob.kt`), and use Split or Design view. The single preview function is at the bottom of that same file. Each gallery contains labeled sections with a short explanation of the UI and its interaction. Enable Interactive Preview to scroll through every section, see animations, type into search, toggle switches, swipe cards and actions, or open and close menus and sheets. Static previews show an animation's initial frame; they do not show the full motion.

| Category | Examples |
| --- | --- |
| Backgrounds | Neutral and neon waves, thin dividers, radial shading, custom borders, moonlit and minimal mountains |
| Shapes | Blob silhouettes, shallow and deep liquid waves, solid/dashed/dotted orbits, glow and satellites |
| Loaders | Default and custom colors, sizes, ring/bar/dot counts, filled and outline morphs, stopped states where supported |
| Cards and text | Glass opacity and tint, raised/flat/pressed neumorphic cards, gradient palettes, multiline text |
| Buttons and search | Pill and bordered buttons, disabled controls, empty/populated search and custom content slots |
| Gestures and inputs | Toggle states, card decks, closed/start/end swipe actions, collapsed/expanded sheets, all four radial layouts |

The examples use the same public components as the app playground. The glass card provides translucency without background blur. A disabled gradient button changes interaction only, without automatic visual dimming.
