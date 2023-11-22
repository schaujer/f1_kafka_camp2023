import numpy as np
import pandas as pd
from fastf1.core import Session

from InputGeneration.constants import LAPS_TOPIC, SECTOR_TOPIC


def setup_laps_update(global_start_time: float, session: Session):
    mylaps = session.laps
    mylaps['DataType'] = LAPS_TOPIC
    mylaps['LapTime'] = mylaps['LapTime'].dt.total_seconds()
    mylaps['Timestamp'] = mylaps['Time'].dt.total_seconds() + global_start_time
    mylaps = mylaps[['DataType',
                     'LapNumber',
                     'LapTime',
                     'Driver',
                     'Position',
                     'Timestamp']]
    sorted_laps = mylaps.sort_values('Timestamp')
    laps = sorted_laps.to_dict('records')
    return laps


def setup_sector_updates(global_start_time: float, session: Session):
    mylaps = session.laps
    mylaps['DataType'] = SECTOR_TOPIC
    mylaps['Sector1Time'] = mylaps['Sector1Time'].dt.total_seconds()
    mylaps['Sector2Time'] = mylaps['Sector2Time'].dt.total_seconds()
    mylaps['Sector3Time'] = mylaps['Sector3Time'].dt.total_seconds()

    sector1 = mylaps[['DataType', 'Driver', 'LapNumber', 'Sector1Time', 'Sector2Time', 'Sector3Time']]
    sector2 = mylaps[['DataType', 'Driver', 'LapNumber', 'Sector1Time', 'Sector2Time', 'Sector3Time']]
    sector3 = mylaps[['DataType', 'Driver', 'LapNumber', 'Sector1Time', 'Sector2Time', 'Sector3Time']]
    sector1['Sector2Time'] = None
    sector1['Sector3Time'] = None
    sector2['Sector3Time'] = None
    sector1['Timestamp'] = mylaps['Sector1SessionTime'].dt.total_seconds() + global_start_time
    sector2['Timestamp'] = mylaps['Sector2SessionTime'].dt.total_seconds() + global_start_time
    sector3['Timestamp'] = mylaps['Sector3SessionTime'].dt.total_seconds() + global_start_time
    df = pd.concat([sector1, sector2, sector3])
    df = df.sort_values('Timestamp')
    df = df.to_dict('records')
    return df

