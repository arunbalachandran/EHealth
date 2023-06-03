import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginData } from '../components/login/logindata';
import { UserDetails } from '../components/login/userdetails';

const postHttpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
  observe: 'response' as 'response',
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/ehealth/auth';
  private refreshRoute = 'refresh';
  private loginRoute = 'login';

  constructor(private http: HttpClient) {}

  login(loginData: LoginData): Observable<HttpResponse<UserDetails>> {
    return this.http.post<UserDetails>(
      `${this.apiUrl}/${this.loginRoute}`,
      loginData,
      postHttpOptions
    );
  }

  refreshToken(refreshToken: string): Observable<HttpResponse<any>> {
    return this.http.post(
      `${this.apiUrl}/${this.refreshRoute}`,
      { refreshToken: refreshToken },
      postHttpOptions
    );
  }
}
