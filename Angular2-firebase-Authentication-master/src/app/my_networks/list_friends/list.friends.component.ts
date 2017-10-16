import {Component, OnInit} from '@angular/core';
import {Friend} from '../../Friends';
import {ListFriendsService} from './list.friends.service';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {Request} from '../Requests';

@Component({
  selector: 'app-list',
  templateUrl: './list.friends.component.html',
  styleUrls: ['./list.friends.component.css']
})
export class ListFriendsComponent implements OnInit {
  friends: Friend[];
  requests: Request[];
  length: any;
  constructor(private listFriendsService: ListFriendsService,
              private router: Router, private http: Http) { }
  ngOnInit() {
    this.listFriendsService.getFriends()
      .subscribe(data => {
        this.friends = data;
      });
    this.listFriendsService.getRequests()
      .subscribe(data => {
        this.requests = data;
        console.log('=---> requests ' + this.requests);
      }, () => {}, () => {this.length = this.requests.length});
  }
  onRequestAccept(value: any) {
    console.log('=---> ' + value);
    this.listFriendsService.onAcceptRequest(value).then((response) => {
      window.location.reload();
    }).catch((error) => {
      console.log(error);
    });
  }
  onRequestReject(value: any) {
    console.log('=---> ' + value);
    this.listFriendsService.onRejectRequest(value).then((response) => {
      window.location.reload();
    }).catch((error) => {
      console.log(error);
    });
  }
}
