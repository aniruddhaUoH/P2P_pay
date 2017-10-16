import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UnloadMoneyService} from './unload.money.service';
import {Headers, Http, Response} from '@angular/http';
import {NgForm} from '@angular/forms';
import {ProfileService} from "../../profile/profile.service";
import {UserDetail} from "../../UserDetails";

@Component({
  selector: 'app-unload-money',
  templateUrl: './unload.money.component.html',
  styleUrls: ['./unload.money.component.css']
})
export class UnloadMoneyComponent implements OnInit {
  userDetails: UserDetail[];
  userMessage: any;
  isVisible: boolean = false ;
  constructor(private unloadMoneyService: UnloadMoneyService,
              private profileService: ProfileService,
              private router: Router, private http: Http) {}
  ngOnInit() {
    this.profileService.getDetails().subscribe(data => {
      this.userDetails = data;
    });
  }
  onUnloadMoney(form: any) {
    let value = form.value;
    event.preventDefault();
    let unloadFormData = {
      'withdrawl': value.withdrawl
    };
    form.resetForm();
    this.unloadMoneyService.unloadAmount(unloadFormData).then((response) => {
      this.isVisible = true;
      if (response.text() === 'success') {
        this.userMessage = 'Wallet unloaded Successfully';
        this.profileService.getDetails().subscribe(data => {
          this.userDetails = data;
        });
      } else {
        this.userMessage = 'Wallet could not be unloaded';
      }
    }).catch((error) => {
    });
  }
}
