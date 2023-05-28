import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { Doctor } from "../common/doctor";
import { Injectable } from "@angular/core";
        
const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
    })
}

@Injectable({
    providedIn: 'root'
})
export class DoctorService {

    private apiUrl = "http://localhost:8080/api/v1/ehealth";
    private doctorRoute = "doctor";

    constructor(private http: HttpClient) {
    }

    getDoctors(): Observable<Doctor[]> {
        const url = `${this.apiUrl}/${this.doctorRoute}`;
        return this.http.get<Doctor[]>(url, httpOptions)
    }
}