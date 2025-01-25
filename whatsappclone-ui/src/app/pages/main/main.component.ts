import {Component, OnInit} from '@angular/core';
import {ChatListComponent} from '../../components/chat-list/chat-list.component';
import {ChatResponse} from '../../services/models/chat-response';
import {ChatService} from '../../services/services/chat.service';
import {KeycloakService} from '../../utills/keycloak/keycloak.service';

@Component({
  selector: 'app-main',
  imports: [
    ChatListComponent
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit{

  chats: Array<ChatResponse> =[];

  constructor(
    private chatService: ChatService,
    private keyCloak: KeycloakService
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
}
