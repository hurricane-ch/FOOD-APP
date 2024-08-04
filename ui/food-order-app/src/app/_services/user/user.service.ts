import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from 'src/app/constants/app.constants';

interface User {
  "id": string,
  "displayName"?: string,
  "email"?: string,
  "rolesName": string[]
}

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  getCurrentUser(): Observable<any> {
    return this.http.get(AppConstants.BASE_API_URL + AppConstants.USER_ME_URL, this.httpOptions);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${AppConstants.BASE_API_URL}user/all`);
  }

  udpateUser(payload: { id: string, rolesName: string[] }): Observable<User> {
    return this.http.put<User>(`${AppConstants.BASE_API_URL}user/update/role`, payload);
  }
}
