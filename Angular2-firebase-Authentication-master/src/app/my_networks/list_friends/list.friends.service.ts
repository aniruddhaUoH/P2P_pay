import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Headers} from '@angular/http';
import {Friend} from '../../Friends';
import {Request} from '../Requests';
import {BaseUrlService} from "../../baseUrl.service";
@Injectable()
export class ListFriendsService {
  private friends: Friend[];
  private requests: Request[];
  constructor(private http: Http, private baseURLService: BaseUrlService) {
  }
  getFriends(): Observable<Friend[]> {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/listOfFriends',
      {headers: headers, withCredentials: true}).map(response => this.friends = (response.json()));
  }
  getRequests(): Observable<Request[]> {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/pendingRequests',
      {headers: headers, withCredentials: true}).map(response => this.requests = (response.json()));
  }
  onAcceptRequest(name: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/acceptRequest',
      name, {headers, withCredentials: true}).toPromise();
  }
  onRejectRequest(name: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/rejectRequest',
      name, {headers, withCredentials: true}).toPromise();
  }
}
