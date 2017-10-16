import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Headers} from '@angular/http';
import {BaseUrlService} from '../baseUrl.service';

@Injectable()
export class HomeService {
  constructor(private http: Http, private baseURLService: BaseUrlService) {}
  destroySession() {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/destroySession',
      {headers: headers, withCredentials: true}).toPromise();
  }
}
