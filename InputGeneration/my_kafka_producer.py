import datetime
import json
import logging
import threading
import time

from kafka import KafkaProducer


logger = logging.getLogger(__name__)


class MyKafkaProducer:
    BOOTSTRAP_SERVER = "localhost:9093"  # Kafka broker address

    def __init__(self, start_delay, warp):
        self.producer = KafkaProducer(
            bootstrap_servers=self.BOOTSTRAP_SERVER,
            # Use default serialization
            value_serializer=lambda v: v,
            api_version=(2, 0, 2)
        )
        self.start_delay = start_delay
        self.warp = warp
        self.threads = []

    def wait_for_finish(self):
        for thread in self.threads:
            thread.join()

    def add_data_source_to_producer(self, name, topic, ds):
        new_thread = threading.Thread(name=name, target=self.execute_ds, args=(topic, ds, ))
        new_thread.start()
        self.threads.append(new_thread)

    def execute_ds(self, topic, ds):
        for el in ds:
            timestamp = el['Timestamp']
            if timestamp < self._current_sim_time() - 0.001:
                logger.info(f'Skip event for topic {topic} since its in the past')
                continue
            time_to_sleep = max(0, timestamp - self._current_sim_time())
            time.sleep(time_to_sleep)
            self._produce_dict(topic, el)

    def _produce_dict(self, topic, data):
        logger.info(f'Produce {data["DataType"]} into topic {topic}')
        self.producer.send(topic, json.dumps(data).encode("utf-8"))

    def _current_sim_time(self):
        return datetime.datetime.now().timestamp() + datetime.timedelta(seconds=self.start_delay+self.warp).total_seconds()

