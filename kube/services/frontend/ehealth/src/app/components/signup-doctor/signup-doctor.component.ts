import { Component, EventEmitter, Output } from '@angular/core';
import { SignupService } from 'src/app/services/signup.service';
import { Router } from '@angular/router';
import { AuthInterceptor } from 'src/app/interceptors/auth.interceptor';

@Component({
  selector: 'app-signup-doctor',
  templateUrl: './signup-doctor.component.html',
  styleUrls: ['./signup-doctor.component.css']
})
export class SignupDoctorComponent {
  email: string = "";
  password: string = "";
  name: string = "";
  specialization: string = "";

  constructor(
    private signupService: SignupService,
    private router: Router
  ) {
  }

  ngOnInit(): void { }

  onSubmit() {
    // you can add some pre post validation here...
    const doctorSignupData = {
      email: this.email,
      password: this.password,
      name: this.name,
      specialization: this.specialization
    }

    // TODO: change this to be event driven
    this.signupService.doctorSignupPost(doctorSignupData).subscribe({
      next: (data) => {
        if (data.body != null && data.headers != null) {
          console.log("Created doctor: " + JSON.stringify(data.body));
          console.log("Doctor headers: " + JSON.stringify(data.headers.keys()));
          AuthInterceptor.accessToken = data.headers.get('access_token')!;
          this.router.navigate(['doctor'], {
            queryParams: {
              id: data.body.id,
              name: data.body.name
            }
          });
        } else {
          console.log("current body: " + JSON.stringify(data));
          console.log("current headers: " + JSON.stringify(data.headers));
          throw new Error("Doctor signup endpoint failed!");
        }
      },
      error: (error) => {
        console.log("Error: ", error);
      },
      complete: () => {
        console.log("Finished posting data to doctorSignupPost");
      }
    });
  }
}
