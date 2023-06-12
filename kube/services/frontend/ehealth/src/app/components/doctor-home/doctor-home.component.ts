import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorAppointment } from './doctorappointment';
import { AuthService } from 'src/app/services/auth.service';
import { constants } from 'src/app/common/appconstants';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-home.component.html',
  styleUrls: ['./doctor-home.component.css']
})
export class DoctorHomeComponent {
  doctorId: string = "";
  doctorName: string = "";
  appointments: DoctorAppointment[] = [];

  // TODO: make this event driven or something similar
  // TODO: remove the snapshot driven queryParameter routing
  constructor(
    private appointmentService: AppointmentService,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {
      console.log("Received parameters: " + JSON.stringify(activatedRoute.snapshot.queryParams));
      this.doctorId = activatedRoute.snapshot.queryParams['id'];
      this.doctorName = activatedRoute.snapshot.queryParams['name'];
      appointmentService.getDoctorAppointments(this.doctorId).subscribe(
        (appointments) => {
          this.appointments = appointments;
        }
      );
  }

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
