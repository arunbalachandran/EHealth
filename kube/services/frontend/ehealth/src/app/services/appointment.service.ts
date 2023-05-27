import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { DoctorAppointment } from "../components/doctor-list/doctorlist";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root',
})
export class AppointmentService {

    private apiUrl = 'http://localhost:8080';
    private appointmentSuffix = 'appointments';

    constructor(private http: HttpClient) {
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getDoctorAppointments(unameDoc: string): Observable<DoctorAppointment[]> {
        return this.http.get<DoctorAppointment[]>(`${this.apiUrl}/${this.appointmentSuffix}?unameDoc=${unameDoc}`);
    }
}