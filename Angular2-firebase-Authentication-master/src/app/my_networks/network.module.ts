import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {routing} from '../app.routing';
import {InviteFriendsComponent} from './invite_friends/invite.friends.component';
import {ListFriendsComponent} from './list_friends/list.friends.component';
import {ListFriendsService} from './list_friends/list.friends.service';
import {InviteFriendsService} from './invite_friends/invite.friends.service';

@NgModule({
  declarations: [ListFriendsComponent, InviteFriendsComponent],
  imports: [BrowserModule,
    FormsModule,
    HttpModule,
    routing],
  providers: [ListFriendsService, InviteFriendsService]
})
export class NetworkModule {}
