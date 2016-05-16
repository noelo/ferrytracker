# ferrytracker
Demo project using the Transport NSW OpenData project

Requires an api key and shared secret which can be created at https://opendata.transport.nsw.gov.au/site/en_us/home.html
Once logged into the website, create an application and attach the Public Transport - Realtime - Vehicle Positions api.

The API key needs to be user scoped and confidential.

Once created retrieve the shared secret via Application -> Edit -> Auth tab.

Inject the apikey/shared secret combo into the Openshift application using an environment variable

e.g. oc env dc/ferryapp SPRING_APPLICATION_JSON='{"pvkey": "l7xxxxxxxxxxxxxx", "sharedsecret":"c....xxxxxxxx"}'


All transport data come from the Transport for NSW web site https://opendata.transport.nsw.gov.au and is used under the terms of the Creative Commons Attribution 4.0.
Terms are available here https://opendata.transport.nsw.gov.au/site/en_us/data-licence.html

It also contains elements of the map-icons project see https://github.com/scottdejonge/map-icons.git

