import {Component, OnInit} from '@angular/core';
import {InviteFriendsService} from './invite.friends.service';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {SearchFriend} from '../search.friends';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-invite',
  templateUrl: './invite.friends.component.html',
  styleUrls: ['./invite.friends.component.css']
})
export class InviteFriendsComponent {
  public visibility = true;
  length: any;
  searchFriends: SearchFriend[];
  constructor(private inviteFriendsService: InviteFriendsService, private router: Router,
              private http: Http) {}
  onSearch(value: any) {
    event.preventDefault();
    this.inviteFriendsService.onFriendSearch(value.friendName).subscribe(friends => {
      this.searchFriends = friends;
        console.log('friends : ' + this.searchFriends);
    }, () => {}, () => {this.length = this.searchFriends.length});
  }
  onSendRequest(searchFriend: any) {
    console.log('=---> ' + searchFriend);
    this.inviteFriendsService.onRequestSend(searchFriend.username).then((response) => {
      // this.visibility = false;
      searchFriend.status = 0;
    }).catch((error) => {
      console.log(error);
    });
  }
}
