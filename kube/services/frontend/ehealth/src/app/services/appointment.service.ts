import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { DoctorAppointment } from "../components/doctor-list/doctorlist";
import { HttpClient } from "@angular/common/http";
import { PatientAppointment } from "../components/patient-list/patientlist";

@Injectable({
    providedIn: 'root',
})
export class AppointmentService {

    private apiUrl = 'http://localhost:8080';
    private appointmentSuffix = 'appointments';

    constructor(private http: HttpClient) {
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getDoctorAppointments(doctorId: string): Observable<DoctorAppointment[]> {
        return this.http.get<DoctorAppointment[]>(`${this.apiUrl}/${this.appointmentSuffix}/doctor/${doctorId}`);
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getPatientAppointments(patientId: string): Observable<PatientAppointment[]> {
        return this.http.get<PatientAppointment[]>(`${this.apiUrl}/${this.appointmentSuffix}/patient/${patientId}`);
    }
}