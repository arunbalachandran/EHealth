import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { DoctorSignup } from "../components/signup-doctor/doctorsignup";
import { Observable } from "rxjs";
import { PatientSignup } from "../components/signup-patient/patientsignup";
    
const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json'
    }),
    // withCredentials: true, // for cookies
    observe: 'response' as 'response'
}

@Injectable({
    providedIn: 'root'
})
export class SignupService {

    private apiUrl = 'http://localhost:8080/api/v1/ehealth';
    private doctorSignupRoute = 'signup/doctor';
    private patientSignupRoute = 'signup/patient';

    constructor(private http: HttpClient) {
    }

    doctorSignupPost(doctorSignupData: DoctorSignup): Observable<HttpResponse<DoctorSignup>> {
        const url = `${this.apiUrl}/${this.doctorSignupRoute}`
        return this.http.post<DoctorSignup>(url, doctorSignupData, httpOptions);
    }

    patientSignupPost(patientSignupData: PatientSignup): Observable<HttpResponse<PatientSignup>> {
        const url = `${this.apiUrl}/${this.patientSignupRoute}`
        return this.http.post<PatientSignup>(url, patientSignupData, httpOptions);
    }
}