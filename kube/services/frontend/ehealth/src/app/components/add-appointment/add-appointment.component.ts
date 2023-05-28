import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Doctor } from 'src/app/common/doctor';
import { AppointmentService } from 'src/app/services/appointment.service';
import { DoctorService } from 'src/app/services/doctor.service';

@Component({
  selector: 'app-add-appointment',
  templateUrl: './add-appointment.component.html',
  styleUrls: ['./add-appointment.component.css']
})
export class AddAppointmentComponent {
  patientName: string = "";
  patientId: string = "";
  radiobutton: string = "";
  datepicker: string = "";
  doctors: Doctor[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private doctorService: DoctorService,
    private appointmentService: AppointmentService
  ) {
    console.log("Received parameters: " + JSON.stringify(activatedRoute.snapshot.queryParams));
    this.patientName = activatedRoute.snapshot.queryParams['patientName'];
    this.patientId = activatedRoute.snapshot.queryParams['patientId'];
    this.doctorService.getDoctors().subscribe(
      (doctors) => {
        this.doctors = doctors;
      }
    );
  }

  onSubmit() {
    const appointmentData = {
      doctorId: this.radiobutton,
      patientId: this.patientId,
      timeAppt: this.datepicker
    }

    this.appointmentService.createAppointment(appointmentData).subscribe({
      next: (data) => {
        console.log("Created Appointment: " + JSON.stringify(data));
        // route back to patients portal and show the list of appointments?
      },
      error: (error) => {
        console.log("Error: ", error);
      },
      complete: () => {
        console.log("Finished posting to createAppointment");
      }
    })
  }
}
