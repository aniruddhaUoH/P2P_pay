import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Headers} from '@angular/http';
import {UserDetail} from '../UserDetails';
import {BaseUrlService} from "../baseUrl.service";

@Injectable()
export class ProfileService {
  private userDetails: UserDetail[];
  constructor(private http: Http, private baseURLService: BaseUrlService) {}
  getDetails(): Observable<UserDetail[]> {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/details',
      {headers: headers, withCredentials: true}).map(response => this.userDetails = (response.json()));
  }
  updateDetails(userData: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/updateDetails',
      userData, {headers: headers, withCredentials: true}).toPromise();
  }
}
