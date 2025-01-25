import {Component, OnInit} from '@angular/core';
import {ChatListComponent} from '../../components/chat-list/chat-list.component';
import {ChatResponse} from '../../services/models/chat-response';
import {ChatService} from '../../services/services/chat.service';
import {KeycloakService} from '../../utills/keycloak/keycloak.service';
import {MessageService} from '../../services/services/message.service';
import {MessageResponse} from '../../services/models/message-response';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-main',
  imports: [
    ChatListComponent,
    DatePipe
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit{

  chats: Array<ChatResponse> =[];
  selectedChat: ChatResponse = {}
  chatMessages: MessageResponse[] = []

  constructor(
    private chatService: ChatService,
    private keyCloak: KeycloakService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.getAllChats()
    }


  private getAllChats(){
    this.chatService.getChatsByReceiver()
      .subscribe({
        next: (res) => {
          this.chats = res;
        }
      })
  }


  userProfile() {
    this.keyCloak.accountManagement()
  }

  logout() {
    this.keyCloak.logout()

  }

  chatSelected(chatResponse: ChatResponse) {
    this.selectedChat = chatResponse;
    this.getAllChatMessages(chatResponse.id as string);
    this.setMessagesToSeen();
    // this.selectedChat.unreadCount = 0;

  }

  private getAllChatMessages(chatId: string) {
    this.messageService.getMessages({
      'chat-id': chatId
    }).subscribe({
      next: (messages) => {
        this.chatMessages = messages;
      }
    })
  }

  private setMessagesToSeen() {

  }

  isSelfMessage(message: MessageResponse):boolean {
    return message.senderId === this.keyCloak.userId;
  }
}
