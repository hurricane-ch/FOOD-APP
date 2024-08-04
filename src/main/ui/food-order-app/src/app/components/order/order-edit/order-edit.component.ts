import { Component, OnInit } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';
import { IOrder } from 'src/app/interfaces/order';
import { OrderService } from 'src/app/_services/order/order.service';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  private roles: string[];
  isLoggedIn = false;
  isAdmin = false;
  isLoading = false;

  message = '';
  order: IOrder;
  id: string;

  form = new UntypedFormGroup({
    status: new UntypedFormControl(''),
  });

  constructor(
    private orderService: OrderService,
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id; // Initialize id directly

    this.isLoading = true;
    
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.isAdmin = this.roles.includes('ROLE_ADMIN');
    }

    this.redirectIfNotLoggedInOrAdmin();
    this.getOrderById();
    this.isLoading = false;
  }

  submitHandler(): void {
    this.isLoading = true;
    this.order.active = this.form.controls['status'].value;
    this.orderService.editOrder(this.order)
      .subscribe(
        updatedOrder => {
          this.order = updatedOrder;
          this.getOrderById();
        },
        err => {
          this.message = err.message;
          console.error('ERROR -->> ', err.message);
          this.getOrderById();
        },
        () => {
          this.isLoading = false;
          this.router.navigate([AppConstants.ORDER_ALL_URL]);
        }
      );
  }

  private redirectIfNotLoggedInOrAdmin() {
    if (!this.isLoggedIn && !this.isAdmin) {
      this.router.navigate([AppConstants.ORDER_ALL_URL]);
    }
  }

  private getOrderById() {
    this.orderService.getById(this.id)
      .subscribe(
        fetchedOrder => {
          this.order = fetchedOrder;
        },
        err => {
          this.message = err.message;
          console.error('ERROR -->> ', err.message);
        },
        () => {
          console.log('Order -->> ', this.order);
        }
      );
  }
}
