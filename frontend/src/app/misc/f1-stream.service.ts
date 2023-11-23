import {Injectable} from '@angular/core';
import {Client, CompatClient, Message, Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {BehaviorSubject, Observable, of, Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class LapseStreamService {

    private baseUrl =  'http://localhost:4200';
    private client: CompatClient | undefined;
    private topicSubscriptions = new Map<string, Subject<any>>();
    private connectionReady = new BehaviorSubject<boolean>(false);


    constructor() {
        this.initializeWebSocketConnection();
    }

    private initializeWebSocketConnection() {
        const socket = new SockJS(`${this.baseUrl}/websocket`);
        this.client = Stomp.over(socket);
        this.client.debug = function (){};//do nothing
        this.client.connect({}, (frame: any) => {
            console.log('Connected: ' + frame);
            this.connectionReady.next(true);
        }, (error: any) => {
            console.error('Error in STOMP connection: ', error);
        });
    }

    subscribeToTopic(topic: string): Observable<any> {
        const subject = new Subject<any>();
        this.connectionReady.subscribe(connected => {
            if (connected && !this.topicSubscriptions.has(topic)) {
                this.topicSubscriptions.set(topic, subject);
                // @ts-ignore
                this.client.subscribe(`/topic/${topic}`, (message: Message) => {
                    subject.next(JSON.parse(message.body));
                });
            }
        });

        return subject.asObservable();
    }

    unsubscribeFromTopic(topic: string) {
        if (this.topicSubscriptions.has(topic)) {
            // Unsubscribe from the STOMP topic
            // Note: You should keep track of the subscription returned by client.subscribe and use it to unsubscribe.
            this.topicSubscriptions.delete(topic);
        }
    }
}
