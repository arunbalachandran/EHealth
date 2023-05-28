import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorAppointment } from './doctorlist';

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent {
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
