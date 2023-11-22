import datetime
import logging

import fastf1

from InputGeneration.constants import LAPS_TOPIC, SECTOR_TOPIC
from InputGeneration.laps import setup_laps_update, setup_sector_updates
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
    producer = MyKafkaProducer(start_delay=start_time,  warp=60*20)

    try:
        laps = setup_laps_update(global_start_time, session)
        producer.add_data_source_to_producer(LAPS_TOPIC, LAPS_TOPIC, laps)

        sectors = setup_sector_updates(global_start_time, session)
        producer.add_data_source_to_producer(SECTOR_TOPIC, SECTOR_TOPIC, sectors)

        producer.wait_for_finish()
    finally:
        producer.stop_signal.set()


def main():
    setup_logging()
    start_simulation()


if __name__ == '__main__':
    main()
