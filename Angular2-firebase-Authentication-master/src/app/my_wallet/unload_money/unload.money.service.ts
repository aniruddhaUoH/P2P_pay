import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Headers} from '@angular/http';
import {BaseUrlService} from "../../baseUrl.service";

@Injectable()
export class UnloadMoneyService {
  constructor(private http: Http, private baseURLService: BaseUrlService) { }
  unloadAmount(insertData: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.baseURLService.baseURL + '/user/unloadMoney',
      insertData, {headers: headers, withCredentials: true}).toPromise();
  }
}
