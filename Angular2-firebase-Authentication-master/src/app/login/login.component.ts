import { Component } from '@angular/core';
import {AngularFire, AuthProviders} from 'angularfire2';
import {Router} from '@angular/router';
import {LocationStrategy} from '@angular/common';
import {LoginService} from './login.service';
import {Headers} from '@angular/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  user = null;
  logged_in = false;
  provider = null;
  idToken: any;
  Email: any;
  constructor(private af: AngularFire,
              private router: Router,
              private url: LocationStrategy,
              private loginService: LoginService) {
    this.af.auth.subscribe(user => {
      if (user) {
        this.user = user;
      }else {
        this.user = {};
      }
    });
  }

  facebookLogin() {
    this.af.auth.login({
      provider: AuthProviders.Facebook,
    }).then((user) => {
      this.user = user;
      console.log(this.user);
      if (this.user.auth.emailVerified === false) {
        this.af.auth.getAuth().auth.sendEmailVerification();
        this.router.navigate(['/app-login']);
      } else {
        this.provider = 'Facebook';
        this.logged_in = true;
        let userDetails = {
          'username': this.user.auth.displayName,
          'email': this.user.auth.email
        };
        this.loginService.addUserDetails(userDetails).then((response) => {
          this.router.navigate(['/app-home']);
        }).catch((error) => {
          console.log(error);
        });
      }
      /*this.idToken = this.user.auth.uid;
      console.log('token: ' + this.idToken);
      this.loginService.verifyToken(this.idToken).then(response => {
        if (response.text() === 'fail') {
          this.router.navigate(['/app-login']);
        }
      });*/
    });
  }
 /*   facebookLogin() {
    this.af.auth.login({
      provider: AuthProviders.Facebook,
    }).then((user) => {
      this.user = user;
      console.log(this.user);
      this.provider = 'Facebook';
      this.logged_in = true;
      let userDetails = {
        'username': this.user.auth.displayName,
        'email': this.user.auth.email
      };
      this.loginService.addUserDetails(userDetails).then((response) => {
        this.router.navigate(['/app-home']);
      }).catch((error) => {
        console.log(error);
      });
    });
  }*/
}
