/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

export interface MessageRequest {
  chatId?: string;
  content?: string;
  receiverId?: string;
  senderId?: string;
  type?: 'TEXT' | 'AUDIO' | 'VIDEO' | 'IMAGE';
}