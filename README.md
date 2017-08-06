# ApkParser

The reason why I wrote this repo is the curiosity about how Android arrange its APK package.
After some internet search, I prefer to parse the APK manually.
This is the most direct way to deeply understand the topic.

There already exist many powerful tools to deal with this parsing work indeed like ApkTool, dex2jar, Jeb, ClassyShark.
Using them will greatly help us diving into Android. 
Buf if you want to dig more, try to parse it yourself.

As we all know, the APK is a ZIP file consists of several components like .dex, .arsc, .manifest, .so, cert, resources.
This repo introduces four of them which we contact most frequently in our daily work.

|component|module|
|---|---|
|.dex|parser_dex|
|.arsc|parser_arsc|
|.manifest|parser_manifest|
|.so|parser_elfso|

The `common` module provides several tool implementations to facilitate the procedure of parsing e.g. RandomAccessStreamer, LogUtil, PrintUtil and more.

If you have interest in this work, feel free to fork and extend more features as you lik. Ideas worth spread.
