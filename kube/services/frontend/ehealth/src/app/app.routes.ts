import { RouterModule, Routes } from "@angular/router";
import { SignupDoctorComponent } from "./components/signup-doctor/signup-doctor.component";
import { HomepageComponent } from "./components/homepage/homepage.component";
import { SignupPatientComponent } from "./components/signup-patient/signup-patient.component";

const routes: Routes = [
    { path: '', component: HomepageComponent},
    { path: 'signup_d', component: SignupDoctorComponent},
    { path: 'signup_p', component: SignupPatientComponent}
]
export const routing = RouterModule.forRoot(routes);