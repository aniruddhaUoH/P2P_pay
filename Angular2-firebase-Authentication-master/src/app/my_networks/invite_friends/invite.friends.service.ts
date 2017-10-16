import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Headers} from '@angular/http';
import {SearchFriend} from '../search.friends';
import {Observable} from 'rxjs/Observable';
import {BaseUrlService} from "../../baseUrl.service";

@Injectable()
export class InviteFriendsService {
  constructor(private http: Http, private baseURLService: BaseUrlService) { }
  onFriendSearch(name: any): Observable<SearchFriend[]> {
    console.log('Search obj: ', name)
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/search',
      name, {headers, withCredentials: true}).map(response => response.json());
  }
  onRequestSend(name: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/sendRequest',
      name, {headers, withCredentials: true}).toPromise();
  }
}
