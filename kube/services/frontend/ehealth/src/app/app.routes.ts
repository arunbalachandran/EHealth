import { RouterModule, Routes } from "@angular/router";
import { SignupDoctorComponent } from "./components/signup-doctor/signup-doctor.component";
import { HomepageComponent } from "./components/homepage/homepage.component";
import { SignupPatientComponent } from "./components/signup-patient/signup-patient.component";
import { DoctorListComponent } from "./components/doctor-list/doctor-list.component";
import { PatientListComponent } from "./components/patient-list/patient-list.component";

const routes: Routes = [
    { path: '', component: HomepageComponent},
    { path: 'signup/doctor', component: SignupDoctorComponent},
    { path: 'signup/patient', component: SignupPatientComponent},
    { path: 'doctor', component: DoctorListComponent},
    { path: 'patient', component: PatientListComponent}
]
export const routing = RouterModule.forRoot(routes);