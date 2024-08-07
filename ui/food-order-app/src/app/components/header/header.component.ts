import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';
import { OrderService } from 'src/app/_services/order/order.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  roles: string[];
  isLoggedIn = false;
  isAdmin = false;
  username: string;
  counter = 0;

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private orderService: OrderService
  ) { }

  ngOnInit(): void {
    this.orderService.getAll()
        .subscribe((orders: any[]) => {
          if(orders) {
            this.counter = orders.filter((el: any) => el.active).length;
          }
        });
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      this.isAdmin = this.roles.includes('ROLE_ADMIN');
      this.username = user.displayName;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate([AppConstants.HOME_URL]).then(() => {
      window.location.reload();
    });
  }
}
