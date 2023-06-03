import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorAppointment } from './doctorappointment';

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
    private activatedRoute: ActivatedRoute
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
}
