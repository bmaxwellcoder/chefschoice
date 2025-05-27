# Chefs' Choice

## Table of Contents
1. [Overview](#overview)
2. [Product Spec](#product-spec)
3. [Wireframes](#wireframes)
4. [Networking](#networking)
5. [Getting Started](#getting-started)
6. [Contributing](#contributing)
7. [License](#license)

---

## Overview
### Description
Chefs' Choice makes mealtime simple through recipe recommendations for users from everyday ingredients found at home.

**Team Members:**

### App Evaluation
- **Category:** Food & Drink
- **Mobile:** This app is intended for mobile devices using Android OS.
- **Story:** Users can capture an image of their fridge or pantry to detect ingredients that can be used in various recipes.
- **Market:** This app is open to users of all ages.
- **Habit:** This app could be used daily for each meal (breakfast, lunch, dinner) of the day.
- **Scope:** First, users can use their camera to filter ingredients in generated recipes, helping eliminate food waste and decision fatigue. Large potential for business use by fitness trainers to create meal plans for their clients.

---

## Product Spec

### 1. User Stories
**Required Must-have Stories**
- User can sign up or log in to see their previous activity
- User can click a button to access their camera and capture an image
- User can discover new recipes
- User can save their favorite recipes

**Optional Nice-to-have Stories**
- User can share their favorite recipes (via text messaging, email, social media, etc.)
- User can rate recipes
- User can omit recipes based on dietary restrictions, food allergies or disliked items
- User can search for cocktail recipes or wine pairings
- User can review recipes
- User can learn food trivia

### 2. Screen Archetypes
- **Splash Screen**: A brief introduction of the app (logo & app name)
- **Login Screen**: Allows user to sign in via email or proceed without login
- **Home Screen**: Browse recipes categorized by type (e.g., popularity) and learn fun facts about food
- **Recipes Screen**: Search specified ingredients to generate recipes; use camera to scan UPC barcodes
- **Detail Screen**: View more information about a selected recipe
- **Favorites Screen**: Overview of favorite recipes
- **Information Screen**: Description of how to use the app

### 3. Navigation
**Tab Navigation**
- Home Screen
- Recipes Screen
- Favorites Screen
- Info Screen

**Flow Navigation**
- Splash Screen → [list screen navigation here]
- Login Screen → [list screen navigation here]
- Home Screen → [list screen navigation here]
- Recipes Screen → [list screen navigation here]
- Favorite Screen → [list screen navigation here]
- Info Screen → [list screen navigation here]

---

## Wireframes
See early mockups: [Wireframe.cc](https://wireframe.cc/pro/pp/a2d29070b510511)

### Digital Wireframes & Mockups
![IMG_2403](https://user-images.githubusercontent.com/83090104/150610780-6ac70369-9e2a-4832-aefc-89422a03daf9.PNG)

### Interactive Prototype
![](https://media.giphy.com/media/xeKrXygDIomMVnRmqk/giphy.gif)
![](https://media.giphy.com/media/NrDsU9QDFjJs2vuCPz/giphy.gif)
![](https://media.giphy.com/media/gI5UqdgQKYlicXEcz7/giphy.gif)
![](https://media.giphy.com/media/jLL8LpAagEDcm5RyRp/giphy.gif)
![](https://media.giphy.com/media/lXDmY2KXEyhUzhzKOQ/giphy.gif)
![](https://media.giphy.com/media/nbQo0uR9HpPQnYt2bG/giphy.gif)

---

## Networking
Spoonacular API Documentation: [https://spoonacular.com/food-api/docs](https://spoonacular.com/food-api/docs)

- **Base URL:** https://api.spoonacular.com/

| HTTP Verb | Endpoint                        | Description                                 |
|-----------|----------------------------------|---------------------------------------------|
| GET       | recipes/findByIngredients        | get recipes by ingredients                  |
| GET       | food/trivia/random              | get random food trivia                      |
| GET       | recipes/{id}/card               | get a recipe card for a recipe              |
| GET       | recipes/complexSearch           | get recipes by using advanced filtering     |
| GET       | food/products/upc/{upc}/        | get info about a packaged food by its UPC   |

---

## Getting Started

### Prerequisites
- Android Studio (latest version recommended)
- Android device or emulator
- JDK 11 or higher

### Setup Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/bmaxwellcoder/chefschoice.git
   ```
2. Open the project in Android Studio.
3. Let Gradle sync and download dependencies.
4. Connect your Android device or start an emulator.
5. Click **Run** to build and launch the app.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
