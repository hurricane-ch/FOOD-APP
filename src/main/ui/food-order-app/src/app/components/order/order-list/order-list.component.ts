import {
  Component,
  OnInit
} from '@angular/core';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';
import { IOrder } from 'src/app/interfaces/order';
import { OrderService } from 'src/app/_services/order/order.service';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  private roles: string[];
  isLoggedIn = false;
  isAdmin = false;

  message = '';
  orders: IOrder[] = [];
  activeOrders: IOrder[] = [];
  deactiveOrders: IOrder[] = [];
  user = this.tokenStorageService.getUser();

  constructor(
    private orderService: OrderService,
    private tokenStorageService: TokenStorageService,
    private router: Router
  ) { }

  ngOnInit(): void {

    if (!this.user.roles.includes('ROLE_ADMIN')) {
      const email = this.user.email
      this.orderService.getOrderByUser(email).subscribe((orders: any) => {
        this.orders = orders;
        this.sort();
      }, err => {
        this.message = err.message;
        console.log('ERROR -->> ', err.message);
      }, () => {
        console.log('Orders -->> ', this.orders);
      });

    } else {

      this.orderService.getAll()
        .subscribe(orders => {
          this.orders = orders;
          this.sort();
        }, err => {
          this.message = err.message;
          console.log('ERROR -->> ', err.message);
        }, () => {
          console.log('Orders -->> ', this.orders);
        });

      this.isLoggedIn = !!this.tokenStorageService.getToken();

      if (this.isLoggedIn) {
        const user = this.tokenStorageService.getUser();
        this.roles = user.roles;
        this.isAdmin = this.roles.includes('ROLE_ADMIN');
      }
      this.redirecting();
    }

  }

  sort() {
    for (let i = 0; i < this.orders.length; i++) {
      if (this.orders[i].active) {
        this.activeOrders.push(this.orders[i]);
      } else {
        this.deactiveOrders.push(this.orders[i]);
      }
    }
  }

  redirecting() {
    if (!this.isLoggedIn && !this.isAdmin) {
      this.router.navigate([AppConstants.HOME_URL]);
    }
  }

}
