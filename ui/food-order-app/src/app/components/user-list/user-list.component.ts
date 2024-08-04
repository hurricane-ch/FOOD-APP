import { Component } from '@angular/core';
import { UserService } from 'src/app/_services/user/user.service';

interface User {
  "id": string,
  "displayName"?: string,
  "email"?: string,
  "rolesName": string[]
}

enum Roles {
  ROLE_USER = 'user',
  ROLE_ADMIN = 'admin'
}

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {
  users: User[] | null;
  roles = Roles;
  successMessage: string | null = null; 
  errorMessage: string | null = null;
  requestInProgress = false;

  constructor(private userService: UserService) {
    this.userService.getUsers().subscribe((val: User[]) => this.users = val);
  }

  onChangeRole(role: string, user: User) {
    if (user.rolesName.includes(role)) {
      user.rolesName = user.rolesName.filter(r => r !== role);
    } else {
      user.rolesName.push(role);
    }
  }

  updateUser(user: User) {
    this.requestInProgress = true;
    if (!user.rolesName.length) {
      this.errorMessage=('Моля изберете роля за потребителя');
      setTimeout(() => {
        this.errorMessage = null;
        window.location.reload();
        this.requestInProgress = false;
      } , 1500);

    
    } else {
      console.log(user);
      
      if (user) {
        const payload = {
          id: user.id,
          rolesName: user.rolesName
        };

        this.userService.udpateUser(payload).subscribe(() => {
          this.successMessage = 'Успешно обновихте потребителя';
          setTimeout(() => {
            this.successMessage = null;
            this.requestInProgress = false;
          } , 1500);
        });
      }
    }
  }
}