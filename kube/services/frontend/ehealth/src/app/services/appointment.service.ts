import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { DoctorAppointment } from "../components/doctor-home/doctorappointment";
import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { PatientAppointment } from "../common/patientlist";
import { Appointment } from "../components/add-appointment/appointment";
import { AppointmentResponse } from "../components/add-appointment/appointmentresponse";
        
        
const getHttpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
};
        
const postHttpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    }),
    // withCredentials: true, // for cookies
    observe: 'response' as 'response'
}

@Injectable({
    providedIn: 'root',
})
export class AppointmentService {

    private apiUrl = 'http://localhost:8080/api/v1/ehealth';
    private appointmentRoute = 'appointments';

    constructor(private http: HttpClient) {
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getDoctorAppointments(doctorId: string): Observable<DoctorAppointment[]> {
        const url = `${this.apiUrl}/${this.appointmentRoute}/doctor/${doctorId}`;
        return this.http.get<DoctorAppointment[]>(url, getHttpOptions);
    }
   
    // TODO: fix null pointer exception when there's no empty appointments
    getPatientAppointments(patientId: string): Observable<PatientAppointment[]> {
        const url = `${this.apiUrl}/${this.appointmentRoute}/patient/${patientId}`;
        return this.http.get<PatientAppointment[]>(url, getHttpOptions);
    }

    createAppointment(appointmentRequest: Appointment): Observable<HttpResponse<AppointmentResponse>> {
        const url = `${this.apiUrl}/${this.appointmentRoute}/add`;
        return this.http.post<AppointmentResponse>(url, appointmentRequest, postHttpOptions);
    }
}