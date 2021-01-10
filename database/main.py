import os
import requests
import json
import datetime
import logging
from string import Template
# from dotenv import load_dotenv
import populartimes
from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

# load_dotenv("../.env")
places_api_key = os.getenv("PLACES_API_KEY")
radar_test_api_key = os.getenv("RADAR_TEST_PUBLISHABLE")
astradb_keyspace = os.getenv("ASTRA_DB_KEYSPACE")
astradb_user = os.getenv("ASTRA_DB_USERNAME")
astradb_pw = os.getenv("ASTRA_DB_PASSWORD")

cloud_config = {'secure_connect_bundle': 'secure-connect-nwhax-database.zip'}
auth_provider = PlainTextAuthProvider(astradb_user, astradb_pw)
cluster = Cluster(cloud=cloud_config, auth_provider=auth_provider)
session = cluster.connect()


def google_query_places(query, inputtype="textquery", fields="place_id", coords="49.2827,-123.1207", radius=100):
    rqst = (
        "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?"
        "input={}&"
        "inputtype={}&"
        "fields={}&"  # fields to return
        "locationbias=circle:{}@{}&"  # radius to search in metres, string in the format latitude,longitude
        "key={}"
    ).format(query, inputtype, fields, radius, coords, places_api_key)
    response = requests.post(rqst)
    print(response)
    with open('places.json', 'w') as json_file:
        json.dump(response, json_file)


def execute_update():
    response = populartimes.get(
        api_key=places_api_key,
        types=["restaurant"],
        p1=(49.231913, -123.267898),
        p2=(49.290547, -123.103937),
        radius=10000,
        n_threads=20,
        all_places=False  # only return places w/ popular times
    )
    print(len(response))
    print(response)

    for place in response:
        place['latitude'] = place['coordinates']['lat']
        place['longitude'] = place['coordinates']['lng']
        place['poptime_mon'] = place['populartimes'][0]['data']
        place['poptime_tue'] = place['populartimes'][1]['data']
        place['poptime_wed'] = place['populartimes'][2]['data']
        place['poptime_thr'] = place['populartimes'][3]['data']
        place['poptime_fri'] = place['populartimes'][4]['data']
        place['poptime_sat'] = place['populartimes'][5]['data']
        place['poptime_sun'] = place['populartimes'][6]['data']
        [place.pop(key) for key in ["populartimes", "time_spent", "time_wait", "coordinates"] if key in place.keys()]
        place = json.dumps(place).replace("'", "''")
        print(place)
        operation = "INSERT INTO nwhax_data.places JSON \'{}\';".format(place)
        session.execute(operation)


def main(data, context):
    """Triggered from a message on a Cloud Pub/Sub topic.
    Args:
        data (dict): Event payload.
        context (google.cloud.functions.Context): Metadata for the event.
    """

    try:
        current_time = datetime.datetime.utcnow()
        log_message = Template('Cloud Function was triggered on $time')
        logging.info(log_message.safe_substitute(time=current_time))

        try:
            execute_update()

        except Exception as error:
            log_message = Template('Query failed due to '
                                   '$message.')
            logging.error(log_message.safe_substitute(message=error))

    except Exception as error:
        log_message = Template('$error').substitute(error=error)
        logging.error(log_message)


if __name__ == "__main__":
    # query = "restaurant"
    # fields = "place_id,photos,formatted_address,name,opening_hours,rating"
    # google_query_places(query=query, fields=fields)
    main('data', 'context')
