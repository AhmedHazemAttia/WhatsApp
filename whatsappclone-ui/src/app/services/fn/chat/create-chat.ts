/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { StringResponse } from '../../models/string-response';

export interface CreateChat$Params {
  sender_id: string;
  receiver_id: string;
}

export function createChat(http: HttpClient, rootUrl: string, params: CreateChat$Params, context?: HttpContext): Observable<StrictHttpResponse<StringResponse>> {
  const rb = new RequestBuilder(rootUrl, createChat.PATH, 'post');
  if (params) {
    rb.query('sender_id', params.sender_id, {});
    rb.query('receiver_id', params.receiver_id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<StringResponse>;
    })
  );
}

createChat.PATH = '/api/v1/chats';