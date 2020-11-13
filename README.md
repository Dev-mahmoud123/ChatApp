# ChatApp
ChatApp is a project that I started to improve my Android Development knowledge.

It uses [Firebase's](https://firebase.google.com) Authentication/Database/Storage/Messaging/CrashReporting libraries for it's implementation and other libraries
like Glide lib - CircleImage lib - Retrofit lib .

# Preview

![image](https://user-images.githubusercontent.com/73883447/99072533-e7532f80-25bc-11eb-9a0a-1339c7d7f4b2.jpg)

 ### log in
![Log in](https://user-images.githubusercontent.com/73883447/99072654-1d90af00-25bd-11eb-9ad8-f20e6bb6db7b.jpg)

 ### register
![register](https://user-images.githubusercontent.com/73883447/99072973-b3c4d500-25bd-11eb-8ffc-b5f5bca11dab.jpg)

## Features 
- Messaging
  - Send and Receive messages with user
  
- Lists
  - List with your Users
  - List with your Users Who have chat with them

- Profile 
  - Update your Profile Picture
  
- Notifications
  - Notification when you have a new message
  
 deleted google-services.json. Add yours
<br><b> Change Authorization:key with your key from firebase project</b>

<br>Implementation Guide 
<br>1 - Project
<br>1 - Open the Project in your android studio;
<br>2 - *IMPORTANT* Change the Package Name. (https://stackoverflow.com/questions/16804093/android-studio-rename-package)

<br>2 - Firebase Panel
<br>- Create Firebase Project (https://console.firebase.google.com/);
<br>- Import the file google-service.json into your project
<br>- Connect to firebase console authentication and database from your IDE
<br>- in firebase Storage Rules, change value of "allow read, write:" from "if request.auth != null" to "if true;"  
<br>- For sending notification, paste your Firebase project key into your project APIService.java  
<br>- When you change database settings, you likely will need to uninstall and reinstall apps to avoid app crashes due to app caches.
