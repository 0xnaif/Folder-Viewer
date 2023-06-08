# Folder-Viewer
It is a demonstration of composite design pattern. A GUI client application using by which you can choose a certain folder. Once you select a folder, the app recursively traverses all of its contents (files and folders) and fills the required information as follows:
- Folder	: only name.
- File	: name, size, extension.

After that, the app fills a tree view with the selected folder and all sub folders. Once you select one of the folders in the tree view, the app shows a detailed info about its contents in the output window. 
After traversing, the app traverses the created structure again and calculate the size of all folders.
