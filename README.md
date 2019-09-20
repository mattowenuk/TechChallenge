# TechChallenge

Sport App Dev Tech Challenge
BBC Sport App Developer

• make a call to a url to retrieve some data
• represent that data in an application
• display that data in a user friendly way, similar to the screenshot above.
• write stats about app usage to a url

• use any frameworks you want to help you create it

Spec
• Read some sport story data (json)
• Parse the data
• Display the data in the app in a way similar to the image above
• Record statistics about the app use:
◦ The command is “event” and the parameters are “load”, “display” and “error”.
◦ “load” and “display” should be followed by a “data” parameter containing the time in
milliseconds.
◦ “error” should be followed by a “data” parameter containing an error reason string.

Data
We've provided a json feed at https://bbc.github.io/sport-app-dev-tech-challenge/data.json
We've provided an endpoint for the stats at https://bbc.github.io/sport-app-dev-tech-challenge/
stats

Example stats:
https://bbc.github.io/sport-app-dev-tech-challenge/stats?event=display&data=1235
https://bbc.github.io/sport-app-dev-tech-challenge/stats?
event=error&data=its%20all%20gone%20to%20pot

-----------------------------------------

Frameworks used:

kotlin extensions
Anko
Gson
Picasso
OkHttp

The navigation activity uses the 4 tabs to load four different fragments.
The MySport fragment is the only one that has been developed further.
This contains the list of articles and selecting an article will replace the fragment with another containing a webView.
The article url is loaded in the webView with the ".app" suffix.
The back navigation tracks the fragment transactions and tab selections back to the original Home fragment before closing the app.
