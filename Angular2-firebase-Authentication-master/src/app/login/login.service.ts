import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Headers} from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {BaseUrlService} from "../baseUrl.service";

@Injectable()
export class LoginService {
  constructor(private http: Http, private baseURLService: BaseUrlService) { }
  addUserDetails(userDetails: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/signin',
      userDetails, { headers: headers, withCredentials: true }).toPromise();
  }
  /*verifyToken(idToken: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'text/plain');
    return this.http.post('http://localhost:8080/P2Ppay-0.0.1-SNAPSHOT/user/verifyIdToken',
      idToken, {headers: headers, withCredentials: true}).toPromise();
  }*/
}
