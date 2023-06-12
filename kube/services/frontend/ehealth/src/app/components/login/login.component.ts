import { Component, Sanitizer } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { UserDetails } from './userdetails';
import { Router } from '@angular/router';
import { constants } from 'src/app/common/appconstants';
import { DomSanitizer, SafeStyle } from '@angular/platform-browser';

const roles = {
  doctor: 'DOCTOR',
  patient: 'PATIENT'
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = "";
  password: string = "";
  bgImageUrl: string = "../../../assets/login/loginpage_bg.png";
  bgImage!: SafeStyle;

  constructor(
    private router: Router,
    private authService: AuthService,
    private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.bgImage = this.sanitizer.bypassSecurityTrustStyle(`url(${this.bgImageUrl})`);
  }

  onSubmit() {
    const loginData = {
      email: this.email,
      password: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (data) => {
        if (data.body != null && data.headers != null) {
          console.log("Logged in successfully: " + JSON.stringify(data.body));
          sessionStorage.setItem(constants.accessToken, data.headers.get(constants.accessToken)!);
          sessionStorage.setItem(constants.refreshToken, data.headers.get(constants.refreshToken)!);
          const user: UserDetails = data.body;
          if (user.role === roles.doctor) {
            this.router.navigate(['doctor'], {
              queryParams: {
                id: user.id,
                name: user.name
              }
            });
          } else if (user.role === roles.patient) {
            this.router.navigate(['patient'], {
              queryParams: {
                id: user.id,
                name: user.name
              }
            });
          } else {
            throw new Error('User login failed!');
          }
        }
      },
      error: (error) => {
        console.log('Error: ', error);
      },
      complete: () => {
        console.log("Finished login process!");
      }
    })
  }
}
