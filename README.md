# BookListing
This is a book-searching app. You put keywords to the wanted genre or topic and the app will come up with related books.
## Table of contents
* [Getting Started](https://github.com/Willyyyy9/BookListing/new/master?readme=1#getting-started)
* [Description](https://github.com/Willyyyy9/BookListing/new/master?readme=1#description)
* [Gradle Configuration](https://github.com/Willyyyy9/BookListing/new/master?readme=1#gradle-configuration)
* [Developers](https://github.com/Willyyyy9/BookListing/new/master?readme=1#developers)
* [Notes](https://github.com/Willyyyy9/BookListing/new/master?readme=1#notes)

## Getting Started

1.  Pull down the code locally by clicking on Download ZIP
2.  Open Android Studio and select 'Open an existing Android Studio Project'
3.  Navigate to checked out repository.
4.  Select 'BookListing'
5.  Open the project
6.  Connect your device/emulator and run the application.

## Description

Book Listing app would allow a user to get a list of published books on a given topic.
[Google Books API](https://developers.google.com/books/docs/v1/getting_started#intro) is used in order to fetch results and display them to the user.<br/>
A user is able to enter a keyword, press the search button, and recieve a list of published books which relate to that keyword.
All API querying processes are done in an AsyncTask background thread.<br/> Once the query is done, the result is parsed.
This involves storing the information returned by the API in a custom class.<br/>
Finally, list and Adapter pattern is used to populate a list on the user's screen with the information stored in the custom objects which was written earlier.


This project is about combining various ideas and skills including:
* Fetching data from an API.
* Using an AsyncTask.
* Parsing a JSON response.
* Creating a list based on that data and displaying it to the user. <br/>


## Gradle Configuration
`minSdkVersion 21` <br/>
`versionCode 1`


## Developers
This app was fully developed by [Mohamed Walaa Eldeen](https://github.com/Willyyyy9)


## Notes
This project was inspired from a task in Google's [Networking](https://classroom.udacity.com/courses/ud843) course in Udacity. <br/>
All vectors and icons were downloaded from [FlatIcon](https://www.flaticon.com/) and similar opensource websites. <br/>
[StackOverFlow](https://stackoverflow.com/) and other similar websites were used to assist me in developing this app.




