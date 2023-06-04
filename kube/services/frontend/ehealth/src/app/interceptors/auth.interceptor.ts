import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpClient,
  HttpHeaders,
  HttpResponse,
} from '@angular/common/http';
import { Observable, catchError, finalize, switchMap, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { constants } from '../common/appconstants';

const postHttpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
  observe: 'response' as 'response',
};

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private apiUrl = 'http://localhost:8080/api/v1/ehealth/auth';
  private refreshRoute = 'refresh';
  private refresh = false;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private addAuthUsingAccessToken(
    request: HttpRequest<unknown>
  ): HttpRequest<unknown> {
    // don't add auth if calling the refresh endpoint
    if (sessionStorage.getItem(constants.accessToken) != null && !this.refresh) {
      const requestWithHeaders = request.clone({
        setHeaders: {
          Authorization: `Bearer ${sessionStorage.getItem(constants.accessToken)}`,
        },
      });
      return requestWithHeaders;
    }
    return request;
  }

  // TODO: what if refresh_token doesn't exist when this method is called?
  private handleAuthFailure(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<any> {
    if (!this.refresh) {
      this.refresh = true;
      return this.authService
        .refreshToken(sessionStorage.getItem(constants.refreshToken)!)
        .pipe(
          switchMap((response: HttpResponse<any>) => {
            sessionStorage.setItem(
              constants.accessToken,
              response.headers.get(constants.accessToken)!
            );
            sessionStorage.setItem(
              constants.refreshToken,
              response.headers.get(constants.refreshToken)!
            );
            // on a successful refresh, reset the refresh flag
            this.refresh = false;
            return next.handle(this.addAuthUsingAccessToken(request));
          }),
          catchError(error => {
            // on a refresh failure, reset the refresh flag
            this.refresh = false;
            return throwError(() => error);
          }),
          finalize(
            () => {
              this.refresh = false;
            }
          )
        );
    } else {
      return next.handle(this.addAuthUsingAccessToken(request));
    }
  }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    console.log('Request intercepted: ' + JSON.stringify(request));
    return next.handle(this.addAuthUsingAccessToken(request)).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          if (this.refresh) { // refresh must've failed
            return throwError(() => error);
          }
          return this.handleAuthFailure(request, next);
        }
        // default error handler
        return throwError(() => error);
      })
    );
  }
}
