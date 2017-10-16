import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class BaseUrlService {
  baseURL: string = 'http://localhost:8080/P2Ppay-0.0.1-SNAPSHOT';
}
