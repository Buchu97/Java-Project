# Java Game Inventory System

## Introduction

This Java application exemplifies sophisticated Object-Oriented Programming (OOP) principles and design patterns, focusing on an inventory system for game development. It demonstrates the implementation of various design patterns including Strategy, Composite, Singleton, and Observer to address complex design challenges effectively.

## Features

- **Dynamic Search Functionality**: Implements the Strategy pattern for flexible item searches by names, descriptions, or comprehensive attribute-based searches.
- **Player and Storage Inventory Management**: Manages items with a weight limit in the player's inventory, automatically transferring excess items to a shared storage inventory.
- **Item Crafting System**: Uses the Composite pattern, allowing players to craft items from base components, enriching the gameplay experience.
- **Singleton Item Dictionary**: Employs the Singleton pattern for a central item dictionary, acting as a consistent and accessible database for item definitions.
- **Exception Handling**: Integrates comprehensive exception handling to ensure the application's stability and reliability.
- **Observer Pattern for Shared Storage**: Incorporates the Observer (Pub-Sub) pattern to manage a shared storage box. Multiple players can access and modify the storage contents. Each player has a local copy of the Storage, and any changes to the storage are broadcast to all players, ensuring everyone is updated with the latest inventory stock.

