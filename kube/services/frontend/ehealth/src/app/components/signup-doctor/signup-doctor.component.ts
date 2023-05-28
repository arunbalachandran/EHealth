import { Component, EventEmitter, Output } from '@angular/core';
import { DoctorSignup } from './doctorsignup';
import { SignupService } from 'src/app/services/signup.service';
import { Router } from '@angular/router';

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

    this.signupService.doctorSignupPost(doctorSignupData).subscribe({
      next: (data) => {
        console.log("Created doctor: " + JSON.stringify(data));
        this.router.navigate(['doctor'], {
          queryParams: {
            id: data.id,
            name: data.name
          }
        });
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
