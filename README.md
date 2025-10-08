# project-2-csce331

Running instructions:
1. Start from the project root directory (has .vscode, lib, and src inside)
2. To compile, javac --module-path ./lib --add-modules javafx.controls,javafx.fxml src/*.java
3. To run, java --module-path ./lib --add-modules javafx.controls,javafx.fxml "-Djava.library.path=./lib/bin" -cp src App

