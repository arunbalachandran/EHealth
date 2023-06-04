import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientAppointment } from '../../common/patientlist';
import { AuthService } from 'src/app/services/auth.service';
import { constants } from 'src/app/common/appconstants';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-home.component.html',
  styleUrls: ['./patient-home.component.css']
})
export class PatientHomeComponent {
  patientId: string = "";
  patientName: string = "";
  appointments: PatientAppointment[] = [];
  
  // TODO: make this event driven or something similar
  // TODO: remove the snapshot driven queryParameter routing
  constructor(
    private appointmentService: AppointmentService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {
      console.log("Received parameters: " + JSON.stringify(activatedRoute.snapshot.queryParams));
      this.patientId = activatedRoute.snapshot.queryParams['id'];
      this.patientName = activatedRoute.snapshot.queryParams['name'];
      appointmentService.getPatientAppointments(this.patientId).subscribe(
        (appointments) => {
          this.appointments = appointments;
        }
      );
  }

  ngOnInit(): void {}

  onSubmit(): void {
    this.authService.logout().subscribe({
      next: (data) => {
        sessionStorage.removeItem(constants.accessToken);
        sessionStorage.removeItem(constants.refreshToken);
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.log("Error: ", error);
      },
      complete: () => {
        console.log("Finished posting to token invalidation endpoint!");
      }
    })
  }
}
