# Inventory Tracker

Console app in plain Java (no libraries).

## Run (from the project root)

    mkdir -p out
    javac -d out $(find src -name "*.java")
    java -cp out com.inventory.Main

On Windows PowerShell:

    mkdir out -Force
    javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
    java -cp out com.inventory.Main
