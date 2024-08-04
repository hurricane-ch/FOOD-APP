import { Component } from '@angular/core';
import { Router } from "@angular/router";
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
    const user = this.tokenStorageService.getUser();
    this.form.id = user.id;
    this.authService.update(this.form).subscribe(
      () => {
        this.successMessage = 'Обновихте данните си успешно.Ще бъдете пренасочение към секция меню.';
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
