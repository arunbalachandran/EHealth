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
  username: string = "";
  mailid: string = "";
  password: string = "";
  specialization: string = ""; // Need to not set the default

  constructor(
    private signupService: SignupService,
    private router: Router
    ) {
  }

  ngOnInit(): void {}

  onSubmit() {
    // you can add some pre post validation here...
    const doctorSignupData = {
      username: this.username,
      mailid: this.mailid,
      password: this.password,
      specialization: this.specialization
    }

    this.signupService.doctorSignupPost(doctorSignupData).subscribe(
      (data) => {
        console.log("Created doctor: " + JSON.stringify(data));
        this.router.navigate(['doctor'], {
          queryParams: {
            unameDoc: data.username
          }
        });
      },
      error => {
        console.log("Error: ", error);
      },
      () => {
        console.log("Finished posting data to doctorSignupPost");
      }
    );
  }
}
