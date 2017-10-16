import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {Headers} from '@angular/http';
import {Transaction} from './Transactions';
import {InboundTransaction} from "./InboundTransactions";
import {OutboundTransaction} from "./OutboundTransactions";
import {BaseUrlService} from "../baseUrl.service";

@Injectable()
export class AccountSummaryService {
  private transactions: Transaction[];
  total: any;
  private inboundTransactions: InboundTransaction[];
  private outboundTransactions: OutboundTransaction[];
  constructor(private http: Http, private baseURLService: BaseUrlService) {
  }
  getInbounds(): Observable<InboundTransaction[]> {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/inboundTransaction',
      {headers: headers, withCredentials: true}).map(response => this.inboundTransactions = (response.json()));
  }
  getOutbounds(): Observable<OutboundTransaction[]> {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/outboundTransaction',
      {headers: headers, withCredentials: true}).map(response => this.outboundTransactions = (response.json()));
  }
  getCountOfTotalTransaction() {
    event.preventDefault();
    const headers = new Headers();
     headers.append('Content-Type', 'application/json');
    this.total = this.http.post(this.baseURLService.baseURL + '/user/totalTransactions', null, {
      headers,
      withCredentials: true}).toPromise();
    return this.total;
  }
  getPaginationArray(Count) {
    const paginationArray = [];
    let x: number;
    x = parseInt(Count, 10);
    const val = 3
    let noOfPages: number = parseInt((x / 3 ) + '', 10);
    if (x % val) {
      noOfPages = noOfPages + 1;
    }
    while (paginationArray.length < noOfPages) {
      paginationArray.push(paginationArray.length + 1);
    }
    return paginationArray;
  }
  transactionList(start: any, end: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.baseURLService.baseURL + '/user/pageOfTransactions/?start=' + start + '&end=' + end, {
      headers, withCredentials: true}).toPromise();
  }
}
