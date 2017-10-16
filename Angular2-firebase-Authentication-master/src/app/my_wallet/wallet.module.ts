import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {routing} from '../app.routing';
import {LoadMoneyComponent} from './load_money/load.money.component';
import {UnloadMoneyComponent} from './unload_money/unload.money.component';
import {UnloadMoneyService} from './unload_money/unload.money.service';
import {LoadMoneyService} from './load_money/load.money.service';

@NgModule({
  declarations: [UnloadMoneyComponent, LoadMoneyComponent],
  imports: [BrowserModule,
    FormsModule,
    HttpModule,
    routing],
  providers: [LoadMoneyService, UnloadMoneyService]
})
export class WalletModule {}
