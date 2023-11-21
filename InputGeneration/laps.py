from fastf1.core import Session

from InputGeneration.constants import LAPS_TOPIC


def setup_laps(global_start_time: float, session: Session):
    mylaps = session.laps
    mylaps['DataType'] = LAPS_TOPIC
    mylaps['LapDuration'] = mylaps['Time'] - mylaps['LapStartTime']
    mylaps['LapDuration'] = mylaps['LapDuration'].dt.total_seconds()
    mylaps['LapStartTime'] = mylaps['LapStartTime'].dt.total_seconds()
    mylaps['Time'] = mylaps['Time'].dt.total_seconds()
    mylaps['Timestamp'] = mylaps['Time'] + global_start_time
    mylaps = mylaps[['DataType',
                     'LapNumber',
                     'LapStartTime',
                     'LapDuration',
                     'Time',
                     'Driver',
                     'Position',
                     'Timestamp']]
    sorted_laps = mylaps.sort_values('Timestamp')
    laps = sorted_laps.to_dict('records')
    return laps