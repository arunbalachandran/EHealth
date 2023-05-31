import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthInterceptor } from 'src/app/interceptors/auth.interceptor';
import { SignupService } from 'src/app/services/signup.service';

@Component({
  selector: 'app-signup-patient',
  templateUrl: './signup-patient.component.html',
  styleUrls: ['./signup-patient.component.css'],
})
export class SignupPatientComponent {
  email: string = '';
  password: string = '';
  name: string = '';
  // TODO: make this field empty and have the user enter in a number
  age: number = 0;

  constructor(private signupService: SignupService, private router: Router) {}

  ngOnInit(): void {}

  // you can add some pre post validation here...
  onSubmit() {
    const patientSignupData = {
      email: this.email,
      password: this.password,
      name: this.name,
      age: this.age,
    };

    this.signupService.patientSignupPost(patientSignupData).subscribe({
      next: (data) => {
        if (data.body != null && data.headers != null) {
          console.log('Created patient: ' + JSON.stringify(data));
          console.log('Patient headers: ' + JSON.stringify(data.headers.keys()));
          AuthInterceptor.accessToken = data.headers.get('access_token')!;
          this.router.navigate(['patient'], {
            queryParams: {
              id: data.body.id,
              name: data.body.name,
            },
          });
        } else {
          console.log('current body: ' + JSON.stringify(data));
          console.log('current headers: ' + JSON.stringify(data.headers));
          throw new Error('Patient signup endpoint failed!');
        }
      },
      error: (error) => {
        console.log('Error: ', error);
      },
      complete: () => {
        console.log('Finished posting data to patientSignupPost');
      },
    });
  }
}
