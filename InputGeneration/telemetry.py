import numpy as np
import pandas as pd
from fastf1.core import Session

from constants import LAPS_TOPIC, SECTOR_TOPIC, TELEMETRY_TOPIC


def setup_telemetry_by_driver(global_start_time: float, session: Session, driver: str):
    telemetry = session.laps.pick_driver(driver).get_telemetry()


    telemetry['DataType'] = TELEMETRY_TOPIC
    telemetry['Driver'] = driver
    telemetry['Timestamp'] = telemetry['SessionTime'].dt.total_seconds() + global_start_time
    telemetry = telemetry[['DataType',
                           'Driver',
                     'DriverAhead',
                     'DistanceToDriverAhead',
                     'RPM',
                     'Speed',
                     'nGear',
                     'Throttle',
                     'Brake',
                     'DRS',
                     'Distance',
                     'RelativeDistance',
                     'Timestamp']]
    df = telemetry.sort_values('Timestamp')
    dicted = df.to_dict('records')
    return dicted
