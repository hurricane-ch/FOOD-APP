import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AppConstants } from 'src/app/constants/app.constants';
import { IProduct } from 'src/app/interfaces/product';
import { Product } from 'src/app/models/product';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class OrderService {

  products: Product[] = [];

  constructor(private http: HttpClient) { }

  transfer(productsInOrder: IProduct[]) {
    this.products = productsInOrder;
  }

  getAll(): Observable<any> {
    return this.http.get(AppConstants.BASE_API_URL + AppConstants.ORDER_ALL_URL, httpOptions);
  }

  getById(id): Observable<any> {
    return this.http.get(AppConstants.BASE_API_URL + AppConstants.ORDER_GET_BY_ID + id, httpOptions);
  }

  getOrderByUser(payload:string) {
    return this.http.get(`${AppConstants.BASE_API_URL}orders/${payload}`, httpOptions);
  }

  addOrder(order) {
    return this.http.post(AppConstants.BASE_API_URL + AppConstants.ORDER_ADD_URL, order,
      {
        headers: httpOptions.headers,
        observe: 'response'
      });
  }

  editOrder(order): Observable<any> {

    console.log('ORDER IN SERVICE -->>', order);
    return this.http.post(AppConstants.BASE_API_URL + AppConstants.ORDER_EDIT, order, httpOptions);
  }

}
