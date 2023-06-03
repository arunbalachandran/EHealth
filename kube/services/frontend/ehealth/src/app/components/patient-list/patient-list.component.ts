import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppointmentService } from 'src/app/services/appointment.service';
import { PatientAppointment } from '../../common/patientlist';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent {
  patientId: string = "";
  patientName: string = "";
  appointments: PatientAppointment[] = [];
  
  // TODO: make this event driven or something similar
  // TODO: remove the snapshot driven queryParameter routing
  constructor(
    private appointmentService: AppointmentService,
    private activatedRoute: ActivatedRoute
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
}
