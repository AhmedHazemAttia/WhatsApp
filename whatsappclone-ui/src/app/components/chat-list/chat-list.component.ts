import {Component, input, InputSignal, output} from '@angular/core';
import {ChatResponse} from '../../services/models/chat-response';
import {DatePipe} from '@angular/common';
import {UserResponse} from '../../services/models/user-response';
import {UserService} from '../../services/services/user.service';
import {ChatService} from '../../services/services/chat.service';
import {KeycloakService} from '../../utills/keycloak/keycloak.service';

@Component({
  selector: 'app-chat-list',
  imports: [
    DatePipe
  ],
  templateUrl: './chat-list.component.html',
  styleUrl: './chat-list.component.scss'
})
export class ChatListComponent {

  chats: InputSignal<ChatResponse[]> = input<ChatResponse[]>([]);
  searchNewContact: boolean = false;
  contacts: Array<UserResponse> = []
  chatSelected =output<ChatResponse>()
  constructor(
    private userService: UserService,
    private chatService: ChatService,
    private keycloakService: KeycloakService
  ) {}



  searchContact() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.contacts = users;
        this.searchNewContact = true;
      }
    })

  }

  chatClicked(chat: ChatResponse) {
    this.chatSelected.emit(chat);

  }

  wrapMessage(lastMessage: string | undefined):string {
    if(lastMessage && lastMessage.length <= 20){
      return lastMessage;
    }
    return lastMessage?.substring(0, 17) + '...'
  }

  selectContact(contact: UserResponse) {

    if (!contact) {
      console.error('Contact is null or undefined.');
      return;
    }

    // Check if the chat already exists
    const chatExists = this.chats().some(
      (chat: ChatResponse):boolean =>
        chat.senderId === this.keycloakService.userId &&
        chat.receiverId === contact.id
    );

    if (chatExists) {
      console.log('Chat with this contact already exists. No action taken.');
      return;
    }


    this.chatService.createChat({
      'sender_id': this.keycloakService.userId as string,
      'receiver_id': contact.id as string
    }).subscribe({
      next: (res) => {
        const chat: ChatResponse = {
          id: res.response,
          name: contact.firstName + ' ' + contact.lastName,
          recipientOnline: contact.online,
          lastMessageTime: contact.lastSeen,
          senderId: this.keycloakService.userId,
          receiverId: contact.id
        };
        this.chats().unshift(chat);
        this.searchNewContact = false;
        this.chatSelected.emit(chat);
      }
    });

  }
}
