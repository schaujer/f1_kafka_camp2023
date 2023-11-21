import datetime
import logging

import fastf1

from InputGeneration.constants import LAPS_TOPIC
from InputGeneration.laps import setup_laps
from InputGeneration.my_kafka_producer import MyKafkaProducer


def setup_logging():
    format = "%(asctime)s:.%(msecs)03d [%(name)-23s] [%(levelname)-8s]: %(message)s"
    logging.basicConfig(format=format, level=logging.INFO,
                        datefmt="%Y-%m-%d %H:%M:%S")


def start_simulation():
    global_start_time = datetime.datetime.now().timestamp()
    session = fastf1.get_session(2023, 1, 'R')
    session.load()

    start_time = session.session_start_time.total_seconds()
    producer = MyKafkaProducer(start_delay=start_time,  warp=60 * 20)

    laps = setup_laps(global_start_time, session)
    producer.add_data_source_to_producer(LAPS_TOPIC, LAPS_TOPIC, laps)
    producer.wait_for_finish()


def main():
    setup_logging()
    start_simulation()


if __name__ == '__main__':
    main()
