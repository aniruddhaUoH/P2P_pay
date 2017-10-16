import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {AppComponent} from './app.component';
import {AngularFireModule, AuthProviders, AuthMethods} from 'angularfire2';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {routing} from './app.routing';
import {ProfileComponent} from './profile/profile.component';
import {AccountSummaryComponent} from './account_summary/account.component';
import {PayComponent} from './pay/pay.component';
import {UserDetailsService} from './user-details.service';
import {LoginService} from './login/login.service';
import {AccountSummaryService} from './account_summary/account.service';
import {PayService} from './pay/pay.service';
import {AuthGuard} from './authguard.service';
import {ProfileService} from './profile/profile.service';
import {WalletModule} from './my_wallet/wallet.module';
import {NetworkModule} from './my_networks/network.module';
import {BaseUrlService} from './baseUrl.service';
import {HomeService} from './home/home.service';

export const firebaseconfig = {
  apiKey: 'AIzaSyD-3gY5qIiFkCcQiCScj5DhNsuM5rCLkrM',
  authDomain: 'p2ppay-dc664.firebaseapp.com',
  databaseURL: 'https://p2ppay-dc664.firebaseio.com',
  projectId: 'p2ppay-dc664',
  storageBucket: '',
  messagingSenderId: '1015749037830'
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ProfileComponent,
    AccountSummaryComponent,
    PayComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing, NetworkModule,
    WalletModule, AngularFireModule.initializeApp(firebaseconfig, {provider: AuthProviders.Google, method: AuthMethods.Popup}),
  ],
  providers: [UserDetailsService,
    LoginService, AccountSummaryService, HomeService,
    PayService, AuthGuard, ProfileService, BaseUrlService],
  bootstrap: [AppComponent]
})

export class AppModule {
}
