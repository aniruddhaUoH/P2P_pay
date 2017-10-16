import {AngularFire} from 'angularfire2';
import {Injectable} from '@angular/core';
@Injectable()

export class UserDetailsService {
  title = 'app works';
  user = null;
  logged_in = false;
  provider = null;

  constructor(private af: AngularFire) {
    this.af.auth.subscribe(user => {
      if (user) {
        this.user = user;
      } else {
        this.user = {};
      }
    });
  }
  getUserDetails(){
    return this.user;
  }
}
