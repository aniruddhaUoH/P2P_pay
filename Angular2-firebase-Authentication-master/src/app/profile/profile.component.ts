import {Component, OnChanges, OnInit} from '@angular/core';
import {AngularFire} from 'angularfire2';
import {UserDetailsService} from '../user-details.service';
import {FormBuilder} from '@angular/forms';
import {UserDetail} from "../UserDetails";
import {ProfileService} from "./profile.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userDetails: UserDetail[];
  user: null;
  gender: any;
  editMode: boolean ;
  public saveMode = false;
  public showMode = true;
  constructor(private af: AngularFire,
              private userDetailsService: UserDetailsService,
              private  profileService: ProfileService) {
    this.user = userDetailsService.getUserDetails();
    this.gender = {
      type: 'Male'
    };
  }
  ngOnInit() {
    this.profileService.getDetails()
      .subscribe(data => {
        this.userDetails = data;
      });
  }
  edit() {
    this.editMode = true;
    this.showMode = false;
    this.saveMode = true;
  }
  onSaveDetails(value: any) {
    let userData = {
      'phone': value.phone,
      'gender': value.type
    };
    this.profileService.updateDetails(userData).then((response) => {
    }).catch((error) => {
      console.log(error);
    });
  }
  onRefresh() {
    window.location.reload();
  }
}

