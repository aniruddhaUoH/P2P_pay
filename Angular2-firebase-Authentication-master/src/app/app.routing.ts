import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {PayComponent} from './pay/pay.component';
import {UnloadMoneyComponent} from './my_wallet/unload_money/unload.money.component';
import {LoadMoneyComponent} from './my_wallet/load_money/load.money.component';
import {AccountSummaryComponent} from './account_summary/account.component';
import {ListFriendsComponent} from './my_networks/list_friends/list.friends.component';
import {InviteFriendsComponent} from './my_networks/invite_friends/invite.friends.component';
import {AuthGuard} from './authguard.service';

const appRoutes: Routes = [
  {path: '', redirectTo: '/app-login', pathMatch: 'full'},
  {path: 'app-login', component: LoginComponent},
  {
    path: 'app-home', component: HomeComponent,
    children: [
      {path: '', component: ProfileComponent, canActivate: [AuthGuard]},
      {path: 'app-profile', component: ProfileComponent, canActivate: [AuthGuard]},
      {path: 'app-pay', component: PayComponent, canActivate: [AuthGuard]},
      {path: 'app-unload-money', component: UnloadMoneyComponent, canActivate: [AuthGuard]},
      {path: 'app-load-money', component: LoadMoneyComponent, canActivate: [AuthGuard]},
      {path: 'app-account', component: AccountSummaryComponent, canActivate: [AuthGuard]},
      {path: 'app-list', component: ListFriendsComponent, canActivate: [AuthGuard]},
      {path: 'app-invite', component: InviteFriendsComponent, canActivate: [AuthGuard]}
    ]
  }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
