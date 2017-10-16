import {Component, OnInit} from '@angular/core';
import {Month} from './month';
import {Year} from './year';
import {LoadMoneyService} from "./load.money.service";
import {Router} from "@angular/router";
import {ProfileService} from "../../profile/profile.service";
import {UserDetail} from "../../UserDetails";

@Component({
  selector: 'app-load-money',
  templateUrl: './load.money.component.html',
  styleUrls: ['./load.money.component.css']
})
export class LoadMoneyComponent implements OnInit {
  userDetails: UserDetail[];
  selectedMonth: Month;
  selectedYear: Year;
  amt: number;
  userMessage: any;
  isVisible: boolean = false ;
  months = [
    new Month(1, 'January'),
    new Month(2, 'February'),
    new Month(3, 'March'),
    new Month(4, 'April'),
    new Month(5, 'May'),
    new Month(6, 'June'),
    new Month(7, 'July'),
    new Month(8, 'August'),
    new Month(9, 'September'),
    new Month(10, 'October'),
    new Month(11, 'November'),
    new Month(12, 'December')
  ];
  years = [
    new Year(2017),
    new Year(2018),
    new Year(2019),
    new Year(2020),
    new Year(2021),
    new Year(2022)
  ];
  constructor(private loadMoneyService: LoadMoneyService,
              private profileService: ProfileService, private router: Router) {}
  ngOnInit() {
    this.selectedMonth = this.months[1];
    this.selectedYear = this.years[1];
    this.profileService.getDetails().subscribe(data => {
      this.userDetails = data;
    });
  }

  onMonthInput($event) {
    $event.preventDefault();
    console.log('selectedMonth: ' + $event.target.value);
  }

  onYearInput($event) {
    $event.preventDefault();
    console.log('selectedYear: ' + $event.target.value);
  }
  successStatus(succeed: boolean) {
    if (succeed) {
      let amountData = {
        'deposit': this.amt
      };
      console.log('passing amount to database =--->> ' + this.amt);
      this.loadMoneyService.addAmount(amountData).then((response) => {
        this.isVisible = true;
        if (response.text() === 'success') {
          this.userMessage = 'Amount loaded Successfully';
          this.profileService.getDetails().subscribe(data => {
            this.userDetails = data;
          });
        } else {
          this.userMessage = 'Wallet could not be loaded';
        }
      }).catch((error) => {
        console.log(error);
      });
    }
  }
}
