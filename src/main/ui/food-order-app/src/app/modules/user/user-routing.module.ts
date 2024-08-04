import {
    RouterModule,
    Routes
} from "@angular/router";
import { UserListComponent } from "src/app/components/user-list/user-list.component";
import { LogInComponent } from "src/app/components/user/log-in/log-in.component";
import { SignupComponent } from 'src/app/components/user/signup/signup.component';
import { ProfileComponent } from "src/app/components/user/update/profile.component";
import { AppConstants } from "src/app/constants/app.constants";

const USER = AppConstants.USER_URL;
const USER_ALL_URL = AppConstants.USER_ALL_URL;

const LOG_IN_URL = AppConstants.LOG_IN;
const SINGN_UP_URL = AppConstants.SINGN_UP;

const routes: Routes = [
    {
        path: USER,
        children: [
            {
                path: LOG_IN_URL,
                component: LogInComponent,
                data: {
                    isLoggedIn: false,
                }
            },
            {
                path: 'update',
                component: ProfileComponent, 
            },
            {
              path: SINGN_UP_URL,
              component: SignupComponent,
              data: {
                isLoggedIn: false,
              }
            },
            {
                path: USER_ALL_URL,
                component: UserListComponent
            }
        ]
    }
];
export const UserRoutingModule = RouterModule.forChild(routes);
