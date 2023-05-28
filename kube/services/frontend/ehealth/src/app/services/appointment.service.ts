import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { DoctorAppointment } from "../components/doctor-list/doctorlist";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { PatientAppointment } from "../components/patient-list/patientlist";
import { Appointment } from "../components/add-appointment/appointment";

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    })
}

@Injectable({
    providedIn: 'root',
})
export class AppointmentService {

    private apiUrl = 'http://localhost:8080';
    private appointmentRoute = 'appointments';

    constructor(private http: HttpClient) {
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getDoctorAppointments(doctorId: string): Observable<DoctorAppointment[]> {
        const url = `${this.apiUrl}/${this.appointmentRoute}/doctor/${doctorId}`;
        return this.http.get<DoctorAppointment[]>(url);
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getPatientAppointments(patientId: string): Observable<PatientAppointment[]> {
        const url = `${this.apiUrl}/${this.appointmentRoute}/patient/${patientId}`;
        return this.http.get<PatientAppointment[]>(url);
    }

    createAppointment(appointmentRequest: Appointment) {
        const url = `${this.apiUrl}/${this.appointmentRoute}`;
        return this.http.post<Appointment>(url, appointmentRequest, httpOptions);
    }
}