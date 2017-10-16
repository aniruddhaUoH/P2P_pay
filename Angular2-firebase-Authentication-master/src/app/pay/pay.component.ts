import {Component, OnInit} from '@angular/core';
import {PayService} from './pay.service';
import {Router} from '@angular/router';
import {ListFriendsService} from '../my_networks/list_friends/list.friends.service';
import {Friend} from '../Friends';
import {Http} from '@angular/http';
import {ProfileService} from '../profile/profile.service';
import {UserDetail} from '../UserDetails';

@Component({
  selector: 'app-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.css']
})
export class PayComponent implements OnInit{
  userDetails: UserDetail[];
  friends: Friend[];
  userMessage: any;
  isVisible: boolean = false ;
  username: any;
  constructor(private payService: PayService,
              private listFriendsService: ListFriendsService,
              private profileService: ProfileService,
              private router: Router, private http: Http) {}
  ngOnInit() {
    this.listFriendsService.getFriends()
      .subscribe(data => {
        this.friends = data;
      });
    this.profileService.getDetails().subscribe(data => {
      this.userDetails = data;
    });
  }
  onClickToPay(form: any) {
    let value = form.value;
    let payFormData = {
      'merchant': value.username,
      'withdrawl': value.withdrawl,
      'description': value.description
    }
    console.log('username is =---> ' + value.username);
    form.resetForm();
    this.payService.payToFriend(payFormData).then((response) => {
      this.isVisible = true;
      if (response.text() === 'success') {
        this.userMessage = 'You have paid successfully';
        this.profileService.getDetails().subscribe(data => {
          this.userDetails = data;
        });
      } else {
        this.userMessage = 'It could not be paid';
      }
    }).catch((error) => {
      console.log(error);
    });
  }
}
