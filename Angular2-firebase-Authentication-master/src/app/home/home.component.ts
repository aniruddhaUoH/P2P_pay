import { Component } from '@angular/core';
import { AngularFire } from 'angularfire2';
import {UserDetailsService} from '../user-details.service';
import {Router} from '@angular/router';
import {LocationStrategy} from '@angular/common';
import {HomeService} from "./home.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  user = null;
  logged_in = false;
  provider = null;
  constructor(private af: AngularFire, private userDetailsService: UserDetailsService,
              private router: Router, private homeService: HomeService,
              private url: LocationStrategy) {
    this.user = userDetailsService.getUserDetails();
  }

  logout() {
    this.af.auth.logout().then(() => {
      this.logged_in = false;
      this.user = {};
      this.provider = null;
      this.homeService.destroySession().then(response => {
        console.log(response.json());
      }).catch((error: any) => {
        return console.log(error);
      });
      this.router.navigate(['/']);
    });
  }
}
