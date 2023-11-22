import {Injectable} from '@angular/core';
import {Message, Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {BehaviorSubject} from "rxjs";
import {LapsUpdateDTO} from "../types/lapse-update.type";

@Injectable({
    providedIn: 'root'
})
export class LapseStreamService {

    private baseUrl =  'http://localhost:4200';
    private lapsUpdates = new BehaviorSubject<LapsUpdateDTO | null>(null);


    constructor() {
        this.initializeWebSocketConnection();
    }

    initializeWebSocketConnection() {
        const socket = new SockJS(`${this.baseUrl}/websocket`);
        const client = Stomp.over(socket);
        client.debug = function (){};//do nothing
        client.connect({}, (frame: any) => {
            client.subscribe('/topic/laptimes', (message: Message) => {
                const update = JSON.parse(message.body);
                this.lapsUpdates.next(update);
            });
        }, (error: any) => {
            console.error('Error in STOMP connection: ', error);
        });
    }

    getLapsUpdates() {
        return this.lapsUpdates.asObservable();
    }
}
