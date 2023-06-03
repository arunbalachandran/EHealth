import { RouterModule, Routes } from "@angular/router";
import { SignupDoctorComponent } from "./components/signup-doctor/signup-doctor.component";
import { SignupPatientComponent } from "./components/signup-patient/signup-patient.component";
import { DoctorHomeComponent } from "./components/doctor-home/doctor-home.component";
import { PatientListComponent } from "./components/patient-list/patient-list.component";
import { AddAppointmentComponent } from "./components/add-appointment/add-appointment.component";
import { LoginComponent } from "./components/login/login.component";

const routes: Routes = [
    { path: '', component: LoginComponent},
    { path: 'signup/doctor', component: SignupDoctorComponent},
    { path: 'signup/patient', component: SignupPatientComponent},
    { path: 'doctor', component: DoctorHomeComponent},
    { path: 'patient', component: PatientListComponent},
    { path: 'appointments/add', component: AddAppointmentComponent}
]
export const routing = RouterModule.forRoot(routes);