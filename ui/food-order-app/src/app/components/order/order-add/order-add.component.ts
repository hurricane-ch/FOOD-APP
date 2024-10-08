import {
  Component,
  OnInit
} from '@angular/core';
import {
  UntypedFormControl,
  UntypedFormGroup,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';

import { IProduct } from 'src/app/interfaces/product';
import { Order } from 'src/app/models/order';
import { OrderService } from 'src/app/_services/order/order.service';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {
  isLoading = false;
  minPrice = 5;
  fee = 0.0;
  products: IProduct[] = [];
  isLoggedIn = false;
  roles = [];
  isAdmin = false;
  userData = '';

  message = '';

  form = new UntypedFormGroup({
    address: new UntypedFormControl('', [
      Validators.required,
      Validators.minLength(3)
    ])
  });

  constructor(
    private orderService: OrderService,
    private router: Router,
    private tokenStorageService: TokenStorageService,
  ) { }

  ngOnInit(): void {
    this.isLoading = true;

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.isAdmin = this.roles.includes('ROLE_ADMIN');
      this.userData = user.displayName + ', ' + user.email;
    }

    this.products = this.orderService.products;
    this.calcFee();

    // Trigger initial validation to show the required message if needed
    this.form.get('address').markAsTouched();
  }

  calcFee() {
    this.fee = this.products.reduce((total, product) => total + product.price, 0);
    this.isLoading = false;
  }

  remove(product: IProduct) {
    const index = this.products.indexOf(product);
    if (index > -1) {
      this.products.splice(index, 1);
      this.orderService.products = this.products;  // Update service
      this.calcFee();  // Recalculate fee after removal
    }
  }

  onSubmit() {
    if (this.form.invalid || this.fee <= this.minPrice) {
      // Trigger validation messages if the form is invalid or fee is not sufficient
      this.form.markAllAsTouched();
      return;
    }

    let order = new Order();
    order.address = this.form.controls['address'].value;
    order.date = new Date().toLocaleDateString();
    order.active = true;
    order.products = this.products;
    order.sum = this.fee;
    order.userData = this.userData;

    this.orderService.addOrder(order).subscribe(response => {
    }, err => {
      this.message = err.message;
    }, () => {
      this.orderService.products = [];
      this.router.navigate([AppConstants.HOME_URL]);
    });
  }

  onCancel() {
    this.products = [];
    this.orderService.products = [];
    this.router.navigate([AppConstants.HOME_URL]);
  }

}
