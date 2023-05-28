import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SignupService } from 'src/app/services/signup.service';

@Component({
  selector: 'app-signup-patient',
  templateUrl: './signup-patient.component.html',
  styleUrls: ['./signup-patient.component.css']
})
export class SignupPatientComponent {
  email: string = "";
  password: string = "";
  name: string = "";
  // TODO: make this field empty and have the user enter in a number
  age: number = 0;

  constructor(
    private signupService: SignupService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  // you can add some pre post validation here...
  onSubmit() {
    // you can add some pre post validation here...
    const patientSignupData = {
      email: this.email,
      password: this.password,
      name: this.name,
      age: this.age
    }

    this.signupService.patientSignupPost(patientSignupData).subscribe({
      next: (data) => {
        console.log("Created patient: " + JSON.stringify(data));
        this.router.navigate(['patient'], {
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
        console.log("Finished posting data to patientSignupPost");
      }
    });
  }
}
