# emojiart
Compose pixel art with emojis

* Left mouse to paint
* Right mouse to flood fill
* Scroll to zoom
* Drag outside canvas to resize

A palette must be loaded to describe colors and their associated shortcuts. An example 3 bit color palette is provided.
Palettes are simple .txt files. Repeat for every color:

* String -> shortcut
* Integer -> red
* Integer -> green
* Integer -> blue

All colors range from [0, 255].