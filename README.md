# Baking-App
Submission for Udacity Android Nano-Degreee Project - Baking App

This android project allow a user to select a recipe and see video-guided steps for how to complete it.

## Data
The JSON data from the [source URL](https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json) contains recipes with the following components:

## Navigation Behavior
In the Main Activity, we query the URL data and display a list of available recipes along with the thumbnail images (if any) and serving sizes.

When user clicks on a recipe, the RecipeDetailActivity is loaded with either:
- one fragment (recipe details) if device screen width is <600 pixel (phone), or
- two fragments (recipe details + step details) if device screen width is >600 pixel (tablet).

When user clicks on a step in the Recipe Detail Fragment, the corresponding step details are displayed in the Step Detail Fragment in a new Step Detail acitivity (phone) or the same Recipe Detail activity (tablet).

The Step Detail fragment consists of:
- A video playback if available using Exoplayer,
- Detailed step instructions, and 
- Previous and Next step navigation buttons if device is a phone.

When screen is rotated to landscape in the Step detail activity in the phone, the video is enlarged to fullscreen.

When user leaves the app or rotates the device, video playback and state (play or pause) resumes at the last position.

## Widget
A ompanion homescreen widget is provided which shows the last viewed recipe's list of ingredients in a listview. The data displayed on widget is replaced each time a new recipe is viewed. Clicking on the title (name of the recipe) in the widget launches the app.

## Unit Test
One unit test using Espresso has been added for the Main Activity:
- When user clicks on item 1 in the main recyclerview, the title in the resulting activity's ingredient title field is checked to see if the correct recipe is loaded.

## Third party libraries
This app uses the following 3rd party libraries
- Picasso for loading images
- Retrofit for parsing JSON data into Java objects
- Exoplayer for video playback

## Lessons Learned
In this project we:
- Used MediaPlayer/Exoplayer to display videos.
- Handled error cases in Android.
- Added a widget to your app experience.
- Leveraged third-party libraries.
- Used Fragments to create a responsive design that works on phones and tablets.
- Used Espresso to perform a simple unit test.
