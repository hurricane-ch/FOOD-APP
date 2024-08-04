import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { AuthService } from 'src/app/_services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  form: any = {};
  errorMessage = '';
  successMessage = '';
  showButton = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  onSubmit(): void {
    this.authService.register(this.form).subscribe(
      () => {
        this.successMessage = 'Регистрирахте се успешно! Ще бъдете пренасочени към страницата за вход.';
        this.showButton = true;
        this.errorMessage = '';
        setTimeout(() => {
          this.router.navigate(['/user/login']);
        }, 3000); 
      },
      err => {
        this.errorMessage = err.error.message || 'An error occurred during registration!';
        this.successMessage = '';
      }
    );
  }
}
