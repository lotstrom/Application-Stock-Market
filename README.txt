README

Project: Software Architecture for Distributed Systems
Group Blue 




1. Database

In order to access the database and view the database:

1. Log on to www.cloudant.com.
2. Click the login button to the top left.
3. The username is “stockin” and the password is “jupiter05”.
4. Click on stockin under your databases
5. Under all documents scrolling down you can see all our design documents aswell as the stock info.






2. ETL Backend

This guide will help you start the process of extracting, transformning and loading the JSON data to our database (CouchDB).

Necessary tools
Erlang (Shell) based on your OS - 
http://www.erlang.org/download.html
TextReader of your choice(Note++, Sublime Text 2) to view .erl source files.

Starting ETL (NOTE! This should be done after 4pm swedish time, due to markets opening time in USA)

1. Start up the erlang shell(if on mac os, open up terminal and type in erl and hit enter).
2. Find one of projectblue files and copy its location.
3. type in cd(“Copy-Paste the location of one of the files”).
4. To start up the server and supervisor, type in app:start(). 
5. To parse information from different sources, app:parse(“sourcename”).
6. We have three different markets, “google”, “yahoo”, “markitondemand”.







3. Website

Necessary tools
Webbrowser Google Chrome - 
https://www.google.com/intl/sv/chrome/browser/
TextReader of your choice(Note++, Sublime Text 2) to view the source codes.
Ruby 1.8.7  - http://rubyinstaller.org/

Access website

Local

1. Navigate to the website folder
2. For OSX without RVM for other distros please omit the sudo where relevant
3. Run the following commands
sudo gem install bundler
sudo bundle install
shotgun
4. A web server will now be present at 127.0.0.1:9393.

Online

1. Copy & paste http://radiant-hamlet-6424.herokuapp.com/ into browser to access the live website.







4. Java Application

This guide will help you setup our java desktop application on your computer. There are some tools that needs to be downloaded before you can configure our project, they are listed below.

Necessary tools
Java (JDK) based on your OS – http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
Eclipse based on your OS – http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1
Tutorial on installation of both Java JDK and eclipse can be found at 
http://www.wikihow.com/Download,-Install,-and-Run-JDK-and-Eclipse

Jar File

1. Locate runnable jar file inside the Group Blue - Desktop, double click to run.
 
Opening source files - Importing project
1.       Go to your workspace that you specified when installing eclipse. Usually it’s located in C:\Users\username\workspace.
2.       Create an empty folder called “Blue_Final_Version”.
3.       Drag the “Blue_Final_Version.zip” from our project folder to the created folder in your workspace.
4.       Right click on the zip file and press “Extract here”
5.       Start eclipse and go to “File -> New -> Java Project”.
6.       In the “Project name” type in the same name as the folder you created before, it should be “Blue_Final_Version” and press finish.
7.       On the left side you can now find our project with all the files inside it, the last step is to build a path to the libraries.
8.       Right click on “Referenced libraries -> Build Path -> Configure build path” 
 
9.    Next step is to press “Add External JARs” and then browse to the project folder we named “Blue_Final_Version”. Locate the “Lib” folder inside it and import all of the libraries inside it including the couchdb, rss, and charts folder.

10.   Right click on the Home.java file and select “Run as -> Java application” and everything should work.







5. Android Application:

Necessary tools
JAVA Development environment
Eclipse IDE
Android SDK
 
1. To download and install JAVA development environment follow next steps:
1.1 Download  Java SE Development Kit 7u45 version which corresponds your operating system (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
1.2 Install JAVA SE Development Kit 7u45 on your computer (follow on-screen instructions)
1.3 Go to the installation folder you specified (on Windows it`s usually C:\Program Files\Java\jdk1.7.0_45\) ; then open \bin folder, right click on any file and choose “properties”
1.4 Under “properties” copy Location path(usually C:\Program Files\Java\jdk1.7.0_45\bin).
1.5 Right click on your computer > choose “Advanced System Settings”> and click on “Environment Variables”. Click the first “New” button and in new dialog box under variable name write “Path” and under variable value paste the path you copied in previous step (usually C:\Program Files\Java\jdk1.7.0_45\bin). Click OK>OK.
 
2. To install Eclipse IDE download Eclipse IDE for Java EE Developers for windows (http://www.eclipse.org/downloads/) or mac (http://www.eclipse.org/downloads/?osType=macosx).
2.1 extract downloaded file to the directory of your choice.
2.2 To run Eclipse, double click on Eclipse.exe in you extracted directory
 
3 To  install Android SDK
3.1 Run Eclipse. Then click Help>Install New Software.
3.2 Next to “Work with” bar click Add. In new dialog window write name “Android Developer Tools”  and for Location paste following link http://dl-ssl.google.com/android/eclipse/ and click next.
3.3 Accept the license agreement, click next Next > Finish.
3.4 Restart Eclipse
3.5 Click Help> heck for Updates to update the ADT
 
 
4 To run android application from source code
4.1 Run Eclipse.
4.2 In top left corner click File>Import>Android>Existing android code into Workspace. The folder name StockIn will appear on your left panel. If you can`t see the panel click Window>Show View>Other>Package Explorer.
4.3 Right click on the folder StockIn choose run as > Android Application and wait until emulator starts the application.
 
 
5 To run Stockin.apk file on you android phone:
5.1 Make sure your phone is running android 4.0.3 Ice Cream Sandwich version or higher
5.2 Got to Setting>Security>Unknown sources and make sure the box is checked.
5.3 Plug your phone to PC, copy Stockin.apk from your computer and paste it to destination folder of your choice on your phone. You can unplug your phone now.
5.4 Find the Stockin.apk file in your phone in the folder you have chosen in above step and click on it. Click install and wait until installation is finished.
