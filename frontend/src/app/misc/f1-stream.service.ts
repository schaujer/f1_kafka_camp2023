import {Injectable} from '@angular/core';
import {Message, Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
    providedIn: 'root'
})
export class LapseStreamService {

    private baseUrl =  'http://localhost:4200';


    constructor() {
        this.initializeWebSocketConnection();
    }

    initializeWebSocketConnection() {
        const socket = new SockJS(`${this.baseUrl}/live-temperature`);
        const client = Stomp.over(socket);

        client.connect({}, (frame: any) => {
            console.log('Connected: ' + frame);
            client.subscribe('/topic/temperature', (message: Message) => {
                console.log(JSON.parse(message.body));
            });
        }, (error: any) => {
            console.error('Error in STOMP connection: ', error);
        });
    }
}
