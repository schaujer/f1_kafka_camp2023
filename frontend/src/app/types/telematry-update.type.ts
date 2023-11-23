export interface TelemetryUpdateDTO {
    timestamp: number
    speed: number
    rpm: number
    throttle: number
    break: boolean
    drs: boolean
    x: number
    y: number
    z: number
}
