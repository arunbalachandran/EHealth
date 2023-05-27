import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DoctorSignup } from "../components/signup-doctor/doctorsignup";
import { Observable } from "rxjs";
import { PatientSignup } from "../components/signup-patient/patientsignup";
    
const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    }),
}

@Injectable({
    providedIn: 'root'
})
export class SignupService {

    private apiUrl = 'http://localhost:8080';
    private doctorSignupRoute = 'signup/doctor';
    private patientSignupRoute = 'signup/patient';

    constructor(private http: HttpClient) {
    }

    doctorSignupPost(doctorSignupData: DoctorSignup): Observable<DoctorSignup> {
        const url = `${this.apiUrl}/${this.doctorSignupRoute}`
        return this.http.post<DoctorSignup>(url, doctorSignupData, httpOptions);
    }

    patientSignupPost(patientSignupData: PatientSignup): Observable<PatientSignup> {
        const url = `${this.apiUrl}/${this.patientSignupRoute}`
        return this.http.post<PatientSignup>(url, patientSignupData, httpOptions);
    }
}