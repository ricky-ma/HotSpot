# Hotspot
*Easily practice social distancing by seeing the busiest crowds in town - and avoiding them*

## Inspiration
Restaurants are one of the few places today where masks are taken off. Due to the pandemic, people prefer to eat at less crowded places.

## What it does
Our app provides a way for users to quickly find less crowded restaurants to dine at while avoiding potential hotspots and super-spreader events. Users are first provided with a heatmap, a large overview of the city where they can decide which areas of the city to avoid at a glance. A simple search operation then displays relative restaurants, where users can view a restaurant's specific crowdedness level and other important details.

## How we built it
Restaurant data are queried from Google using the Google Places API and stored in an Apache Cassandra database using DataStax Astra. Since popular-times data does not update very frequently, and in order to conserve computations, we define a Google Cloud Function and using the Google Cloud Scheduler to provide updates to the database hourly. The Android app is built entirely in Java and data is queried from the database using a REST API. We using Google Place Search to perform search operations and the SDK for Google Maps Android development.

## What's next for Hotspot
To simplify development and conserve compute credits, we have limited the app to a testing area of just Vancouver, Canada, and only update the database hourly. The app, however, can easily be configured to be deployed around the world with real-time hotspot updates. We believe such an app is immensely useful as Covid-19 vaccines are distributed and the world transitions back to normalcy.

## Demo
[![DEMO](http://img.youtube.com/vi/k1GhTtKepxE/0.jpg)](http://www.youtube.com/watch?v=k1GhTtKepxE)

## Built With
- java
- python
- android-studio
- datastax-astra
- google-cloud
- google-places
- cassandra