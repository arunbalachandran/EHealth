import { Component, EventEmitter, Output } from '@angular/core';
import { DoctorSignup } from './doctorsignup';
import { SignupService } from 'src/app/services/signup.service';

@Component({
  selector: 'app-signup-doctor',
  templateUrl: './signup-doctor.component.html',
  styleUrls: ['./signup-doctor.component.css']
})
export class SignupDoctorComponent {
  @Output() onSubmitDoctorSignup: EventEmitter<DoctorSignup> = new EventEmitter();
  userid: string = "";
  mailid: string = "";
  password: string = "";
  specialization: string = ""; // Need to not set the default

  constructor(private signupService: SignupService) {}

  ngOnInit(): void {}

  onSubmit() {
    // you can add some pre post validation here...
    const doctorSignupData = {
      userid: this.userid,
      mailid: this.mailid,
      password: this.password,
      specialization: this.specialization
    }

    this.signupService.doctorSignupPost(doctorSignupData).subscribe(
      (doctorSignupData) => {  // do something
        console.log("Able to send data successfully!");
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
