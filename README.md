# Java Game Inventory System

## Introduction

This Java Game Inventory System is a comprehensive application designed to demonstrate an in-depth understanding of Java's Object-Oriented Programming (OOP) principles and design patterns. It showcases how to develop a medium-sized application with features like inventory management, item crafting, and flexible search functionality. The project emphasizes the use of inheritance, encapsulation, polymorphism, and design patterns to create a scalable and robust inventory system for games.


## Features

- **Dynamic Search Functionality**: Implements the Strategy pattern for flexible item searches by names, descriptions, or comprehensive attribute-based searches.
- **Player and Storage Inventory Management**: Manages items with a weight limit in the player's inventory, automatically transferring excess items to a shared storage inventory.
- **Item Crafting and Uncrafting System**: Employs the Composite pattern to enable players to both craft and uncraft items. Crafting allows players to combine base components into new items, enhancing gameplay depth. Uncrafting permits the disassembly of items into their original components, offering strategic gameplay decisions.
- **Singleton Item Dictionary**: Employs the Singleton pattern for a central item dictionary, acting as a consistent and accessible database for item definitions.
- **Exception Handling**: Integrates comprehensive exception handling to ensure the application's stability and reliability.
- **Observer Pattern for Shared Storage**: Incorporates the Observer (Pub-Sub) pattern to manage a shared storage box. Multiple players can access and modify the storage contents. Each player has a local copy of the Storage, and any changes to the storage are broadcast to all players, ensuring everyone is updated with the latest inventory stock.

