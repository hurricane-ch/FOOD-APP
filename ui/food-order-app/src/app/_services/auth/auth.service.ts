import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/_services/token/token-storage.service';  // Make sure the import path is correct
import { AppConstants } from '../../constants/app.constants';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'  // This will make sure AuthService is a singleton service
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private router: Router,  // Inject Router
    private tokenStorageService: TokenStorageService  // Inject TokenStorageService
  ) {}

  login(credentials): Observable<any> {
    return this.http.post(AppConstants.BASE_AUTH_API + AppConstants.SIGN_IN, {
      email: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  logout(): void {
    this.tokenStorageService.signOut();  
    this.router.navigate([AppConstants.HOME_URL]).then(() => {
      window.location.reload();
    });
  }


  register(user): Observable<any> {
    return this.http.post(AppConstants.BASE_AUTH_API + AppConstants.SINGN_UP, {
      displayName: user.displayName,
      email: user.email,
      password: user.password,
      matchingPassword: user.matchingPassword,
      socialProvider: 'LOCAL'
    }, httpOptions);
  }

  update(user: any): Observable<any> {
    return this.http.put(AppConstants.BASE_AUTH_API + AppConstants.UPDATE, {
      id: user.id,
      displayName: user.displayName,
      email: user.email,
    }, httpOptions);
  }
}
