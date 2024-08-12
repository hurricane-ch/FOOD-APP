import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppConstants } from 'src/app/constants/app.constants';
import { AuthService } from 'src/app/_services/auth/auth.service';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  form: any = {};
  errorMessage = '';
  successMessage = '';
  showButton = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private tokenStorageService: TokenStorageService
  ) { }

  onSubmit(): void {
    if (!this.form.displayName && !this.form.email) {
      this.errorMessage = 'Трябва да попълните поне едно поле: Ново име или Нов имейл!';
      return;
    }

    if (this.form.email && !this.validateEmail(this.form.email)) {
      this.errorMessage = 'Невалиден имейл формат!';
      return;
    }

    const user = this.tokenStorageService.getUser();
    this.form.id = user.id;

    this.authService.update(this.form).subscribe(
      () => {
        this.successMessage = 'Обновихте данните си успешно. Ще бъдете пренасочени към секция меню.';
        this.showButton = true;
        this.errorMessage = '';
        setTimeout(() => {
          this.router.navigate([AppConstants.HOME_URL])
        }, 3000);
      },
      err => {
        this.errorMessage = err.error.message || 'An error occurred during update!';
        this.successMessage = '';
      }
    );
  }

  validateEmail(email: string): boolean {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
  }
}
