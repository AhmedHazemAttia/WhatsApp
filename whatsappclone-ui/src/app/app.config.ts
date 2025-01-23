import {ApplicationConfig, inject, provideAppInitializer, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {KeycloakService} from './utills/keycloak/keycloak.service';
import {keycloakHttpInterceptor} from './utills/http/keycloak-http.interceptor';

const myKeycloakInit = ()=> {
  const initFn = ((key: KeycloakService) => {
    return () => key.init();
  }) (inject(KeycloakService))
  return initFn();
}


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([keycloakHttpInterceptor])
    ),
    provideAppInitializer(myKeycloakInit)

  ]
};



