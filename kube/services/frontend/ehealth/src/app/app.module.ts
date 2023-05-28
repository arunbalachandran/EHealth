import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { routing } from './app.routes';
import { SignupDoctorComponent } from './components/signup-doctor/signup-doctor.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { SignupPatientComponent } from './components/signup-patient/signup-patient.component';
import { DoctorListComponent } from './components/doctor-list/doctor-list.component';
import { PatientListComponent } from './components/patient-list/patient-list.component';
import { AddAppointmentComponent } from './components/add-appointment/add-appointment.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    SignupDoctorComponent,
    HomepageComponent,
    SignupPatientComponent,
    DoctorListComponent,
    PatientListComponent,
    AddAppointmentComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    routing
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
