import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SignupService } from 'src/app/services/signup.service';

@Component({
  selector: 'app-signup-patient',
  templateUrl: './signup-patient.component.html',
  styleUrls: ['./signup-patient.component.css']
})
export class SignupPatientComponent {
  username: string = "";
  mailid: string = "";
  password: string = "";
  age: string = "";

  constructor(
    private signupService: SignupService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  // you can add some pre post validation here...
  onSubmit() {
    // you can add some pre post validation here...
    const patientSignupData = {
      username: this.username,
      mailid: this.mailid,
      password: this.password,
      age: this.age
    }

    this.signupService.patientSignupPost(patientSignupData).subscribe(
      (data) => {
        console.log("Created patient: " + JSON.stringify(data));
        this.router.navigate(['patient'], {
          queryParams: {
            unamePat: data.username
          }
        });
      },
      error => {
        console.log("Error: ", error);
      },
      () => {
        console.log("Finished posting data to patientSignupPost");
      }
    );
  }
}
