import {Component, OnInit} from '@angular/core';
import {Transaction} from './Transactions';
import {AccountSummaryService} from './account.service';
import {Router} from '@angular/router';
import {Http} from '@angular/http';
import {ProfileService} from "../profile/profile.service";
import {UserDetail} from "../UserDetails";
import {InboundTransaction} from "./InboundTransactions";
import {OutboundTransaction} from "./OutboundTransactions";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountSummaryComponent implements OnInit {
  inboundTransactions: InboundTransaction[];
  outboundTransactions: OutboundTransaction[];
  userDetails: UserDetail[];
  transactions = [];
  totalpages;
  paginationArrayForTransaction;
  start: any;
  end: any;
  dateArray = [];
  currentPage = 1;
  constructor(private accountSummaryService: AccountSummaryService,
              private profileService: ProfileService,
              private router: Router, private http: Http) { }
  ngOnInit() {
    this.transactionPagination();
    this.accountSummaryService.transactionList(0, 3).then((response) => {
      this.transactions = response.json();
      for ( let i = 0 ; i < 3 ; i++) {
        this.dateArray[i] = new Date(this.transactions[i].dateTime);
      }
    }).catch((error) => {
      console.log(error);
    });
    this.profileService.getDetails().subscribe(data => {
      this.userDetails = data;
    });
    this.accountSummaryService.getInbounds().subscribe(data => {
      this.inboundTransactions = data;
    });
    this.accountSummaryService.getOutbounds().subscribe(data => {
      this.outboundTransactions = data;
    });
  }
  transactionPagination() {
    this.accountSummaryService.getCountOfTotalTransaction().then((Response) => {
      console.log('In Pagination Method', Response.json());
      this.totalpages = Response.json();
      this.paginationArrayCallBackFunction();
    }).catch((error) => {
      console.log(error);
    });
  }
  paginationArrayCallBackFunction() {
    this.paginationArrayForTransaction = this.accountSummaryService.getPaginationArray(this.totalpages);
    console.log('Total No Of Pages We Got ', this.paginationArrayForTransaction);
  }
  loadTransactionList(pageid: number) {
    if (pageid === 0) {
      pageid = 1;
    }
    this.currentPage = pageid;
    this.start = ( pageid - 1) * 3;
    this.end = pageid * 3;
    this.accountSummaryService.transactionList(this.start, this.end).then((response) => {
      this.transactions = response.json();
      for ( let i = 0 ; i < 3 ; i++) {
        this.dateArray[i] = new Date(this.transactions[i].dateTime);
      }
    }).catch((error) => {
      console.log(error);
    });
  }
  onPaginationNext(pageId: string) {
    const temp: number = (1 + parseInt( pageId, 10));
    this.loadTransactionList(temp);
  }
}
